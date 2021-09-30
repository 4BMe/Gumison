package com.ssafy.gumison.common.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRankDto extends UserSearchDto {

  private Long rank;

  public UserRankDto(UserSearchDto userSearchDto, Long rank) {
    super(userSearchDto.getNickname(), userSearchDto.getProfile(), userSearchDto.getTier(),
        userSearchDto.getSolCnt());
    this.rank = rank;
  }

  public static UserRankDto of(UserSearchDto userSearchDto, Long rank) {
    return new UserRankDto(userSearchDto, rank);
  }
}
