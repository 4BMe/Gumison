package com.ssafy.gumison.db.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ssafy.gumison.db.entity.Hours;

public interface HoursRepository extends JpaRepository<Hours, Long> {
  List<Hours> findByClimbingId(Long climbingId);
}
