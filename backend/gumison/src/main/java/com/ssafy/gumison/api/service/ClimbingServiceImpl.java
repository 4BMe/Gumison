package com.ssafy.gumison.api.service;

import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.ssafy.gumison.api.response.ClimbingRes;
import com.ssafy.gumison.api.response.ClimbingSolutionRes;
import com.ssafy.gumison.db.entity.Climbing;
import com.ssafy.gumison.db.repository.ClimbingRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClimbingServiceImpl implements ClimbingService{

  private final ClimbingRepository climbingRepository;

  @Override
  public List<Climbing> getClimbingList(String climbingName, int pageNumber) {
    PageRequest page = PageRequest.of(pageNumber, 10);
    return climbingRepository.findByClimbingNameContaining(climbingName, page);
  }

  @Override
  public ClimbingRes getClimbing(String climbingId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ClimbingSolutionRes getLevelSolution(String climbingId, int pageNumber) {
    // TODO Auto-generated method stub
    return null;
  }
  
}
