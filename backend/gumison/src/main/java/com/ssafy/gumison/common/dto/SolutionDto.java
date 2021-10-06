package com.ssafy.gumison.common.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.ssafy.gumison.db.entity.SolutionVideo;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
public class SolutionDto {
  private Long climbingId;
  private String climbingName;
  private List<Long> solutionIds;
  private List<String> level;
  private List<String> tier;
  private List<SolutionVideo> solutionVideoList;
  private List<Integer> counts;
  private LocalDate date;

}
