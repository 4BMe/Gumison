package com.ssafy.gumison.api.response;

import com.ssafy.gumison.common.dto.UserRankDto;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class UserRankListRes {

  List<UserRankDto> userRankList;

  Long lastPageNumber;

  public static UserRankListRes of(List<UserRankDto> userRankDtoList, Long lastPageNumber) {
    return new UserRankListRes(userRankDtoList, lastPageNumber);
  }
}
