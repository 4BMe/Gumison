package com.ssafy.gumison.db.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
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
public class SolutionEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "solution_id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private UserEntity user;

  @ManyToOne
  @JoinColumn(name = "climbing_id")
  private ClimbingEntity climbing;

  @ManyToOne
  @JoinColumn(name = "level_tier_id")
  private LevelTierEntity levelTier;

  @OneToMany(mappedBy = "solution")
  private List<SolutionVideoEntity> solutionVideoList = new ArrayList<>();

  @Column(nullable = false)
  @ColumnDefault("0")
  private Integer count;

  @Column(nullable = false)
  @CreatedDate
  private LocalDateTime date;

  @Column(nullable = false)
  @ColumnDefault("N")
  private Character deleteYN;

  @Column(nullable = false)
  @ColumnDefault("0")
  private Integer accumulateReport;
}
