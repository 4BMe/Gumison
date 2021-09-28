package com.ssafy.gumison.common.dto;

import com.ssafy.gumison.db.entity.User;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@ToString
public class SessionUserDto implements Serializable {

  private String oAuthId;
  private String profile;
  private String oAuthType;

  public SessionUserDto(User user) {
    this.oAuthId = user.getOauthId();
    this.profile = user.getProfile();
    this.oAuthType = user.getOauthType();
  }
}
