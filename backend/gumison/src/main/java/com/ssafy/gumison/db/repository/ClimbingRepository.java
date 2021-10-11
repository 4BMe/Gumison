package com.ssafy.gumison.db.repository;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ssafy.gumison.db.entity.Climbing;

public interface ClimbingRepository extends JpaRepository<Climbing, Long> {
  
  List<Climbing> findByClimbingNameContaining(String climbingName, Pageable page);

}
