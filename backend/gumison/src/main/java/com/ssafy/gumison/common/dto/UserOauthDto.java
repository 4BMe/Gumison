package com.ssafy.gumison.common.dto;

import com.ssafy.gumison.db.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class UserOauthDto implements Serializable {

  private String nickname;
  private String description;
  private String oAuthId;
  private String profile;
  private String oAuthType;

}
