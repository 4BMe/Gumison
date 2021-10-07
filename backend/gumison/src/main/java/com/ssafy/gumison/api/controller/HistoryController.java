package com.ssafy.gumison.api.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.ssafy.gumison.api.request.SolutionRequest;
import com.ssafy.gumison.api.response.HistoryRes;
import com.ssafy.gumison.api.response.SolutionListRes;
import com.ssafy.gumison.api.response.SolutionRes;
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
  private final String videoPath = "home/video/";

  @ApiOperation(value = "유저 히스토리", notes = "히스토리를 보여줍니다.", response = ApiResponseDto.class)
  @GetMapping("/{nickname}")
  public ApiResponseDto<HistoryRes> userHistory(@PathVariable("nickname") String nickname) {
    HistoryRes historyRes = historyService.history(nickname);
    log.info(historyRes.getUser().getNickname());
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
  public ApiResponseDto<List<Solution>> createSolution(
      @ModelAttribute SolutionRequest solutionRequest) {
    log.info("[createSolution] - HistoryController : {}", solutionRequest);
    List<Solution> solutions = historyService.createSolution(solutionRequest);
    return ApiResponseDto.success(solutions);
  }

  @ApiOperation(value = "레벨 기록 수정", notes = "성공한 레벨 기록을 수정합니다.", response = ApiResponseDto.class)
  @PutMapping("/")
  public ApiResponseDto<List<Solution>> updateSolution(
      @ModelAttribute SolutionRequest solutionRequest) {
    log.info("[updateSolution] - HistoryController : {}", solutionRequest);
    List<Solution> solutions = historyService.updateSolution(solutionRequest);
    return ApiResponseDto.success(solutions);
  }

  @ApiOperation(value = "레벨 기록 삭제", notes = "레벨 기록을 삭제합니다.", response = ApiResponseDto.class)
  @DeleteMapping("/{uploadId}")
  public ApiResponseDto<Integer> deleteSolution(@PathVariable String uploadId) {
    log.info("[deleteSolution] - HistoryController : {}", uploadId);
    return ApiResponseDto.success(historyService.deleteSolution(uploadId));
  }

  @ApiOperation(value = "레벨 기록 비디오", notes = "성공한 레벨 기록의 영상을 반환합니다.", response = ApiResponseDto.class)
  @GetMapping("/videos")
  public ResponseEntity<StreamingResponseBody> stream(HttpServletRequest req,
      @RequestParam("fileName") String fileName) throws Exception {
    log.info("[stream] : {}", fileName);
    File file = new File(videoPath + fileName);
    final InputStream is = new FileInputStream(file);
    StreamingResponseBody stream = os -> {
      readAndWrite(is, os);
    };
    final HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "video/mp4");
    return ResponseEntity.ok().headers(headers).body(stream);
  }

  private void readAndWrite(final InputStream is, OutputStream os) throws IOException {
    byte[] data = new byte[2048];
    int read = 0;
    while ((read = is.read(data)) > 0) {
      os.write(data, 0, read);
    }
    os.flush();
  }
}
