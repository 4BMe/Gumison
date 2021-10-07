package com.ssafy.gumison.db.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserRepositoryTest {

  @Autowired
  UserRepository userRepository;

  @Test
  void 키워드에_해당하는_유저_카운트를_검색한다() {
    // given
    String keyword = "dum";
    // when
    Long count = userRepository.countByNicknameContaining(keyword);
    // then
    assertNotNull(count);
    assertTrue(count >= 0);
  }
}
