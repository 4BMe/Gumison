package com.ssafy.gumison.redis;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserRankSearchKeywordRepositoryTest {

  @Autowired
  UserRankSearchKeywordRepository userRankSearchKeywordRepository;

  @Test
  public void 검색키워드를_저장하고_조회한다() {
    //given
    final String keyword = "testKey";
    final Long value = 3L;

    //when
    userRankSearchKeywordRepository.setUserSearchKeywordCount(keyword, value);
    Long result = userRankSearchKeywordRepository.getUserSearchKeywordCount(keyword);

    //then
    assertEquals(result, value);
  }
}