package com.ssafy.gumison.api.controller;

import com.ssafy.gumison.api.response.UserRankListRes;
import com.ssafy.gumison.api.response.UserRankRes;
import com.ssafy.gumison.api.service.RankService;
import com.ssafy.gumison.api.service.UserService;
import com.ssafy.gumison.common.dto.UserRankDto;
import com.ssafy.gumison.common.dto.UserSearchDto;
import com.ssafy.gumison.common.exception.ResourceNotFoundException;
import com.ssafy.gumison.common.response.ApiResponseDto;
import com.ssafy.gumison.redis.UserRankSearchKeywordRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 유저 경험치 순위 API
 *
 * @author cherrytomato1
 * @version 1.1 키워드 검색
 */

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/rank")
public class RankController {

  private final RankService rankService;
  private final UserService userService;
  private final UserRankSearchKeywordRepository userRankSearchKeywordRepository;

  @ApiOperation(value = "닉네임으로 랭크 불러오기", notes = "닉네임으로 순위를 불러옵니다.")
  @ApiResponses({
      @ApiResponse(code = 200, message = "성공"),
      @ApiResponse(code = 404, message = "사용자 닉네임 없음"),
      @ApiResponse(code = 500, message = "서버 오류")}
  )
  @Transactional
  @GetMapping("/detail/{nickname}")
  public ApiResponseDto<UserRankRes> getUserRank(@PathVariable("nickname") String nickname) {
    UserRankDto userRankDto = rankService.getUserRankByNickname(nickname);
    try {
      userRankDto.setUserSearchDto(userService.getUserSearchDtoByNickname(nickname));
    } catch (ResourceNotFoundException e) {
      rankService.deleteUserRankByNickname(userRankDto.getNickname());
      throw e;
    }
    return ApiResponseDto.success(UserRankRes.of(userRankDto));
  }

  @ApiOperation(value = "페이지에 해당하는 유저 랭킹 정보 리스트 출력", notes = "페이지를 입력하면 해당 페이지에 출력할 사용자 정보를 반환합니다.")
  @ApiResponses({
      @ApiResponse(code = 200, message = "성공"),
      @ApiResponse(code = 400, message = "잘못된 페이지 요청"),
      @ApiResponse(code = 500, message = "서버 오류")}
  )
  @Transactional
  @GetMapping("/{page}")
  public ApiResponseDto<UserRankListRes> getUserRankListByPage(@PathVariable("page") Integer page) {
    List<UserRankDto> userRankDtoList = rankService.getUserRankByPage(page);

    userRankDtoList.forEach(userRankDto -> {
      UserSearchDto userSearchDto;
      try {
        userSearchDto = userService.getUserSearchDtoByNickname(userRankDto.getNickname());
      } catch (ResourceNotFoundException e) {
        //탈퇴, 닉네임 변경 등의 이유로 랭킹에는 존재하나 DB에 없는 사용자 랭킹에서 삭제
        rankService.deleteUserRankByNickname(userRankDto.getNickname());
        return;
      }
      userRankDto.setUserSearchDto(userSearchDto);
    });

    Long lastPageNumber = rankService.getMaxPageCount();

    return ApiResponseDto.success(UserRankListRes.of(userRankDtoList, lastPageNumber));
  }

  @ApiOperation(value = "키워드와 페이지에 해당하는 유저 랭킹 정보 리스트 출력", notes = "키워드와 페이지를 입력하면 해당 페이지에 출력할 사용자 정보를 반환합니다.")
  @ApiResponses({
      @ApiResponse(code = 200, message = "성공"),
      @ApiResponse(code = 400, message = "잘못된 페이지 요청"),
      @ApiResponse(code = 500, message = "서버 오류")}
  )
  @GetMapping("/search/{keyword}/{page}")
  public ApiResponseDto<UserRankListRes> getUserRankListByKeywordAndPage(
      @PathVariable("keyword") String keyword, @PathVariable("page") Integer page) {

    Long lastPageNumber;

    try {
      lastPageNumber = userRankSearchKeywordRepository.getUserSearchKeywordCount(keyword);
    } catch (RuntimeException e) {
      lastPageNumber =
          userService.getUserCountByKeyword(keyword) / rankService.getUserSizePerPage() + 1;
      userRankSearchKeywordRepository.setUserSearchKeywordCount(keyword, lastPageNumber);
    }

    if (page > lastPageNumber) {
      throw new IllegalArgumentException("invalid page number : " + page);
    }

    List<UserSearchDto> userSearchDtoList = userService.getUserList(keyword, page - 1).getUsers();

    List<UserRankDto> userRankDtoList = new ArrayList<>(userSearchDtoList.size());
    userSearchDtoList.forEach(userSearchDto -> {
      Long rank = rankService.getUserRankByNickname(userSearchDto.getNickname()).getRank();
      userRankDtoList.add(UserRankDto.of(userSearchDto, rank));
    });

    return ApiResponseDto.success(UserRankListRes.of(userRankDtoList, lastPageNumber));
  }
}
