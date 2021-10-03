package com.ssafy.gumison.api.request;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SolutionRequest {
  private Long solutionId;
  private Integer accumulateReport;
  private Integer count;
  private LocalDate date;
  private Boolean deleteYn;
  private Long climbingId;
  private Long levelTierId;
  private Long userId;
}
