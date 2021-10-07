package com.ssafy.gumison.common.util;

import com.ssafy.gumison.api.request.SolutionRequest;
import com.ssafy.gumison.api.service.HistoryService;
import com.ssafy.gumison.db.entity.LevelTier;
import com.ssafy.gumison.db.entity.User;
import com.ssafy.gumison.db.repository.LevelTierRepository;
import com.ssafy.gumison.db.repository.UserRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DummyUserGenerator {

  private final UserRepository userRepository;

  private final HistoryService historyService;

  private final LevelTierRepository levelTierRepository;

  private final Random random = new Random();

  private final String DUMMY_KEY = "DUMMY";

  private final String[] DUMMY_NICKNAME_1 = {
      "이상한", "불타는", "볼빨간", "새침한", "나는야"
      , "새빨간", "차가운", "빛나는", "엄청난", "달콤한"
      , "반짝이는", "끈질긴", "귀여운", "로맨틱", "다정한"
      , "믿음직한", "침착한", "멍청한", "끝내주는", "친절한"
      , "영리한", "듬직한", "녹슨", "어두운", "화가난"
      , "우우웅", "새로운", "마지막", "다시한번"
      , "미운", "슬기로운", "검은", "파란", "노란"
      , "매콤한", "따끔한", "다시없을", "신나는", "최고의"
      , "자전거탄", "우산쓴", "장화신은", "슬픈", "배고픈"
      , "3대550", "3대550", "편안한"
  };

  private final String[] DUMMY_NICKNAME_2 = {
      "그늘", "공무원", "청바지", "사과", "거미손", "홀드"
      , "딸기", "문제", "기록", "바나나", "도토리", "시작"
      , "세탁기", "체리", "토마토", "수제비", "식탁", "사탕"
      , "비", "화장실", "변기", "별", "감자", "피카츄"
      , "백수", "공주", "참새", "휴가", "키보드", "고추장"
      , "애기", "라이언", "춘식이", "낙지", "칼국수", "거위"
      , "추억", "사랑", "이별", "만남", "떡볶이", "치킨"
      , "사인", "바보", "코털", "클라이밍", "곰돌이", "자전거"
      , "풍경", "바위", "파인애플", "문어", "고양이", "라면"
      , "너구리", "손수건", "겨울"
  };

  private final String[] DUMMY_DESCRIPTION_1 = {
      "안녕하세요 ", "", "반갑습니다 ", "나는 ", "ㅎㅇㅎㅇ "
      , "안녕! ", "", "나는 ", "신난다 ", "야호 ", "우우웅"
  };

  private final String[] DUMMY_DESCRIPTION_2 = {
      "입니다 ", "", "야 ", "에요 ", "의 프로필", " 짱이야", "", "",
  };

  private final String DUMMY_OAUTH_SUFFIX = "D";

  private final int LEVEL_TIER_CORD_MAX = 337;

  public void generateUser() {

    int userCount = 180;
    int nickname1Size = DUMMY_NICKNAME_1.length;
    int nickname2Size = DUMMY_NICKNAME_2.length;
    int description1Size = DUMMY_DESCRIPTION_1.length;
    int description2Size = DUMMY_DESCRIPTION_2.length;

    for (int i = 1; i <= userCount; i++) {
//      String nickname = generateNickname() +"D";
//      Long exp = (long) random.nextInt(62881063);
//      Long tier = (long) random.nextInt(24) + 201;
//      Integer video =  random.nextInt(250);

      String nickname = DUMMY_NICKNAME_1[random.nextInt(nickname1Size)] +
          DUMMY_NICKNAME_2[random.nextInt(nickname2Size)];

      System.out.println(nickname);

      String description = DUMMY_DESCRIPTION_1[random.nextInt(description1Size)] + nickname
          + DUMMY_DESCRIPTION_2[random
          .nextInt(description2Size)];

      System.out.println(description);
      System.out.println(i);

      String dummyOAuthId = generateOAuthId() + DUMMY_OAUTH_SUFFIX;
      try {
        userRepository.save(
            User.builder().nickname(nickname).accumulateExp(0L).tierCode(201L)
                .oauthId(dummyOAuthId)
                .accumulateVideo(0)
                .oauthType(DUMMY_KEY).description(description)
                .build());
      } catch (Exception e) {
        continue;
      }


      generateSolution(dummyOAuthId);
    }
  }

  private void generateSolution(String oauthId) {
    final int MAX_SOLUTION_SIZE = 25;
    final int MAX_LEVEL_TIER_SIZE = 25;
    final int MAX_COUNT_SIZE = 6;

    int solutionSize = random.nextInt(MAX_SOLUTION_SIZE);
    if (solutionSize % 2 == 0) {
      solutionSize *= 2;
    }

    for (int i = 0; i < solutionSize; i++) {
      List<Integer> countList = new ArrayList<>();
      List<Long> levelTierIdList = new ArrayList<>();
      int levelTierSize = random.nextInt(MAX_LEVEL_TIER_SIZE) + 1;

      if (levelTierSize % 2 == 0) {
        levelTierSize *= 2;
      }

      Long randomDays = (long) random.nextInt(35);
      Long levelTierRandomVariable = (long) random.nextInt(LEVEL_TIER_CORD_MAX) + 1;

      LevelTier levelTier = levelTierRepository.findById(levelTierRandomVariable)
          .orElseThrow(RuntimeException::new);

      Long ClimbingId = levelTier.getClimbing().getId();

      for (int j = 1; j < levelTierSize; j++) {
        levelTierRandomVariable = (long) random.nextInt(LEVEL_TIER_CORD_MAX) + 1;

        levelTier = levelTierRepository.findById(levelTierRandomVariable)
            .orElseThrow(RuntimeException::new);

        if (!levelTier.getClimbing().getId().equals(ClimbingId)) {

          continue;
        }

        int count = random.nextInt(MAX_COUNT_SIZE) + 1;
        countList.add(count);
        levelTierIdList.add(levelTier.getId());
      }

      if (levelTierIdList.isEmpty()) {
        continue;
      }

      SolutionRequest solutionRequest = SolutionRequest.builder().climbingId(ClimbingId)
          .date(LocalDate.now().minusDays(randomDays))
          .levelTierIds(levelTierIdList).counts(countList).oauthId(oauthId).build();
      historyService.createSolution(solutionRequest);
    }

//

  }

  private String generateOAuthId() {
    int leftLimit = '0'; // letter 'a'
    int rightLimit = '9'; // letter 'z'
    int targetStringLength = 10;
    return random.ints(leftLimit, rightLimit + 1)
        .limit(targetStringLength)
        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
        .toString();
  }
}
