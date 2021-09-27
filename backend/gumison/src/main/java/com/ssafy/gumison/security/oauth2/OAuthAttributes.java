package com.ssafy.gumison.security.oauth2;

import com.ssafy.gumison.db.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@Getter
@ToString
public class OAuthAttributes {

  private Map<String, Object> attributes; // OAuth2 반환하는 유저 정보 Map
  private String nameAttributeKey;
  private String oauthId;
  private String oauthType;
  private String name;
  private String email;
  private String picture;

  @Builder
  public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String oauthId,
      String oauthType, String name, String email, String picture) {
    this.attributes = attributes;
    this.nameAttributeKey = nameAttributeKey;
    this.oauthId = oauthId;
    this.oauthType = oauthType;
    this.name = name;
    this.email = email;
    this.picture = picture;
  }

  public static OAuthAttributes of(String registrationId, String userNameAttributeName,
      Map<String, Object> attributes) {
    // 여기서 네이버와 카카오 등 구분 (ofNaver, ofKakao)
    if (registrationId.equals("google")) {
      return ofGoogle(userNameAttributeName, attributes);
    }
    return null;
  }

  private static OAuthAttributes ofGoogle(String userNameAttributeName,
      Map<String, Object> attributes) {
    return OAuthAttributes.builder()
        .name((String) attributes.get("name"))
        .email((String) attributes.get("email"))
        .picture((String) attributes.get("picture"))
        .attributes(attributes)
        .nameAttributeKey(userNameAttributeName)
        .oauthId((String) attributes.get(userNameAttributeName))
        .oauthType("google")
        .build();
  }

  public User toEntity() {
    return User.builder()
        .oauthId(email)
        .profile(picture)
        .build();
  }
}