package com.ssafy.gumison.api.response;

import java.util.List;
import com.ssafy.gumison.common.dto.HoursDto;
import com.ssafy.gumison.common.dto.LevelTierDto;
import com.ssafy.gumison.db.entity.Climbing;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class ClimbingRes {

  Climbing climbing;
  
  List<HoursDto> hours;
  
  List<LevelTierDto> levelTiers;
  
}
