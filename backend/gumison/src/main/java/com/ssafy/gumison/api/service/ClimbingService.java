package com.ssafy.gumison.api.service;

import java.util.List;
import com.ssafy.gumison.api.response.ClimbingRes;
import com.ssafy.gumison.api.response.ClimbingSolutionRes;
import com.ssafy.gumison.db.entity.Climbing;

/**
 *  클라이밍장 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface ClimbingService {

  List<Climbing> getClimbingList(String climbingName, int pageNumber);
  
  ClimbingRes getClimbing(String climbingId);

  ClimbingSolutionRes getLevelSolution(String climbingId, int pageNumber);
}
