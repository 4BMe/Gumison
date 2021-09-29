package com.ssafy.gumison.api.response;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.ssafy.gumison.db.entity.SolutionVideo;
import lombok.Setter;
import lombok.ToString;

@Setter
@ToString
public class ClimbingSolutionRes {

  private String nickname;
  
  private String level;
  
  private String tier;
  
  private Integer count;
  
  private LocalDate date;
  
  private List<SolutionVideo> solutionVideoList = new ArrayList<>();
}
