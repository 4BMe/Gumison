package com.ssafy.gumison.api.service;

import com.ssafy.gumison.common.dto.UserRankDto;
import java.util.List;

public interface RankService {
  /*
닉네임으로 해당 유저의 순위 가져오기
 @params: nickname
 @returns: UserRankDto - 유저 닉네임, 순위
*/
  UserRankDto getUserRankByNickname(String nickname);

  /*
  시작 오프셋 + limit 의 유저 랭크 정보 및 닉네임 반환
  @params: page - 요청하는 페이지

  @returns: userRankDtoList - 유저 닉네임, 랭크 순위 리스트
 */
  List<UserRankDto> getUserRankByPage(int page);

  /*
    마지막 페이지의 페이지 번호 반환
    @returns: 랭크 마지막 페이지 번호
   */
  Long getMaxPageCount();
}
