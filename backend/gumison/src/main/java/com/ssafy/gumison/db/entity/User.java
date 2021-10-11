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
import org.hibernate.annotations.DynamicInsert;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@DynamicInsert
@ToString(exclude = "solutionList")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Long id;

  @Column(unique = true)
  private String nickname;

  private String description;

  private String profile;

  @NotNull
  private String oauthId;

  @NotNull
  private String oauthType;

  @OneToMany(mappedBy = "user")
  @JsonIgnore
  private List<Solution> solutionList = new ArrayList<>();

  @NotNull
  @ColumnDefault("0")
  private Long accumulateExp;

  @NotNull
  @ColumnDefault("0")
  private Integer accumulateVideo;

  @NotNull
  @ColumnDefault("201")
  private Long tierCode;

  public User update(String profile) {
    this.profile = profile;
    return this;
  }


}
