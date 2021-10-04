package com.ssafy.gumison.api.service;

import com.ssafy.gumison.api.request.ContributionRequest;
import com.ssafy.gumison.db.entity.Contribution;

public interface ContributionService {
  public Contribution createContribution(ContributionRequest contributionRequest);
}
