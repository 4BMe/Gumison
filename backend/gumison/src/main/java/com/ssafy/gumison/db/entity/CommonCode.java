package com.ssafy.gumison.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import com.ssafy.gumison.common.enums.CodeTypeName;
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
public class CommonCode {
  @Id
  private String code;

  @Column(nullable = false)
  private String type;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private CodeTypeName typeName;

  private String name;

  private Long solutionExp;

  private Long tierExp;
}
