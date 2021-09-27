package com.ssafy.gumison.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ssafy.gumison.api.response.UserSearchRes;
import com.ssafy.gumison.api.service.UserService;
import com.ssafy.gumison.common.response.ApiResponseDto;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/user")
@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  
  @GetMapping("search/{keyword}/{pageNumber}")
  public ApiResponseDto<UserSearchRes> searchUsers(@PathVariable("keyword") String nickname, @PathVariable int pageNumber) {
    UserSearchRes userSearchRes = userService.getUserList(nickname, pageNumber);
    return ApiResponseDto.success(userSearchRes);
  }
  
}
