package com.ssafy.gumison.api.response;

import com.ssafy.gumison.common.dto.UserRankDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class UserRankRes {

  UserRankDto userRank;

  public static UserRankRes of(UserRankDto userRankDto) {
    return new UserRankRes(userRankDto);
  }
}
