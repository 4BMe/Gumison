package com.ssafy.gumison.api.response;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SolutionListItem {
  private Long tier;
  private String climbingName;
  private Long level;
  private int cnt;
  private LocalDate date;
}
