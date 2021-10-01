package com.ssafy.gumison.api.controller;

import com.ssafy.gumison.common.dto.UserOauthDto;
import com.ssafy.gumison.db.entity.User;
import com.ssafy.gumison.security.CurrentUser;
import com.ssafy.gumison.security.UserPrincipal;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ssafy.gumison.api.response.UserSearchRes;
import com.ssafy.gumison.api.service.UserService;
import com.ssafy.gumison.common.response.ApiResponseDto;
import lombok.RequiredArgsConstructor;

import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@Api(value = "사용자 관련 API")
@RequestMapping("/api/users")
@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping("search/{keyword}/{pageNumber}")
  public ApiResponseDto<UserSearchRes> searchUsers(@PathVariable("keyword") String nickname,
      @PathVariable int pageNumber) {
    UserSearchRes userSearchRes = userService.getUserList(nickname.trim(), pageNumber);
    return ApiResponseDto.success(userSearchRes);
  }



  @ApiOperation(value = "사용자 정보", notes = "인증된 사용자의 정보를 반환합니다.", response = ApiResponseDto.class)
  @ApiResponses({@ApiResponse(code = 200, message = "성공"),
      @ApiResponse(code = 401, message = "인증 실패"), @ApiResponse(code = 404, message = "페이지 없음"),
      @ApiResponse(code = 500, message = "서버 오류")})
  @PreAuthorize("hasRole('USER')")
  @GetMapping("/oauth2/login")
  public ApiResponseDto<UserOauthDto> getCurrentUser(@ApiIgnore @CurrentUser UserPrincipal userPrincipal) {
    log.info("getCurrentUser: userPrincipal - {}",  userPrincipal);
    UserOauthDto user = userService.getOauthUserByOauthId(userPrincipal.getEmail());
    log.info("Get user profile: the User OauthId - {}",  user.getOAuthId());

    return ApiResponseDto.success(user);
  }
}
