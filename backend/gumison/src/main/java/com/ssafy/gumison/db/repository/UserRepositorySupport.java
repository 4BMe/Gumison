package com.ssafy.gumison.db.repository;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.gumison.common.dto.UserExpDto;
import com.ssafy.gumison.db.entity.QUser;

import lombok.RequiredArgsConstructor;

/**
 * 유저 모델 동적 쿼리 생성을 위한 클래스
 */
@Repository
@RequiredArgsConstructor
public class UserRepositorySupport {

  private final JPAQueryFactory jpaQueryFactory;
  private final QUser qUser = QUser.user;

  public List<UserExpDto> findNicknamesAndExpAll() {
    List<Tuple> tupleList = jpaQueryFactory.select(qUser.nickname, qUser.accumulateExp).from(qUser)
        .fetch();

    List<UserExpDto> resultList = new LinkedList<>();

    tupleList.forEach(tuple -> resultList
        .add(UserExpDto.of(tuple.get(0, String.class), tuple.get(1, Long.class))));

    return resultList;
  }
}

