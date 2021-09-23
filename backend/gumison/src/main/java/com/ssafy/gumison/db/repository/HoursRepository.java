package com.ssafy.gumison.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.gumison.db.entity.Hours;

public interface HoursRepository extends JpaRepository<Hours, Long> {

}
