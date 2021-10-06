package com.ssafy.gumison.redis;

import com.ssafy.gumison.common.enums.RedisKey;
import com.ssafy.gumison.common.exception.ResourceNotFoundException;
import java.util.Optional;

import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 유저 검색 키워드를 레디스에 저장하고 불러오는 인터페이스의 구현체
 *
 * @author cherrytomato1
 * @version 1.1 캐시 저장시간 5분 -> 2분 30초
 */

@Component
@Slf4j
public class UserRankSearchKeywordRepositoryImpl implements UserRankSearchKeywordRepository {

  private final ValueOperations<String, Object> valueOperations;
  private final String KEY_PREFIX = RedisKey.SEARCH_KEYWORD_COUNT.name() + "_";

  public UserRankSearchKeywordRepositoryImpl(RedisTemplate<String, Object> redisTemplate) {
    valueOperations = redisTemplate.opsForValue();
  }

  /**
   * 키워드에 해당하는 유저의 수를 받아옵니다.
   *
   * @param keyword 검색할 키워드
   * @return 해당하는 유저의 수
   */
  @Override
  public Long getUserSearchKeywordCount(String keyword) {
    Optional<Object> countOptional = Optional
        .ofNullable(valueOperations.get(KEY_PREFIX + keyword));

    try {
      return Long.valueOf((String) countOptional
          .orElseThrow(() -> new ResourceNotFoundException("key set", keyword, "keyword")));
    } catch (NumberFormatException | ClassCastException e) {
      throw new RuntimeException("value isn't Long type");
    }
  }

  /**
   * 키워드에 해당하는 유저의 수를 저장
   *
   * @param keyword 저장할 키워드
   * @param count   저장할 값
   */
  @Transactional
  public void setUserSearchKeywordCount(String keyword, Long count) {
    log.info("set new keyword in redis, keyword - {}, count - {}", keyword, count);
    valueOperations.set(KEY_PREFIX + keyword, String.valueOf(count), 150, TimeUnit.SECONDS);
  }
}
