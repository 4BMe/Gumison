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
  private String climbingName;
  private String level;
  private String tier;
  private List<SolutionVideo> solutionVideoList = new ArrayList<>();
  private Integer count;
  private LocalDate date;

}
