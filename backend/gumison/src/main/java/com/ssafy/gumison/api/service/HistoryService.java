package com.ssafy.gumison.api.service;

import java.util.List;

import org.springframework.core.io.Resource;

import com.ssafy.gumison.api.request.SolutionRequest;
import com.ssafy.gumison.api.response.HistoryRes;
import com.ssafy.gumison.api.response.SolutionListRes;
import com.ssafy.gumison.api.response.SolutionRes;
import com.ssafy.gumison.db.entity.Solution;

import reactor.core.publisher.Mono;

public interface HistoryService {
  public HistoryRes history(String nickname);

  public SolutionListRes solutionList(String nickname, int pageNumber);

  public SolutionRes solution(String solutionId);

  public List<Solution> createSolution(SolutionRequest solutionRequest);

  public List<Solution> updateSolution(SolutionRequest solutionRequest);
}