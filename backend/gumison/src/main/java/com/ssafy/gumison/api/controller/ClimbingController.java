package com.ssafy.gumison.api.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ssafy.gumison.api.response.ClimbingRes;
import com.ssafy.gumison.api.response.ClimbingSolutionRes;
import com.ssafy.gumison.api.response.SolutionDto;
import com.ssafy.gumison.api.service.ClimbingService;
import com.ssafy.gumison.common.response.ApiResponseDto;
import com.ssafy.gumison.db.entity.Climbing;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/climbing")
@RequiredArgsConstructor
public class ClimbingController {
  
  private final ClimbingService climbingService;

  @GetMapping("/search/{keyword}/{pageNumber}")
  public ApiResponseDto<List<Climbing>> searchClimbingList(@PathVariable("keyword") String climbingName, @PathVariable int pageNumber) {
    List<Climbing> climbingList = climbingService.getClimbingList(climbingName, pageNumber);
    log.info("Get user climbing list - {}", climbingList);
    return ApiResponseDto.success(climbingList);
  }
  
  
  @GetMapping("/detail/{climbingId}")
  public ApiResponseDto<ClimbingRes> getClimbing(@PathVariable String climbingId) {
    ClimbingRes climbingRes = climbingService.getClimbing(climbingId);
    return ApiResponseDto.success(climbingRes);
  }

  @GetMapping("/detail/{climbingId}/{pageNumber}")
  public ApiResponseDto<ClimbingSolutionRes> getLevelSolution(@PathVariable String climbingId, @PathVariable int pageNumber) {
    ClimbingSolutionRes climbingSolutionRes = climbingService.getLevelSolution(climbingId, pageNumber);
    return ApiResponseDto.success(climbingSolutionRes);
  }
  
  
  
}
