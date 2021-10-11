package com.ssafy.gumison.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
public class LevelTier {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "level_tier_id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "climbing_id")
  private Climbing climbing;

  @Column(nullable = false)
  private Long levelCode;

  @Column(nullable = false)
  private Long tierCode;
}
