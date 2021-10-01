package com.ssafy.gumison.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.gumison.db.entity.Contribution;

public interface ContributionRepository extends JpaRepository<Contribution, Long> {

}
