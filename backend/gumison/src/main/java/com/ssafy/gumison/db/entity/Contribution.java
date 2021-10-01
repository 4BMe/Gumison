package com.ssafy.gumison.db.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.DynamicInsert;

import com.sun.istack.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Entity
@Builder
@Getter
@ToString
@DynamicInsert
public class Contribution {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "contribution_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "level_tier_id")
  private LevelTier levelTier;

  @NotNull
  private String newTier;
  
  @NotNull
  private String oldTier;

  @NotNull
  private LocalDate date;
}
