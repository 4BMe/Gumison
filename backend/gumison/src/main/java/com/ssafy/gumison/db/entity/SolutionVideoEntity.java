package com.ssafy.gumison.db.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
public class SolutionVideoEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "video_id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "solution_id")
  private SolutionEntity solution;

  @Column(nullable = false)
  private String url;

  @Column(nullable = false)
  @CreatedDate
  private LocalDateTime date;
}
