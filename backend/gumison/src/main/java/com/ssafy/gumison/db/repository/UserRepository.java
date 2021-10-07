package com.ssafy.gumison.db.repository;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ssafy.gumison.db.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByNickname(String nickname);

  Optional<User> findByOauthId(String oauthId);

  // ~ where nickname like '%nickname%'
  List<User> findByNicknameContaining(String nickname, Pageable page);

  Long countByNicknameContaining(String keyword);

  @Modifying
  @Transactional
  @Query(value = "UPDATE user SET profile = ?1 WHERE user_id = ?2", nativeQuery = true)
  void updateProfileByOauthId(String profile, String oauthId);
}
