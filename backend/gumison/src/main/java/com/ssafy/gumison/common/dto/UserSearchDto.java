package com.ssafy.gumison.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@AllArgsConstructor
@ToString
public class UserSearchDto {
  
  String nickname;
  String profile;
  String tier;
  long solCnt;
  
}
