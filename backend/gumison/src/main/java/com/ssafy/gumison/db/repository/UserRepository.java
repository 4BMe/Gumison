package com.ssafy.gumison.db.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.gumison.db.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByNickname(String nickname);

	// ~ where nickname like '%nickname%'
	List<User> findByNicknameContaining(String nickname);

}
