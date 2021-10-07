package com.ssafy.gumison.db.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ssafy.gumison.db.entity.SolutionVideo;

public interface SolutionVideoRepository extends JpaRepository<SolutionVideo, Long> {
  List<SolutionVideo> findByUploadId(String uploadId);

  @Transactional
  @Modifying
  @Query(value = "delete from solution_video where upload_id=?", nativeQuery = true)
  Integer removeByUploadId(String uploadId);

}
