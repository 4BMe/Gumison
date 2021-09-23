package com.ssafy.gumison.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ssafy.gumison.api.response.SolutionListRes;
import com.ssafy.gumison.api.response.SolutionRes;
import com.ssafy.gumison.api.response.UserHistoryRes;
import com.ssafy.gumison.api.service.HistoryService;
import lombok.RequiredArgsConstructor;

@RequestMapping("/history")
@RestController
@RequiredArgsConstructor
public class HistoryController {
  private final HistoryService historyService;

  @GetMapping("/{nickname}")
  public UserHistoryRes userHistory(@PathVariable("nickname") String nickname) {
    return historyService.history(nickname);
  }

  @GetMapping("/{nickname}/{pageNumber}")
  public SolutionListRes solutionList(@PathVariable("nickname") String nickname,
      @PathVariable("pageNumber") int pageNumber) {
    return historyService.solutionList(nickname, pageNumber);
  }

  @GetMapping("/detail/{solutionId}")
  public SolutionRes getSoultion(@PathVariable("solutionId") String solutionId) {
    return historyService.solution(solutionId);
  }
}
