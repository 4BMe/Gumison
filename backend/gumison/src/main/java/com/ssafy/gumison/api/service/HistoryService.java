package com.ssafy.gumison.api.service;

import com.ssafy.gumison.api.response.SolutionListRes;
import com.ssafy.gumison.api.response.SolutionRes;
import com.ssafy.gumison.api.response.UserHistoryRes;

public interface HistoryService {
  public UserHistoryRes history(String nickname);

  public SolutionListRes solutionList(String nickname, int pageNumber);

  public SolutionRes solution(String solutionId);
}