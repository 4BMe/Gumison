package com.ssafy.gumison.api.response;

import com.ssafy.gumison.common.dto.UserRankDto;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRankRes {

  UserRankDto userRank;

  private UserRankRes(UserRankDto userRankDto) {
    this.userRank = userRankDto;
  }

  public static UserRankRes of(UserRankDto userRankDto) {
    return new UserRankRes(userRankDto);
  }
}
