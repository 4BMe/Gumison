package com.ssafy.gumison.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.gumison.db.entity.SolutionVideo;

public interface SolutionVideoRepository extends JpaRepository<SolutionVideo, Long> {
  List<SolutionVideo> findByUploadId(String uploadId);
}
