package com.ssafy.gumison.api.service;

import org.springframework.stereotype.Service;
import com.ssafy.gumison.api.response.SolutionListRes;
import com.ssafy.gumison.api.response.SolutionRes;
import com.ssafy.gumison.api.response.UserHistoryRes;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {

  @Override
  public UserHistoryRes history(String nickname) {
    if (false) {
      return null;
    }

    return null;
  }

  @Override
  public SolutionListRes solutionList(String nickname, int pageNumber) {
    if (false) {
      return null;
    }

    return null;
  }

  @Override
  public SolutionRes solution(String solutionId) {
    if (false) {
      return null;
    }

    return null;
  }

}
