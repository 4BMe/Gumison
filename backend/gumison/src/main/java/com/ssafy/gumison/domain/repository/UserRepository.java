package com.ssafy.gumison.domain.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ssafy.gumison.domain.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  
  // ~ where nickname like '%nickname%'
  Optional<User> findByNicknameContaining(String nickname);
  
}
