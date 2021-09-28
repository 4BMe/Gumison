package com.ssafy.gumison.db.repository;

import com.ssafy.gumison.common.dto.UserExpDto;
import com.ssafy.gumison.db.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RankRepository extends JpaRepository<User, Long> {

  @Query(value = "select nickname, accumulate_exp from user;", nativeQuery = true)
  List<UserExpDto> findNicknamesAndExpAll();
}
