package com.ssafy.gumison.redis;

/**
 * 유저 검색 키워드를 레디스에 저장하고 불러오는 인터페이스
 *
 * @author cherrytomato1
 * @version 1.0 키워드에 해당하는 유저 수 검색 및 저장
 */
public interface UserRankSearchKeywordRepository {

  /**
   * 키워드에 해당하는 유저의 수를 반환
   *
   * @param keyword 검색할 키워드
   * @return 해당하는 유저의 수
   */
  Long getUserSearchKeywordCount(String keyword);

  /**
   * 키워드에 해당하는 유저의 수를 저장
   *
   * @param keyword 저장할 키워드
   * @param count   저장할 값
   */
  void setUserSearchKeywordCount(String keyword, Long count);
}
