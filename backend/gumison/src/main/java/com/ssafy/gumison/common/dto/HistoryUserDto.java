package com.ssafy.gumison.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
public class HistoryUserDto {
  private String profile;
  private String nickname;
  private String description;
  private String tier;
  private Long exp;
  private Long nextExp;

}
