package com.ssafy.gumison.db.entity;

import java.sql.Time;
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

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HoursEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "hours_id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "climbing_id")
  private ClimbingEntity climbing;

  @Column(nullable = false)
  private Integer type;

  @Column(nullable = false)
  private Time startTime;

  @Column(nullable = false)
  private Time endTime;
}
