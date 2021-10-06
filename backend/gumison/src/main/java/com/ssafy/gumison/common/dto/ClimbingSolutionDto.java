package com.ssafy.gumison.common.dto;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class ClimbingSolutionDto {
  private Long id;

  private String nickname;

  private String level;

  private String tier;

  private Integer count;

  private LocalDate date;

}
