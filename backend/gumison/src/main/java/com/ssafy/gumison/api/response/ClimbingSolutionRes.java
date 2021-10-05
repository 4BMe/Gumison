package com.ssafy.gumison.api.response;

import java.util.List;
import com.ssafy.gumison.common.dto.ClimbingSolutionDto;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class ClimbingSolutionRes {

  private List<ClimbingSolutionDto> climbingSolutions;
  
  private long totalElements; 
  
}
