package com.ssafy.gumison.api.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ssafy.gumison.api.response.ClimbingRes;
import com.ssafy.gumison.api.response.ClimbingSolutionRes;
import com.ssafy.gumison.api.service.ClimbingService;
import com.ssafy.gumison.common.dto.SolutionDto;
import com.ssafy.gumison.common.response.ApiResponseDto;
import com.ssafy.gumison.db.entity.Climbing;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/climbing")
@RequiredArgsConstructor
public class ClimbingController {

  private final ClimbingService climbingService;

  @ApiOperation(value = "클라이밍장 검색", notes = "검색어가 포함된 클라이밍장을 반환합니다.")
  @ApiResponses({@ApiResponse(code = 200, message = "성공"),
      @ApiResponse(code = 500, message = "서버 오류")})
  @GetMapping("/search/{keyword}/{pageNumber}")
  public ApiResponseDto<List<Climbing>> searchClimbingList(
      @PathVariable("keyword") String climbingName, @PathVariable int pageNumber) {
    List<Climbing> climbingList = climbingService.getClimbingList(climbingName.trim(), pageNumber);
    log.info("Get user climbing list - {}", climbingList);
    return ApiResponseDto.success(climbingList);
  }

  @ApiOperation(value = "클라이밍장 조회", notes = "climgingId와 일치하는 클라이밍장에 관련된 정보를 반환합니다.")
  @ApiResponses({@ApiResponse(code = 200, message = "성공"),
      @ApiResponse(code = 500, message = "서버 오류")})
  @GetMapping("/detail/{climbingId}")
  public ApiResponseDto<ClimbingRes> getClimbing(@PathVariable Long climbingId) {
    ClimbingRes climbingRes = climbingService.getClimbingDetail(climbingId);
    return ApiResponseDto.success(climbingRes);
  }

  @ApiOperation(value = "클라이밍장 solution 조회",
      notes = "climgingId와 level이 모두 일치하는 클라이밍장의 solution 정보를 반환합니다.")
  @ApiResponses({@ApiResponse(code = 200, message = "성공"),
      @ApiResponse(code = 500, message = "서버 오류")})
  @GetMapping("/detail/{climbingId}/{levelTierId}/{pageNumber}")
  public ApiResponseDto<ClimbingSolutionRes> getLevelSolution(@PathVariable Long climbingId,
      @PathVariable Long levelTierId, @PathVariable int pageNumber) {
    ClimbingSolutionRes climbingSolutionRes =
        climbingService.getLevelSolution(climbingId, levelTierId, pageNumber);
    
    log.info("Get climbing solution list - {}", climbingSolutionRes);
    
    return ApiResponseDto.success(climbingSolutionRes);
  }



}
