package com.ssafy.gumison.api.service;

import com.ssafy.gumison.api.request.SolutionRequest;
import com.ssafy.gumison.api.response.SolutionListRes;
import com.ssafy.gumison.api.response.SolutionRes;
import com.ssafy.gumison.api.response.HistoryRes;
import com.ssafy.gumison.db.entity.Solution;

public interface HistoryService {
  public HistoryRes history(String nickname);

  public SolutionListRes solutionList(String nickname, int pageNumber);

  public SolutionRes solution(String solutionId);

  public Solution createSolution(SolutionRequest solutionRequest);
  
  public Solution updateSolution(String solutionId, SolutionRequest solutionRequest);
}