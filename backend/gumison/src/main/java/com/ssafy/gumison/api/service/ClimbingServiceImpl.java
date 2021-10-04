package com.ssafy.gumison.api.service;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.ssafy.gumison.api.response.ClimbingRes;
import com.ssafy.gumison.api.response.ClimbingSolutionRes;
import com.ssafy.gumison.common.dto.HoursDto;
import com.ssafy.gumison.common.dto.LevelTierDto;
import com.ssafy.gumison.common.enums.HoursDay;
import com.ssafy.gumison.db.entity.Climbing;
import com.ssafy.gumison.db.entity.Hours;
import com.ssafy.gumison.db.entity.LevelTier;
import com.ssafy.gumison.db.entity.Solution;
import com.ssafy.gumison.db.repository.ClimbingRepository;
import com.ssafy.gumison.db.repository.CommonCodeRepository;
import com.ssafy.gumison.db.repository.HoursRepository;
import com.ssafy.gumison.db.repository.LevelTierRepository;
import com.ssafy.gumison.db.repository.SolutionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClimbingServiceImpl implements ClimbingService {

  private final ClimbingRepository climbingRepository;
  private final HoursRepository hoursRepository;
  private final LevelTierRepository levelTierReposotory;
  private final CommonCodeRepository commonCodeRepository;
  private final SolutionRepository solutionRepository;

  @Override
  public List<Climbing> getClimbingList(String climbingName, int pageNumber) {
    PageRequest page = PageRequest.of(pageNumber, 10);
    return climbingRepository.findByClimbingNameContaining(climbingName, page);
  }

  @Override
  public ClimbingRes getClimbingDetail(Long climbingId) {
    Climbing climbing = climbingRepository.findById(climbingId).orElseThrow(RuntimeException::new);
    List<Hours> hoursList = hoursRepository.findByClimbingId(climbingId);
    List<LevelTier> levelTierList =
        levelTierReposotory.findByClimbingIdOrderByTierCodeAsc(climbingId);

    ClimbingRes climbingRes = ClimbingRes.builder().climbing(climbing)
        .hours(getHoursDtoList(hoursList)).levelTiers(getLevelTierDtoList(levelTierList)).build();

    log.info("Get climbing detail: {}", climbingRes);

    return climbingRes;
  }

  @Override
  public List<ClimbingSolutionRes> getLevelSolution(Long climbingId, Long levelTierId,
      int pageNumber) {
    List<ClimbingSolutionRes> solutionResList = new ArrayList<>();

    List<Solution> solutionList = getSolutionList(climbingId, levelTierId, pageNumber);
    for (Solution solution : solutionList) {
      solutionResList.add(
          ClimbingSolutionRes.builder()
          .id(solution.getId())
          .nickname(solution.getUser().getNickname())
          .level(codeNameConvert(solution.getLevelTier().getLevelCode()))
          .tier(codeNameConvert(solution.getLevelTier().getTierCode()).toLowerCase())
          .count(solution.getCount())
          .date(solution.getDate())
          .build()
      );
    }

    return solutionResList;
  }

  private List<Solution> getSolutionList(Long climbingId, Long levelTierId, int pageNumber) {
    PageRequest page = PageRequest.of(pageNumber, 10, Sort.by(Sort.Direction.DESC, "date"));
    return solutionRepository.findByClimbingIdAndLevelTierId(climbingId, levelTierId, page);
  }

  private List<HoursDto> getHoursDtoList(List<Hours> hoursList) {
    List<HoursDto> hoursDtoList = new ArrayList<>();

    for (Hours hours : hoursList) {
      HoursDto hoursDto = HoursDto.builder().startTime(removeSeconds(hours.getStartTime()))
          .endTime(removeSeconds(hours.getEndTime())).day(HoursDay.getEnumByType(hours.getType()))
          .build();
      hoursDtoList.add(hoursDto);
    }

    log.info("Hours to HoursDto: {}", hoursDtoList);

    return hoursDtoList;
  }

  private String removeSeconds(Time time) {
    Date myDate = new Date(time.getTime());
    DateFormat df = new SimpleDateFormat("H:mm");
    return df.format(myDate);
  }

  private List<LevelTierDto> getLevelTierDtoList(List<LevelTier> levelTierList) {
    List<LevelTierDto> leveltierDto = new ArrayList<LevelTierDto>();

    for (LevelTier levelTier : levelTierList) {
      // 레벨명 가져오기
      String level = codeNameConvert(levelTier.getLevelCode());

      // 티어명 가져오기
      String tier = codeNameConvert(levelTier.getTierCode());

      leveltierDto
          .add(LevelTierDto.builder().id(levelTier.getId()).level(level).tier(tier).build());
    }

    log.info("LevelTier to LevelTierDto: {}", leveltierDto);

    return leveltierDto;
  }

  private String codeNameConvert(Long code) {
    String name = "";
    name = commonCodeRepository.findName(code);
    return name;
  }

}
