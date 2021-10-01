package com.ssafy.gumison.common.enums;

import lombok.Getter;

@Getter
public enum HoursDay {
  월요일(1), 
  화요일(2), 
  수요일(3), 
  목요일(4), 
  금요일(5), 
  토요일(6), 
  일요일(7), 
  공휴일(8);
  
  private int dayType;
  
  HoursDay(int dayType) {
    this.dayType = dayType;
  }
  
  public static HoursDay getEnumByType(int type) {
    HoursDay[] hoursArr = HoursDay.values();
    
    for (HoursDay hoursDay : hoursArr) {
      if(hoursDay.getDayType() == type)
        return hoursDay;
    }
    
    return null;
  }
}
