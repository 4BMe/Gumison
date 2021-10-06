package com.ssafy.gumison.api.request;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SolutionRequest {

  private Long solutionId;
  private String oauthId;
  private Long climbingId;
  private Integer accumulateReport;
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate date;
  private Boolean deleteYn;
  private List<Long> levelTierIds;
  private List<Integer> counts;
  private List<MultipartFile> videos;
}
