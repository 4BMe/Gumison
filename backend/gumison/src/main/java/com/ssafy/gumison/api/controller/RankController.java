package com.ssafy.gumison.api.controller;

import com.ssafy.gumison.api.response.UserRankListRes;
import com.ssafy.gumison.api.response.UserRankRes;
import com.ssafy.gumison.api.service.RankService;
import com.ssafy.gumison.api.service.UserService;
import com.ssafy.gumison.common.dto.UserRankDto;
import com.ssafy.gumison.common.dto.UserSearchDto;
import com.ssafy.gumison.common.response.ApiResponseDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/rank")
@RequiredArgsConstructor
public class RankController {

  private final RankService rankService;

  private final UserService userService;

  @ApiOperation(value = "닉네임으로 랭크 불러오기", notes = "닉네임으로 순위를 불러옵니다.")
  @ApiResponses({
      @ApiResponse(code = 200, message = "성공"),
      @ApiResponse(code = 404, message = "사용자 닉네임 없음"),
      @ApiResponse(code = 500, message = "서버 오류")}
  )
  @GetMapping("/detail/{nickname}")
  public ApiResponseDto<UserRankRes> getUserRank(@PathVariable("nickname") String nickname) {
    UserRankDto userRankDto = rankService.getUserRankByNickname(nickname);
    userRankDto.setUserSearchDto(userService.getUserSearchDtoByNickname(nickname));
    return ApiResponseDto.success(UserRankRes.of(userRankDto));
  }

  @ApiOperation(value = "닉네임으로 랭크 불러오기", notes = "닉네임으로 순위를 불러옵니다.")
  @ApiResponses({
      @ApiResponse(code = 200, message = "성공"),
      @ApiResponse(code = 400, message = "잘못된 페이지 요청"),
      @ApiResponse(code = 500, message = "서버 오류")}
  )
  @GetMapping("/{page}")
  public ApiResponseDto<UserRankListRes> getUserRankListByPage(@PathVariable("page") Integer page) {
    List<UserRankDto> userRankDtoList = rankService.getUserRankByPage(page);

    userRankDtoList.forEach(userRankDto ->{
          userRankDto
              .setUserSearchDto(userService.getUserSearchDtoByNickname(userRankDto.getNickname()));
        });

    Long lastPageNumber = rankService.getMaxPageCount();
    return ApiResponseDto.success(UserRankListRes.of(userRankDtoList, lastPageNumber));
  }

}
