package com.ssafy.gumison.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/*
  User nickname과 exp만을 전달하는 Dto
 */

@AllArgsConstructor
@Setter
@Getter
public class UserExpDto {

  private String nickname;
  private Long accumulateExp;

  public static UserExpDto of(String nickname, Long accumulateExp) {
    return new UserExpDto(nickname, accumulateExp);
  }
}
