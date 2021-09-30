package com.ssafy.gumison.api.controller;

import com.ssafy.gumison.api.response.ClimbingRes;
import com.ssafy.gumison.api.response.ClimbingSolutionRes;
import com.ssafy.gumison.api.service.ClimbingService;
import com.ssafy.gumison.api.service.RankService;
import com.ssafy.gumison.api.service.RankServiceImpl;
import com.ssafy.gumison.common.response.ApiResponseDto;
import com.ssafy.gumison.db.entity.Climbing;
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

  @GetMapping("/detail/{nickname}")
  public ApiResponseDto<?> searchClimbingList(
      @PathVariable("nickname") String nickname) {
    return ApiResponseDto.success(null);
  }


}
