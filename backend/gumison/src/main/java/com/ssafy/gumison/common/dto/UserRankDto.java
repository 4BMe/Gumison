package com.ssafy.gumison.common.dto;

import lombok.Getter;

@Getter
public class UserRankDto extends UserSearchDto {

  private final Long rank;

  private UserRankDto(UserSearchDto userSearchDto, Long rank) {
    super(userSearchDto.getNickname(), userSearchDto.getProfile(), userSearchDto.getTier(),
        userSearchDto.getSolCnt());
    this.rank = rank;
  }

  private UserRankDto(String nickname, Long rank) {
    this.nickname = nickname;
    this.rank = rank;
  }

  public void setUserSearchDto(UserSearchDto userSearchDto) {
    this.tier = userSearchDto.getTier();
    this.profile = userSearchDto.getProfile();
    this.nickname = userSearchDto.getNickname();
    this.solCnt = userSearchDto.getSolCnt();
  }

  public static UserRankDto of(UserSearchDto userSearchDto, Long rank) {
    return new UserRankDto(userSearchDto, rank);
  }

  public static UserRankDto of(String nickname, Long rank) {
    return new UserRankDto(nickname, rank);
  }
}
