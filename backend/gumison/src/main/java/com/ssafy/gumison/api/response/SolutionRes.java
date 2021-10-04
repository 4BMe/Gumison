package com.ssafy.gumison.api.response;

import com.ssafy.gumison.common.dto.SolutionDto;
import com.ssafy.gumison.db.entity.Solution;
import com.ssafy.gumison.db.entity.User;
import lombok.Getter;

@Getter
public class SolutionRes {
  private String profile;
  private String nickname;
  private String tier;
  private SolutionDto solution;

  public static SolutionRes of(User user, String tier, Solution solution, String tierName,
      String levelName) {
    SolutionRes res = new SolutionRes();
    res.profile = user.getProfile();
    res.nickname = user.getNickname();
    res.tier = tier;
    res.solution = SolutionDto.builder().climbingName(solution.getClimbing().getClimbingName())
        .level(levelName).tier(tierName).count(solution.getCount()).date(solution.getDate())
        .build();
    return res;
  }
}
