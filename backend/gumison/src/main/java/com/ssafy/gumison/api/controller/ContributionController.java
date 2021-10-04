package com.ssafy.gumison.api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.gumison.api.request.ContributionRequest;
import com.ssafy.gumison.api.service.ContributionService;
import com.ssafy.gumison.common.response.ApiResponseDto;
import com.ssafy.gumison.db.entity.Contribution;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/api/contribution")
@RestController
@RequiredArgsConstructor
@Slf4j
public class ContributionController {

  private final ContributionService contributionService;

  @ApiOperation(value = "레벨 기여 생성", notes = "레벨 기여를 생성합니다.", response = ApiResponseDto.class)
  @PostMapping("/")
  public ApiResponseDto<Contribution[]> createContribution(
      @RequestBody ContributionRequest[] contributionRequests) {
    log.info("[createContribution] - ContributionController : {}", contributionRequests);
    Contribution[] contributions = new Contribution[contributionRequests.length];
    for (int i = 0; i < contributionRequests.length; i++) {
      Contribution contribution = contributionService.createContribution(contributionRequests[i]);
      contributions[i] = contribution;
    }
    return ApiResponseDto.success(contributions);
  }
}
