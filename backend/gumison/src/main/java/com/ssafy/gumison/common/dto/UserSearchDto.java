package com.ssafy.gumison.common.dto;

import com.ssafy.gumison.db.entity.User;
import lombok.Getter;

@Getter
public class UserSearchDto {
  
  String nickname;
  String profile;
  String level;
  int solCnt;

  public UserSearchDto(User user, String level) {
    nickname = user.getNickname();
    profile = user.getProfile();
    user.getSolutionList().forEach((solution) -> {
      solCnt += solution.getCount();
    });
    
    this.level = level;
  }
  
}
