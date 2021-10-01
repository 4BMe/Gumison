package com.ssafy.gumison.api.response;

import lombok.Getter;
import lombok.Setter;

/*
  페이지 처리를 위한 Response 정의
 */
@Setter
@Getter
public class RankPageCountRes {

  private Integer pageCount;

  private RankPageCountRes(int pageCount) {
    this.pageCount = pageCount;
  }

  public static RankPageCountRes of(int pageCount) {
    return new RankPageCountRes(pageCount);
  }
}
