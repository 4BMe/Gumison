package com.ssafy.gumison.api.service;

import java.util.List;

import com.ssafy.gumison.api.request.ContributionRequest;
import com.ssafy.gumison.db.entity.Contribution;

public interface ContributionService {
  public List<Contribution> createContribution(ContributionRequest contributionRequest);
}
