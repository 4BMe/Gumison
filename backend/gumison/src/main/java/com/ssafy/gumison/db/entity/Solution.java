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

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@DynamicInsert
@DynamicUpdate
public class Solution {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "solution_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "climbing_id")
  private Climbing climbing;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "level_tier_id")
  private LevelTier levelTier;

  @NotNull
  @ColumnDefault("0")
  private Integer count;

  @NotNull
  @CreatedDate
  private LocalDate date;

  @NotNull
  @ColumnDefault("false")
  private Boolean deleteYN;

  @NotNull
  @ColumnDefault("0")
  private Integer accumulateReport;

  @NotNull
  private String uploadId;
}
