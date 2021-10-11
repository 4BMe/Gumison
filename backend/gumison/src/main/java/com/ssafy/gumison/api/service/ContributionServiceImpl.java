package com.ssafy.gumison.api.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
  public List<Contribution> createContribution(ContributionRequest contributionRequest) {
    log.info("[createContribution] - ContributionService : {}", contributionRequest);
    List<Contribution> contributions = new ArrayList<>();
    for (int i = 0; i < contributionRequest.getContributionData().size(); i++) {
      LevelTier levelTier = levelTierRepository
          .findById(contributionRequest.getContributionData().get(i).getLevelTierId())
          .orElseThrow(RuntimeException::new);
      String newTier = contributionRequest.getContributionData().get(i).getNewTier();
      String oldTier = contributionRequest.getContributionData().get(i).getOldTier();
      User user = userRepository.findByNickname(contributionRequest.getNickname())
          .orElseThrow(RuntimeException::new);
      LocalDate today = LocalDate.now();
      if (!oldTier.equals(newTier)) {
        Contribution contribution = Contribution.builder().levelTier(levelTier).newTier(newTier)
            .oldTier(oldTier).user(user).date(today).build();
        contributions.add(contribution);
      }
    }
    List<Contribution> ret = contributionRepository.saveAll(contributions);
    return ret;
  }

}
