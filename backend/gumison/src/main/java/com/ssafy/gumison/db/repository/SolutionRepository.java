package com.ssafy.gumison.db.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ssafy.gumison.db.entity.Solution;

public interface SolutionRepository extends JpaRepository<Solution, Long> {

  List<Solution> findByUserId(Long userId, Pageable pageable);

  List<Solution> findAllByUserId(Long userId);

  Page<Solution> findByClimbingIdAndLevelTierId(Long climingId, Long levelTierId,
      Pageable pageable);

  List<Solution> findByUploadId(String uploadId);

  @Transactional
  @Modifying
  @Query(value = "delete from solution where upload_id=?", nativeQuery = true)
  Integer removeByUploadId(String uploadId);

}
