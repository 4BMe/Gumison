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

  private String nickname;
  private String profile;
  private String tier;
  private long solCnt;

}
