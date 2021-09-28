package com.ssafy.gumison.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/*
  User nickname과 exp만을 저장하는 Dto
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserExpDto {

  private String nickname;
  private Long accumulateExp;
}
