package com.ssafy.gumison.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class UserRankDto {

  private String nickname;
  private Long rank;

  public static UserRankDto of(String nickname, Long rank) {
    return new UserRankDto(nickname, rank);
  }
}
