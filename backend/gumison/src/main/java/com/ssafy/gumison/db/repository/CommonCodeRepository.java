package com.ssafy.gumison.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.ssafy.gumison.db.entity.CommonCode;

public interface CommonCodeRepository extends JpaRepository<CommonCode, Long> {

  @Query(
      value = "select name from common_code where tier_exp = ( select max(a.tier_exp) from (select * from common_code where tier_exp <?1) as a);",
      nativeQuery = true)
  String findTier(Long exp);

  @Query(value = "select name from common_code where code = ?;", nativeQuery = true)
  String findName(Long code);

  @Query(value = "select tier_exp from common_code where code = ?;", nativeQuery = true)
  Long findExp(Long code);
}
