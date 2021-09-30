package com.ssafy.gumison.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.gumison.api.request.SolutionRequest;
import com.ssafy.gumison.api.response.SolutionListRes;
import com.ssafy.gumison.api.response.SolutionRes;
import com.ssafy.gumison.api.response.UserHistoryRes;
import com.ssafy.gumison.api.service.HistoryService;
import com.ssafy.gumison.common.response.ApiResponseDto;
import com.ssafy.gumison.db.entity.Solution;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/api/history")
@RestController
@RequiredArgsConstructor
@Slf4j
public class HistoryController {
  private final HistoryService historyService;

  @ApiOperation(value = "유저 히스토리", notes = "히스토리를 보여줍니다.", response = ApiResponseDto.class)
  @GetMapping("/{nickname}")
  public ApiResponseDto<UserHistoryRes> userHistory(@PathVariable("nickname") String nickname) {
    UserHistoryRes historyRes = historyService.history(nickname);
    log.info(historyRes.getNickname());
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
    return ApiResponseDto.success(solutionRes);
  }

  @ApiOperation(value = "레벨 기록 생성", notes = "성공한 레벨 기록을 생성합니다.", response = ApiResponseDto.class)
  @PostMapping("/")
  public ApiResponseDto<Solution> createSolution(@RequestBody SolutionRequest solutionRequest) {
    log.info("[createSolution] : " + solutionRequest);
    Solution solution = historyService.createSolution(solutionRequest);
    return ApiResponseDto.success(solution);
  }

  @ApiOperation(value = "레벨 기록 수정", notes = "성공한 레벨 기록을 수정합니다.", response = ApiResponseDto.class)
  @PutMapping("/{solutionId}")
  public ApiResponseDto<Solution> updateSolution(@PathVariable String solutionId,
      @RequestBody SolutionRequest solutionRequest) {
    Solution solution = historyService.updateSolution(solutionId, solutionRequest);
    return ApiResponseDto.success(solution);
  }
}
