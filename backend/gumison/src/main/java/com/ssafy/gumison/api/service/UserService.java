package com.ssafy.gumison.api.service;

import com.ssafy.gumison.api.response.UserSearchRes;
import com.ssafy.gumison.common.dto.UserOauthDto;

import org.springframework.security.core.userdetails.UserDetails;
import com.ssafy.gumison.common.dto.UserSearchDto;
import com.ssafy.gumison.db.entity.User;
import javax.servlet.http.HttpSession;

/**
 * 유저 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface UserService {

  UserSearchRes getUserList(String nickname, int pageNumber);

  UserOauthDto getOauthUserByOauthId(String oauthId);

  UserDetails loadUserByOauthId(String oauthId);

  /**
   * 유저 닉네임으로 UserSearcDto 반환
   *
   * @param nickname 사용자 닉네임
   * @return 유저 정보 중 닉네임, 프로필, 티어코드, 문제 해결 숫자 반환
   */
  UserSearchDto getUserSearchDtoByNickname(String nickname);

  /**
   * 키워드로 검색한 유저의 전체 수를 반환
   *
   * @param keyword 검색 키워드
   * @return 해당 키워드를 포함한 유저의 수 카운트
   */
  Long getUserCountByKeyword(String keyword);

}
