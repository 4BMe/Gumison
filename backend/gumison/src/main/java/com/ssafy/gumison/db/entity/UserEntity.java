package com.ssafy.gumison.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.hibernate.annotations.ColumnDefault;
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
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;

  @Column(unique = true)
  private String nickname;

  private String description;

  private String profile;

  @Column(nullable = false)
  private String oauthId;

  @Column(nullable = false)
  private String oauthType;

  @Column(nullable = false)
  @ColumnDefault("0")
  private Long accumulateExp;

  @Column(nullable = false)
  @ColumnDefault("0")
  private Integer accumulateVideo;


}
