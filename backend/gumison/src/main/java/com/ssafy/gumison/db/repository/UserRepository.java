package com.ssafy.gumison.db.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ssafy.gumison.db.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
  
  Optional<UserEntity> findByNickname(String nickname);
  
  // ~ where nickname like '%nickname%'
  List<UserEntity> findByNicknameContaining(String nickname);
  
}
