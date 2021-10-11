package com.ssafy.gumison.db.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ssafy.gumison.db.entity.LevelTier;

public interface LevelTierRepository extends JpaRepository<LevelTier, Long> {
  List<LevelTier> findByClimbingIdOrderByTierCodeAsc(Long climbingId);
}
