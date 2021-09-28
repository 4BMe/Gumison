package com.ssafy.gumison.common.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RankUtilityTest {

  @Autowired
  private RankUtility rankUtility;

  @Test
  public void 모든사용자의_경험치를_ZSET에_올린다(){
    rankUtility.loadAllUserExpIntoRankZSet();
  }

}