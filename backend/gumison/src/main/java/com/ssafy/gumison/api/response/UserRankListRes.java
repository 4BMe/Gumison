package com.ssafy.gumison.api.response;

import com.ssafy.gumison.common.dto.UserRankDto;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
public class UserRankListRes {

  List<UserRankDto> userRankList;

  Long lastPageNumber;

  private UserRankListRes(List<UserRankDto> userRankDtoList, Long lastPageNumber) {
    this.userRankList = userRankDtoList;
    this.lastPageNumber = lastPageNumber;
  }

  public static UserRankListRes of(List<UserRankDto> userRankDtoList, Long lastPageNumber) {
    return new UserRankListRes(userRankDtoList, lastPageNumber);
  }
}
