package com.ssafy.gumison.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ssafy.gumison.api.response.SolutionListRes;
import com.ssafy.gumison.api.response.SolutionRes;
import com.ssafy.gumison.api.response.UserHistoryRes;
import com.ssafy.gumison.api.service.HistoryService;
import com.ssafy.gumison.common.response.ApiResponseDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/history")
@RestController
@RequiredArgsConstructor
public class HistoryController {
  private final HistoryService historyService;

  @ApiOperation(value = "유저 히스토리", notes = "히스토리를 보여줍니다.", response = ApiResponseDto.class)
  @GetMapping("/{nickname}")
  public ApiResponseDto<UserHistoryRes> userHistory(@PathVariable("nickname") String nickname) {
    UserHistoryRes historyRes = historyService.history(nickname);
    return ApiResponseDto.success(historyRes);
  }

  @GetMapping("/{nickname}/{pageNumber}")
  public ApiResponseDto<SolutionListRes> solutionList(@PathVariable("nickname") String nickname,
      @PathVariable("pageNumber") int pageNumber) {
    SolutionListRes solutionListRes = historyService.solutionList(nickname, pageNumber);
    return ApiResponseDto.success(solutionListRes);
  }

  @GetMapping("/detail/{solutionId}")
  public ApiResponseDto<SolutionRes> getSoultion(@PathVariable("solutionId") String solutionId) {
    SolutionRes solutionRes = historyService.solution(solutionId);
    System.out.println(solutionRes.getNickname()+solutionRes.getTier());
    System.out.println(solutionRes.getSolutionDto().toString());
    return ApiResponseDto.success(solutionRes);
  }
}
