package com.ssafy.gumison.common.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class LevelTierDto {

  private Long id;
  private String level;
  private String tier;
}
