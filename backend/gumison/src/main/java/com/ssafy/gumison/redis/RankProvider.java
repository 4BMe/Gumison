package com.ssafy.gumison.redis;

import com.ssafy.gumison.common.dto.UserRankDto;
import java.util.List;

public interface RankProvider {

  /*
    모든 유저의 닉네임과 경험치를 ZSet에 입력
    @returns : 올라간 유저의 수
   */
  Long loadAllUserExpIntoRankZSet();

  /*
  닉네임으로 해당 유저의 순위 가져오기
   @params: nickname
   @returns: UserRankDto - 유저 닉네임, 순위
 */
  UserRankDto getUserRankByNickname(String nickname);

  /*
  시작 오프셋 + limit 의 유저 랭크 정보 및 닉네임 반환
  @params: startOffset - 시작하는 사용자 위치 인덱스
           limit - 가져올 사용자 수
  @returns: userRankDtoList - 유저 닉네임, 랭크 순위 리스트 (size() == limit)
 */
  List<UserRankDto> getUserRankByStartOffsetAndLimit(int startOffset, int limit);

  /*
    ZSet에 적재된 유저 수를 반환
   */
  Long getUserCount();

}
