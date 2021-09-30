package com.ssafy.gumison.api.service;

import com.ssafy.gumison.api.response.UserSearchRes;
import com.ssafy.gumison.common.dto.SessionUserDto;

import com.ssafy.gumison.common.dto.UserSearchDto;
import com.ssafy.gumison.db.entity.User;
import javax.servlet.http.HttpSession;

/**
 * 유저 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface UserService {

  UserSearchRes getUserList(String nickname, int pageNumber);

  SessionUserDto getCurrentUser(HttpSession httpSession);

  /**
   * 유저 닉네임으로 UserSearcDto 반환
   *
   * @param nickname 사용자 닉네임
   * @return 유저 정보 중 닉네임, 프로필, 티어코드, 문제 해결 숫자 반환
   */
  UserSearchDto getUserSearchDtoByNickname(String nickname);


}
