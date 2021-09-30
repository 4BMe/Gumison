package com.ssafy.gumison.redis;

import com.ssafy.gumison.common.dto.UserExpDto;
import com.ssafy.gumison.common.dto.UserRankDto;
import com.ssafy.gumison.common.enums.RedisKey;
import com.ssafy.gumison.db.repository.UserRepositorySupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RankProviderImpl implements RankProvider {

  private final Long MAX_EXP = (long) 0xffff_ffff;

  private final String KEY_PREFIX = RedisKey.GUMISON_CACHE.name();

  private final UserRepositorySupport userRepositorySupport;

  private final ZSetOperations<String, Object> zSetOperations;

  private Long userCount;

  public RankProviderImpl(UserRepositorySupport userRepositorySupport,
      RedisTemplate<String, Object> redisTemplate) {
    this.userRepositorySupport = userRepositorySupport;
    this.zSetOperations = redisTemplate.opsForZSet();
    loadAllUserExpIntoRankZSet();
  }

  /*
      모든 유저의 닉네임과 경험치를 ZSet에 입력
      @returns : 올라간 유저의 수
     */
  @Override
  public Long loadAllUserExpIntoRankZSet() {

    List<UserExpDto> userExpDtoList = userRepositorySupport.findNicknamesAndExpAll();

    log.info("Load all user exp into ZSet, size - {}", userExpDtoList.size());
    userExpDtoList.forEach(userExpDto -> {
      Long accumulateExp = userExpDto.getAccumulateExp();
      String nickname = userExpDto.getNickname();
      if (nickname == null || accumulateExp == null) {
        log.error("illegal user information, user nickname - {}, user Exp - {}", nickname,
            accumulateExp);
        return;
//        throw new RuntimeException("illegal user information");
      }

      zSetOperations
          .add(KEY_PREFIX + RedisKey.RANK.name(), nickname, MAX_EXP - accumulateExp);
    });

    this.userCount = (long)userExpDtoList.size();
    return userCount;
  }

  /*
    닉네임으로 해당 유저의 순위 가져오기
     @params: nickname
     @returns: UserRankDto - 유저 닉네임, 순위
   */
  @Override
  public UserRankDto getUserRankByNickname(String nickname) {
    Optional<Long> userRankOptional = Optional
        .ofNullable(zSetOperations.rank(KEY_PREFIX + RedisKey.RANK, nickname));
    log.info("load user rank, nickname - {}, rank - {}", nickname, userRankOptional.orElse(-1L));
    return UserRankDto.of(nickname, userRankOptional.orElseThrow(RuntimeException::new));
  }

  /*
    시작 오프셋 + limit 의 유저 랭크 정보 및 닉네임 반환
    @params: startOffset - 시작하는 사용자 위치 인덱스
             limit - 가져올 사용자 수
    @returns: userRankDtoList - 유저 닉네임, 랭크 순위 리스트 (size() == limit)
   */
  @Override
  public List<UserRankDto> getUserRankByStartOffsetAndLimit(int startOffset, int limit) {
    int endOffset = startOffset + limit;
    Optional<Set<Object>> setOptional = Optional.ofNullable(zSetOperations
        .range(KEY_PREFIX + RedisKey.RANK, startOffset, endOffset < userCount ? limit : -1));

    if (!setOptional.isPresent()) {
      throw new RuntimeException("redis is used in pipeline or transaction");
    }

    List<UserRankDto> userRankDtoList = new ArrayList<>(limit);
    AtomicLong rank = new AtomicLong(startOffset + 1);
    setOptional.get()
        .forEach(v -> userRankDtoList.add(UserRankDto.of((String) v, rank.getAndIncrement())));

    return userRankDtoList;
  }

  @Override
  public Long getUserCount() {
    this.userCount = zSetOperations.zCard(KEY_PREFIX + RedisKey.RANK);
    return userCount;
  }

  private String paddingNicknameWithAccumulateVideo(String nickname, Integer accumulateVideo) {
    return String.format("%012d", accumulateVideo) + nickname;
  }
}
