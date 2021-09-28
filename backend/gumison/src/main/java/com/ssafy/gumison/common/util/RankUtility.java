package com.ssafy.gumison.common.util;

import com.ssafy.gumison.common.dto.UserExpDto;
import com.ssafy.gumison.common.enums.RedisKey;
import com.ssafy.gumison.db.entity.User;
import com.ssafy.gumison.db.repository.RankRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RankUtility {

  private final RankRepository rankRepository;

  private final RedisTemplate<String, Object> restTemplate;

  public void loadAllUserExpIntoRankZSet() {
    ZSetOperations<String, Object> zSetOperations = restTemplate.opsForZSet();
    log.info("Load all user exp into zset");

    List<User> userExpDtoList = rankRepository.findNicknamesAndExpAll();
    userExpDtoList.forEach(userExpDto -> zSetOperations
        .add(RedisKey.RANK.name(), userExpDto.getNickname(), userExpDto.getAccumulateExp()));
  }
}
