package com.ssafy.gumison.db.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.ssafy.gumison.common.dto.UserExpDto;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserRepositorySupportTest {

  @Autowired
  UserRepositorySupport userRepositorySupport;

  @Test
  public void 모든_사용자의_경험치와_닉네임을_불러온다() {
    //given

    //when
    List<UserExpDto> userExpDtoList = userRepositorySupport.findNicknamesAndExpAll();

    //then
    userExpDtoList.forEach(userExpDto -> {
      assertNotNull(userExpDto.getNickname());
      assertNotNull(userExpDto.getAccumulateExp());
    });
  }
}