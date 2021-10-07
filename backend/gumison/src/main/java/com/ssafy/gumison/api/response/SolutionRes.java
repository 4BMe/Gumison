package com.ssafy.gumison.api.response;

import java.util.List;

import com.ssafy.gumison.common.dto.SolutionDto;
import com.ssafy.gumison.db.entity.Solution;
import com.ssafy.gumison.db.entity.SolutionVideo;
import com.ssafy.gumison.db.entity.User;

import lombok.Getter;

@Getter
public class SolutionRes {

  private String profile;
  private String nickname;
  private String tier;
  private String uploadId;
  private SolutionDto solution;

  public static SolutionRes of(User user, String tier, Solution solution, List<String> tierNames,
      List<Long> solutionIds,
      List<String> levelNames, List<Integer> counts, List<SolutionVideo> solutionVideoList) {
    SolutionRes res = new SolutionRes();
    res.profile = user.getProfile();
    res.nickname = user.getNickname();
    res.tier = tier;
    res.uploadId = solution.getUploadId();
    res.solution = SolutionDto.builder().climbingId(solution.getClimbing().getId()).climbingName(solution.getClimbing().getClimbingName())
        .solutionIds(solutionIds).levelTierIds(levelTierIds).level(levelNames).tier(tierNames).counts(counts).date(solution.getDate())
        .solutionVideoList(solutionVideoList).build();
    return res;
  }
}
