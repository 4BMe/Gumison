package com.ssafy.gumison.api.request;

import java.time.LocalDate;
import java.util.List;

import com.ssafy.gumison.common.dto.ContributionDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ContributionRequest {
  List<ContributionDto> contributionData;
  private String nickname;
  private LocalDate date;
}
