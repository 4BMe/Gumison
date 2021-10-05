package com.ssafy.gumison.api.service;

import com.ssafy.gumison.api.response.UserSearchRes;
import com.ssafy.gumison.common.dto.UserOauthDto;

import org.springframework.security.core.userdetails.UserDetails;
import com.ssafy.gumison.common.dto.UserSearchDto;

/**
 * 유저 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface UserService {

  UserSearchRes getUserList(String nickname, int pageNumber);

  /**
   * oauthUserId로 사용자 정보 UserOauthDto 반환
   *
   * @param oauthId
   * @return { 닉네임, 사용자 소개, 프로필 사진, oAuthId, oAuthType }
   */
  UserOauthDto getOauthUserByOauthId(String oauthId);

  /**
   * TokenAuthenticationFilter 만들 때 사용 UserDetails 반환
   *
   * @param oauthId 소셜 로그인 key값
   * @return userName, password
   */
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

  /**
   * oauthId로 유저 정보(닉네임, 프로필 사진, 소개글) 변경
   *
   * @param oauthId     사용자의 oAuthId
   * @param userOauthDto (변경 후 닉네임, 프로필 사진, 소개글)
   * @return 변경된 유저 정보 (닉네임, 프로필 사진, 소개글)
   */
  UserOauthDto updateUserByOauthId(String oauthId, UserOauthDto userOauthDto);

  /**
   * oauthId로 유저 탈퇴
   *
   * @param oauthId
   */
  void deleteUserByOauthId(String oauthId);
}
