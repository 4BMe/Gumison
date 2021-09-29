package com.ssafy.gumison.common.util;

import com.ssafy.gumison.common.dto.UserRankDto;
import java.util.List;

public interface RankProvider {

  /*
    모든 유저의 닉네임과 경험치를 ZSet에 입력
    @returns : 올라간 유저의 수
   */
  int loadAllUserExpIntoRankZSet();

  /*
  닉네임으로 해당 유저의 순위 가져오기
   @params: nickname
   @returns: UserRankDto - 유저 닉네임, 순위
 */
  UserRankDto getUserRankByNickname(String nickname);

  List<UserRankDto> getUserRankByStartOffsetAndLimit(int startOffset, int limit);

}
