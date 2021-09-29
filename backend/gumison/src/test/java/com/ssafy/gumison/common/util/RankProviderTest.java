package com.ssafy.gumison.common.util;

import static org.junit.jupiter.api.Assertions.*;

import com.ssafy.gumison.common.dto.UserRankDto;
import com.ssafy.gumison.db.repository.UserRepositorySupport;
import java.util.List;
import org.junit.jupiter.api.Assertions;
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

  @Test
  public void 사용자의_순위를_검색한다() {
    //given
    final String NICKNAME = "dummy1";
    //when
    UserRankDto userExpDto = rankProvider.getUserRankByNickname(NICKNAME);
    final int SIZE = userRepositorySupport.findNicknamesAndExpAll().size();
    //then
    assertNotNull(userExpDto);
    assertTrue(userExpDto.getRank() >= 0 && userExpDto.getRank() <= SIZE);
  }

  @Test
  public void 순위_목록을_가져온다(){
    //given
    final int START_OFFSET = 0;
    final int LIMIT = 10;
    //when
    List<UserRankDto> list = rankProvider.getUserRankByStartOffsetAndLimit(START_OFFSET, LIMIT);
    //then
    assertNotNull(list);
    list.forEach(Assertions::assertNotNull);
  }

}