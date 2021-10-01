package com.ssafy.gumison.common.dto;

import com.ssafy.gumison.common.enums.HoursDay;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class HoursDto {

  private HoursDay day;

  private String startTime;

  private String endTime;

}
