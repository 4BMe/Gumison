package com.ssafy.gumison.db.repository;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ssafy.gumison.db.entity.Solution;

public interface SolutionRepository extends JpaRepository<Solution, Long> {

  List<Solution> findByUserId(Long userId, Pageable pageable);

  List<Solution> findByClimbingIdAndLevelTierId(Long climingId, Long levelTierId,
      Pageable pageable);

  List<Solution> findByUploadId(String uploadId);
}
