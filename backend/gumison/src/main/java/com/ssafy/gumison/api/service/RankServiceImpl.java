package com.ssafy.gumison.api.service;

import com.ssafy.gumison.common.dto.UserRankDto;
import com.ssafy.gumison.redis.RankProvider;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 랭킹 API 비즈니스 로직을 처리하는 서비스의 구현체
 *
 * @author cherrytomato1
 * @version 1.2 deleteUserRankByNickname 추가
 */

@Service("RankService")
@RequiredArgsConstructor
@Slf4j
public class RankServiceImpl implements RankService {

  private final RankProvider rankProvider;

  private final int MAX_USER_PER_PAGE = 10;

  /**
   * 닉네임으로 해당 유저의 순위 가져오기
   *
   * @param nickname 유저 닉네임
   * @return 유저 닉네임, 순위이 저장된 Dto
   */
  @Override
  public UserRankDto getUserRankByNickname(String nickname) {
    return rankProvider.getUserRankByNickname(nickname);
  }

  /**
   * 해당 페이지에 보여줄 유저 랭크 정보 반환
   *
   * @param page 요청하는 페이지 번호
   * @return 해당 페이지에 보여줄 유저 랭크 정보 리스트
   */
  @Override
  public List<UserRankDto> getUserRankByPage(int page) {
    if (page > getMaxPageCount() || page <= 0) {
      throw new IllegalArgumentException(String.format("page number %s is invalid", page));
    }
    long startOffset = (long) (page - 1) * MAX_USER_PER_PAGE ;
    return rankProvider.getUserRankByStartOffsetAndLimit(startOffset, MAX_USER_PER_PAGE - 1);
  }

  /**
   * 마지막 페이지의 페이지 번호 반환
   *
   * @return 마지막 페이지 번호, 랭크에 등록된 유저에서 페이지당 유저수를 나눈 값
   */
  @Override
  public Long getMaxPageCount() {
    return rankProvider.getUserCount() / (long) MAX_USER_PER_PAGE + 1;
  }

  /**
   * 해당 유저 정보를 ZSet에서 삭제
   *
   * @param nickname 유저 닉네임
   * @return 삭제 여부
   */
  @Override
  public boolean deleteUserRankByNickname(String nickname) {

    return rankProvider.deleteUserByNickname(nickname);
  }

  /**
   * 한 페이지 당 몇 명의 사용자를 출력할 것인지 반환
   *
   * @return 한 페이지에 가져올 사용자 수
   */
  @Override
  public Integer getUserSizePerPage() {
    return this.MAX_USER_PER_PAGE;
  }
}
