package com.ssafy.gumison.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ssafy.gumison.db.entity.SolutionEntity;

public interface SolutionRepository extends JpaRepository<SolutionEntity, Long> {

}
