package com.ssafy.gumison.common.util;

import com.ssafy.gumison.common.dto.UserExpDto;
import com.ssafy.gumison.common.dto.UserRankDto;
import com.ssafy.gumison.common.enums.RedisKey;
import com.ssafy.gumison.db.repository.UserRepositorySupport;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RankProvider {

  private final Long MAX_EXP = (long) 0xffff_ffff;

  private final String KEY_PREFIX = RedisKey.GUMISON_CACHE.name();

  private final UserRepositorySupport userRepositorySupport;

  private final ZSetOperations<String, Object> zSetOperations;

  public RankProvider(UserRepositorySupport userRepositorySupport,
      RedisTemplate<String, Object> redisTemplate) {
    this.userRepositorySupport = userRepositorySupport;
    this.zSetOperations = redisTemplate.opsForZSet();
  }

  /*
      모든 유저의 닉네임과 경험치를 ZSet에 입력
      @returns : 올라간 유저의 수
     */
  public int loadAllUserExpIntoRankZSet() {

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

    return userExpDtoList.size();
  }

  public UserRankDto getUserRankByNickname(String nickname) {
    Optional<Long> userRankOptional = Optional
        .ofNullable(zSetOperations.rank(KEY_PREFIX + RedisKey.RANK, nickname));

    return UserRankDto.of(nickname, userRankOptional.orElseThrow(RuntimeException::new));
  }

  private String paddingNickNameWithAccumulateVideo(String nickname, Integer accumulateVideo) {
    return String.format("%012d", accumulateVideo) + nickname;
  }
}
