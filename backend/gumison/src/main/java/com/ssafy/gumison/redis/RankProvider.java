package com.ssafy.gumison.redis;

import com.ssafy.gumison.common.dto.UserRankDto;
import java.util.List;

/**
 * 레디스 ZSet(Sorted Set)을 제어하기 위한 인터페이스
 *
 * @author cherrytomato1
 * @version 1.2   deleteUserByNickname 추가
 */

public interface RankProvider {

  /**
   * 모든 유저의 닉네임과 경험치를 ZSet에 입력
   *
   * @return 입력된 유저의 수
   */
  Long loadAllUserExpIntoRankZSet();

  /**
   * 닉네임으로 해당 유저의 순위 가져오기
   *
   * @param nickname 순위를 가져올 유저의 닉네임
   * @return 유저 닉네임, 순위가 기록된 DTO
   */
  UserRankDto getUserRankByNickname(String nickname);


  /**
   * 시작 오프셋 + limit 의 유저 랭크 정보 및 닉네임 반환
   *
   * @param startOffset 시작하는 사용자 위치 인덱스
   * @param limit       가져올 사용자 수
   * @return 유저 닉네임, 랭크 순위 리스트 (size() == limit)
   */
  List<UserRankDto> getUserRankByStartOffsetAndLimit(int startOffset, int limit);

  /**
   * ZSet에 저장된 유저 수를 반환
   *
   * @return ZSet에 저장된 유저 수
   */
  Long getUserCount();

  /**
   * 해당하는 사용자 닉네임 Value를 ZSet에서 삭제
   *
   * @param nickname 사용자 닉네임
   * @return 삭제 성공 여부, 존재하지 않는 유저일 경우 false
   */
  boolean deleteUserByNickname(String nickname);
}
