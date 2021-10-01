package com.ssafy.gumison.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class ContributionRequest {
  private Long levelTierId;
  private String newTier;
  private String oldTier;
  private Long userId;
}
