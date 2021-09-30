package com.ssafy.gumison.api.service;

import com.ssafy.gumison.api.response.UserSearchRes;
import com.ssafy.gumison.common.dto.UserOauthDto;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * 유저 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface UserService {

  UserSearchRes getUserList(String nickname, int pageNumber);

  UserOauthDto getOauthUserByOauthId(String oauthId);
  UserDetails loadUserByOauthId(String oauthId);

}
