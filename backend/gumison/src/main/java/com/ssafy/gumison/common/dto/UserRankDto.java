package com.ssafy.gumison.common.dto;

import lombok.Getter;

@Getter
public class UserRankDto extends UserSearchDto {

  private final Long rank;

  public UserRankDto(UserSearchDto userSearchDto, Long rank) {
    super(userSearchDto.getNickname(), userSearchDto.getProfile(), userSearchDto.getTier(),
        userSearchDto.getSolCnt());
    this.rank = rank;
  }

  public void setUserSearchDto(UserSearchDto userSearchDto) {
    super.tier = userSearchDto.getTier();
    super.profile = userSearchDto.getProfile();
    super.nickname = userSearchDto.getNickname();
    super.solCnt = userSearchDto.getSolCnt();
  }

  public static UserRankDto of(UserSearchDto userSearchDto, Long rank) {
    return new UserRankDto(userSearchDto, rank);
  }
}
