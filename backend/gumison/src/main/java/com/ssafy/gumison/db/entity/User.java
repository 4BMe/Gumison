package com.ssafy.gumison.db.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Long id;

  @Column(unique = true)
  private String nickname;

  private String description;

  private String profile;

  @Column(nullable = false)
  private String oauthId;

  @Column(nullable = false)
  private String oauthType;

  @OneToMany(mappedBy = "user")
  private List<Solution> solutionList = new ArrayList<>();

  @Column(nullable = false)
  @ColumnDefault("0")
  private Long accumulateExp;

  @Column(nullable = false)
  @ColumnDefault("0")
  private Integer accumulateVideo;


}
