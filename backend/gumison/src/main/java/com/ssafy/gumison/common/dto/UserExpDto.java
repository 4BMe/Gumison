package com.ssafy.gumison.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/*
  User nickname과 exp만을 저장하는 Dto
 */

@AllArgsConstructor
@Getter
@Setter
public class UserExpDto {

  private String nickname;
  private Long accumulateExp;
}
