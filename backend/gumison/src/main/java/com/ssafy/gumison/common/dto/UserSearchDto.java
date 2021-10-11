package com.ssafy.gumison.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserSearchDto {

  protected String nickname;
  protected String profile;
  protected String tier;
  protected long solCnt;

}
