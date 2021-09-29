package com.ssafy.gumison.api.controller;

import com.ssafy.gumison.common.dto.SessionUserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ssafy.gumison.api.response.UserSearchRes;
import com.ssafy.gumison.api.service.UserService;
import com.ssafy.gumison.common.response.ApiResponseDto;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpSession;

@Slf4j
@Api(value = "사용자 관련 API")
@RequestMapping("/api/users")
@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final HttpSession httpSession;

  @GetMapping("search/{keyword}/{pageNumber}")
  public ApiResponseDto<UserSearchRes> searchUsers(@PathVariable("keyword") String nickname,
      @PathVariable int pageNumber) {
    UserSearchRes userSearchRes = userService.getUserList(nickname.trim(), pageNumber);
    return ApiResponseDto.success(userSearchRes);
  }


  @GetMapping("/oauth2/login/google")
  @ApiOperation(value = "소셜 로그인", notes = "구글 로그인을 합니다.")
  @ApiResponses({@ApiResponse(code = 200, message = "성공"),
      @ApiResponse(code = 401, message = "인증 실패"), @ApiResponse(code = 404, message = "페이지 없음"),
      @ApiResponse(code = 500, message = "서버 오류")})
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<SessionUserDto> getCurrentUser() {
    log.info("getCurrentUser!!!");
    SessionUserDto sessionUserDto = userService.getCurrentUser(httpSession);
    log.info("Get user profile - {}", sessionUserDto);
    return ResponseEntity.status(200).body(sessionUserDto);
  }

}
