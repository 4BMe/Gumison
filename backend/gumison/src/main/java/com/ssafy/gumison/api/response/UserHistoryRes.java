package com.ssafy.gumison.api.response;

import java.util.List;
import com.ssafy.gumison.db.entity.User;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("UserHistoryResponse")
public class UserHistoryRes {

  private String profile;
  private String nickname;
  private String description;
  private String tier;
  private Long exp;
  private List<SolutionListItem> solutionList;

  public static UserHistoryRes of(User user, String tier, Long exp, List<SolutionListItem> list) {
    UserHistoryRes res = new UserHistoryRes();
    res.setProfile(user.getProfile());
    res.setNickname(user.getNickname());
    res.setDescription(user.getDescription());
    res.setTier(tier);
    res.setExp(exp);
    res.setSolutionList(list);
    return res;
  }
}
