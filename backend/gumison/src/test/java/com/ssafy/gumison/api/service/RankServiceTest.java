package com.ssafy.gumison.api.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.ssafy.gumison.common.dto.UserRankDto;
import com.ssafy.gumison.redis.RankProvider;

@SpringBootTest
class RankServiceTest {

  @Autowired
  RankService rankService;

  @Autowired
  RankProvider rankProvider;

  @Test
  public void 닉네임으로_랭크를_불러온다() {

    // given
    final String NICKNAME = "dummy1";
    final Long USER_COUNT = rankProvider.getUserCount();
    // when
    UserRankDto userRankDto = rankService.getUserRankByNickname(NICKNAME);
    // then
    assertNotNull(userRankDto);
    assertTrue(userRankDto.getRank() >= 0 && userRankDto.getRank() <= USER_COUNT);
  }

  @Test
  public void 페이지에_맞게_유저_리스트를_불러온다() {
    // given
    final int PAGE = 1;
    final int MAX_USER_PER_PAGE = 10;

    // when
    List<UserRankDto> userRankDtoList = rankService.getUserRankByPage(PAGE);

    // then
    assertTrue(userRankDtoList.size() <= MAX_USER_PER_PAGE);

    AtomicInteger index = new AtomicInteger(1);
    userRankDtoList.forEach(userRankDto -> {
      assertNotNull(userRankDto.getRank());
      assertNotNull(userRankDto.getNickname());

      assertTrue((PAGE - 1) * MAX_USER_PER_PAGE + index.getAndIncrement() == userRankDto.getRank());
    });
  }
}
