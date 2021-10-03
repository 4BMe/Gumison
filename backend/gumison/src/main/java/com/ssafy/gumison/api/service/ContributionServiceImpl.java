package com.ssafy.gumison.api.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.ssafy.gumison.api.request.ContributionRequest;
import com.ssafy.gumison.db.entity.Contribution;
import com.ssafy.gumison.db.entity.LevelTier;
import com.ssafy.gumison.db.entity.User;
import com.ssafy.gumison.db.repository.ContributionRepository;
import com.ssafy.gumison.db.repository.LevelTierRepository;
import com.ssafy.gumison.db.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContributionServiceImpl implements ContributionService {

  private final ContributionRepository contributionRepository;
  private final LevelTierRepository levelTierRepository;
  private final UserRepository userRepository;

  @Override
  public Contribution createContribution(ContributionRequest contributionRequest) {
    log.info("[createContribution] - ContributionService : {}", contributionRequest);
    LevelTier levelTier = levelTierRepository.findById(contributionRequest.getLevelTierId())
        .orElseThrow(RuntimeException::new);
    User user = userRepository.findById(contributionRequest.getUserId())
        .orElseThrow(RuntimeException::new);
    LocalDate today = LocalDate.now();
    Contribution contribution = Contribution.builder().levelTier(levelTier)
        .newTier(contributionRequest.getNewTier())
        .oldTier(contributionRequest.getOldTier()).user(user).date(today).build();
    Contribution ret = contributionRepository.save(contribution);
    return ret;
  }

}
