package com.ssafy.gumison.api.response;

import java.util.List;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("SolutionListResponse")
public class SolutionListRes {
  private List<SolutionListItem> solutionList;

  public static SolutionListRes of(List<SolutionListItem> list) {
    SolutionListRes res = new SolutionListRes();
    res.solutionList = list;
    return res;
  }
}
