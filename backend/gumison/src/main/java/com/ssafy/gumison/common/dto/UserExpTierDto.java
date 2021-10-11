package com.ssafy.gumison.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/*
  User nickname과 exp만을 전달하는 Dto
 */

@AllArgsConstructor
@Getter
@Builder
public class UserExpTierDto {

  private final String nickname;
  private final Long accumulateExp;
  private final Long tierCode;

  public static UserExpTierDto of(String nickname, Long accumulateExp, Long tierCode) {
    return new UserExpTierDto(nickname, accumulateExp, tierCode);
  }
}
