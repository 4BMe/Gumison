package com.ssafy.gumison.api.request;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
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
