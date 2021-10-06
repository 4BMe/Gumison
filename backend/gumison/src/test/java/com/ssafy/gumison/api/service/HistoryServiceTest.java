package com.ssafy.gumison.api.service;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.ssafy.gumison.api.request.SolutionRequest;
import com.ssafy.gumison.db.entity.User;
import com.ssafy.gumison.db.repository.LevelTierRepository;
import com.ssafy.gumison.db.repository.UserRepository;

@SpringBootTest
class HistoryServiceTest {

  @Autowired
  HistoryService historyService;

  @Autowired
  LevelTierRepository levelTierRepository;

  @Autowired
  UserRepository userRepository;

  @Test
  void 문제를_해결하면_경험치와_티어가_변경된다() {
    // given
    final String OAUTH_ID = "108411740976101103566";

    List<Integer> countList = new ArrayList<>();
    List<Long> levelTierIdList = new ArrayList<>();
    for (int i = 1; i <= 3; i++) {
      countList.add(i);
      levelTierIdList.add((long) i);
    }
    SolutionRequest solutionRequest = SolutionRequest.builder().climbingId(1L).date(LocalDate.now())
        .levelTierIds(levelTierIdList).counts(countList).oauthId(OAUTH_ID).build();

    // when
    User user = userRepository.findByOauthId(OAUTH_ID).orElseThrow(RuntimeException::new);
    Long oldExp = user.getAccumulateExp();
    historyService.createSolution(solutionRequest);

    // then
    user = userRepository.findByOauthId(OAUTH_ID).orElseThrow(RuntimeException::new);
    assertNotEquals(user.getAccumulateExp(), oldExp);
  }

  @Test
  void 문제_해결을_수정하면_경험치와_티어가_변경된다() {
    // to do
  }
}
