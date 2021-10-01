package com.ssafy.gumison.api.response;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.ssafy.gumison.db.entity.SolutionVideo;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class ClimbingSolutionRes {

  private Long id;
  
  private String nickname;
  
  private String level;
  
  private String tier;
  
  private Integer count;
  
  private LocalDate date;
  
}
