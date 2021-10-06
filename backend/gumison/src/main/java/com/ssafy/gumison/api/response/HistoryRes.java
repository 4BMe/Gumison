package com.ssafy.gumison.api.response;

import java.util.List;
import com.ssafy.gumison.common.dto.HistoryUserDto;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("UserHistoryResponse")
public class HistoryRes {

  private HistoryUserDto user;
  private List<SolutionListItem> solutionList;
  private int solutionLastPage;

  public static HistoryRes of(HistoryUserDto user, List<SolutionListItem> list,
      int solutionLastPage) {
    HistoryRes res = new HistoryRes();
    res.setUser(user);
    res.setSolutionList(list);
    res.setSolutionLastPage(solutionLastPage);
    return res;
  }
}
