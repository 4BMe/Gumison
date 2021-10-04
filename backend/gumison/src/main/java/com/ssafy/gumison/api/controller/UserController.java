package com.ssafy.gumison.api.controller;

import com.ssafy.gumison.common.dto.UserOauthDto;
import com.ssafy.gumison.common.dto.UserSearchDto;
import com.ssafy.gumison.security.CurrentUser;
import com.ssafy.gumison.security.UserPrincipal;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
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
  public ApiResponseDto<UserOauthDto> getCurrentUser(
      @ApiIgnore @CurrentUser UserPrincipal userPrincipal) {
    log.info("getCurrentUser: userPrincipal - {}", userPrincipal);
    UserOauthDto user = null;
    try {
      user = userService.getOauthUserByOauthId(userPrincipal.getEmail());
      return ApiResponseDto.success(user);
    } catch (Exception e) {
      log.error("[getCurrentUser] ", e);
    }
    return ApiResponseDto.fail(user, "사용자 정보를 가져오는데 실패했습니다.");
  }

  @ApiOperation(value = "닉네임 중복검사", notes = "해당 닉네임이 사용중인지 검사합니다.", response = ApiResponseDto.class)
  @ApiResponses({@ApiResponse(code = 200, message = "성공"),
      @ApiResponse(code = 401, message = "인증 실패"), @ApiResponse(code = 404, message = "페이지 없음"),
      @ApiResponse(code = 500, message = "서버 오류")})
  @GetMapping("{nickname}")
  public ApiResponseDto<Boolean> isValidNickname(@PathVariable("nickname") String nickname) {
    Boolean isValid = false;
    try {
      UserSearchDto userSearchDto = userService.getUserSearchDtoByNickname(nickname);
      if (userSearchDto != null) {
        return ApiResponseDto.fail(isValid, nickname + "은 이미 사용중인 닉네임입니다.");
      }

    } catch (Exception e) {
      log.error("[isValidNickname] ", e);
    }
    isValid = true;
    return ApiResponseDto.success(isValid);
  }

  @ApiOperation(value = "사용자 정보 변경", notes = "닉네임으로 사용자 정보를 변경합니다.", response = ApiResponseDto.class)
  @ApiResponses({@ApiResponse(code = 200, message = "성공"),
      @ApiResponse(code = 401, message = "인증 실패"), @ApiResponse(code = 404, message = "페이지 없음"),
      @ApiResponse(code = 500, message = "서버 오류")})
  @PreAuthorize("hasRole('USER')")
  @PutMapping("{oauthId}")
  public ApiResponseDto<UserOauthDto> updateUserByoauthId(@PathVariable("oauthId") String oauthId,
      @RequestBody UserOauthDto userOauthDto) {
    UserOauthDto updateUser = null;
    log.info("[updateUserByoauthId] oauthId: {}, userBaseDto:{}", oauthId, userOauthDto);
    try {
      updateUser = userService.updateUserByOauthId(oauthId, userOauthDto);
      return ApiResponseDto.success(updateUser);
    } catch (Exception e) {
      log.error("[updateUserByoauthId] ", e);
    }
    return ApiResponseDto.fail(updateUser, "사용자 정보 변경에 실패했습니다.");
  }
}
