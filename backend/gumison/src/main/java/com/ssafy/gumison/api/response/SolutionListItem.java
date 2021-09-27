package com.ssafy.gumison.api.response;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SolutionListItem {
  private int tier;
  private String climbingName;
  private int level;
  private int cnt;
  private LocalDate date;
}
