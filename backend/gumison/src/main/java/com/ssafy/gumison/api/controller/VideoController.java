package com.ssafy.gumison.api.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.gumison.api.request.VideoRequest;
import com.ssafy.gumison.api.service.VideoService;
import com.ssafy.gumison.common.response.ApiResponseDto;
import com.ssafy.gumison.db.entity.SolutionVideo;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/api/video")
@RestController
@RequiredArgsConstructor
@Slf4j
public class VideoController {
  private final VideoService videoService;

  @ApiOperation(value = "비디오 업로드", response = ApiResponseDto.class)
  @PostMapping("/")
  public ApiResponseDto<SolutionVideo[]> uploadVideos(@ModelAttribute VideoRequest videoRequest) {
    log.info("[uploadVideo] - VideoController : {}", videoRequest);
    SolutionVideo[] solutionVideos = videoService.uploadVideo(videoRequest);
    return ApiResponseDto.success(solutionVideos);
  }

}
