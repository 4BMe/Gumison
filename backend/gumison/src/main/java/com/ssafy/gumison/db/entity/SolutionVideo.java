package com.ssafy.gumison.db.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.sun.istack.NotNull;

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
public class SolutionVideo {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "video_id")
  private Long id;

  @NotNull
  @Column(nullable = false)
  private String uploadId;

  @NotNull
  @Column(nullable = false)
  private String uri;

  @NotNull
  @Column(nullable = false)
  private LocalDateTime dateTime;
}
