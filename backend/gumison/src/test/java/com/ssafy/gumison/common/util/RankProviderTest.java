package com.ssafy.gumison.common.util;

import static org.junit.jupiter.api.Assertions.*;

import com.ssafy.gumison.db.repository.UserRepositorySupport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RankProviderTest {

  @Autowired
  private RankProvider rankProvider;

  @Autowired
  private UserRepositorySupport userRepositorySupport;

  @Test
  public void 모든사용자의_경험치를_ZSET에_올린다() {
    //given
    final int SIZE = userRepositorySupport.findNicknamesAndExpAll().size();
    //when
    int returnSize = rankProvider.loadAllUserExpIntoRankZSet();
    //then
    assertEquals(SIZE, returnSize);

  }

}