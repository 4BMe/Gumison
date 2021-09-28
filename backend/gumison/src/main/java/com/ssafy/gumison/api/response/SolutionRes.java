package com.ssafy.gumison.api.response;

import com.ssafy.gumison.db.entity.Solution;
import com.ssafy.gumison.db.entity.User;
import lombok.Getter;

@Getter
public class SolutionRes {
  private String profile;
  private String nickname;
  private String tier;
  private SolutionDto solutionDto;

  public static SolutionRes of(User user, String tier, Solution solution, String tierName,
      String levelName) {
    SolutionRes res = new SolutionRes();
    res.profile = user.getProfile();
    res.nickname = user.getNickname();
    res.tier = tier;
    res.solutionDto = SolutionDto.builder().climbingName(solution.getClimbing().getClimbingName())
        .level(levelName).tier(tierName).solutionVideoList(solution.getSolutionVideoList())
        .count(solution.getCount()).date(solution.getDate().toLocalDate()).build();
    return res;
  }
}
