package com.ssafy.gumison.api.response;

import com.ssafy.gumison.common.dto.UserRankDto;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRankListRes {

  List<UserRankDto> userRankList;

  private UserRankListRes(List<UserRankDto> userRankDtoList) {
    this.userRankList = userRankDtoList;
  }

  public static UserRankListRes of(List<UserRankDto> userRankDtoList) {
    return new UserRankListRes(userRankDtoList);
  }
}
