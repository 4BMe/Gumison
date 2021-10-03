package com.ssafy.gumison.api.service;

import java.util.LinkedList;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.ssafy.gumison.api.request.SolutionRequest;
import com.ssafy.gumison.api.response.HistoryRes;
import com.ssafy.gumison.api.response.SolutionListItem;
import com.ssafy.gumison.api.response.SolutionListRes;
import com.ssafy.gumison.api.response.SolutionRes;
import com.ssafy.gumison.common.dto.HistoryUserDto;
import com.ssafy.gumison.db.entity.Climbing;
import com.ssafy.gumison.db.entity.CommonCode;
import com.ssafy.gumison.db.entity.LevelTier;
import com.ssafy.gumison.db.entity.Solution;
import com.ssafy.gumison.db.entity.User;
import com.ssafy.gumison.db.repository.ClimbingRepository;
import com.ssafy.gumison.db.repository.CommonCodeRepository;
import com.ssafy.gumison.db.repository.LevelTierRepository;
import com.ssafy.gumison.db.repository.SolutionRepository;
import com.ssafy.gumison.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class HistoryServiceImpl implements HistoryService {

  private final UserRepository userRepository;
  private final SolutionRepository solutionRepository;
  private final CommonCodeRepository commonCodeRepository;
  private final ClimbingRepository climbingRepository;
  private final LevelTierRepository levelTierRepository;
  private final int LIST_PER_PAGE = 10;

  @Override
  public HistoryRes history(String nickname) {
    User user = userRepository.findByNickname(nickname).orElseThrow(RuntimeException::new);
    List<Solution> solutionList = getSolutionList(user.getId(), 0);
    CommonCode code =
        commonCodeRepository.findById(user.getTierCode()).orElseThrow(RuntimeException::new);
    String tier = codeNameConvert(code.getCode());
    List<SolutionListItem> solutionListItems = solutionListConvert(solutionList);
    Long nextExp = codeExpConvert(code.getCode() + 1L);
    HistoryUserDto userDto = userConvert(user, tier, user.getAccumulateExp(), nextExp);
    return HistoryRes.of(userDto, solutionListItems);
  }

  @Override
  public SolutionListRes solutionList(String nickname, int pageNumber) {
    User user = userRepository.findByNickname(nickname).orElseThrow(RuntimeException::new);
    List<Solution> solutionList = getSolutionList(user.getId(), pageNumber);
    List<SolutionListItem> solutionListItems = solutionListConvert(solutionList);

    return SolutionListRes.of(solutionListItems);
  }

  @Override
  public SolutionRes solution(String solutionId) {
    Solution solution =
        solutionRepository.findById(Long.parseLong(solutionId)).orElseThrow(RuntimeException::new);
    User user = solution.getUser();
    CommonCode code =
        commonCodeRepository.findById(user.getTierCode()).orElseThrow(RuntimeException::new);
    String tier = codeNameConvert(code.getCode());
    String tierName = codeNameConvert(solution.getLevelTier().getTierCode());
    String levelName = codeNameConvert(solution.getLevelTier().getLevelCode());
    return SolutionRes.of(user, tier, solution, tierName, levelName);
  }

  private HistoryUserDto userConvert(User user, String tier, Long exp, Long nextExp) {
    HistoryUserDto userDto =
        HistoryUserDto.builder().profile(user.getProfile()).nickname(user.getNickname())
            .description(user.getDescription()).tier(tier).exp(exp).nextExp(nextExp).build();
    return userDto;
  }

  private List<SolutionListItem> solutionListConvert(List<Solution> solutionList) {
    List<SolutionListItem> list = new LinkedList<SolutionListItem>();
    solutionList.forEach(solution -> {
      list.add(SolutionListItem.builder().id(solution.getId())
          .tier(codeNameConvert(solution.getLevelTier().getTierCode()))
          .climbingName(solution.getClimbing().getClimbingName())
          .level(codeNameConvert(solution.getLevelTier().getLevelCode())).cnt(solution.getCount())
          .date(solution.getDate()).build());
    });
    return list;
  }

  private List<Solution> getSolutionList(Long userId, int pageNumber) {
    List<Solution> solutionList = new LinkedList<Solution>();
    PageRequest page =
        PageRequest.of(pageNumber, LIST_PER_PAGE, Sort.by(Sort.Direction.DESC, "date"));
    solutionList = solutionRepository.findByUserId(userId, page);
    return solutionList;
  }

  private String codeNameConvert(Long code) {
    String name = "";
    name = commonCodeRepository.findName(code);
    return name.toLowerCase();
  }

  private Long codeExpConvert(Long code) {
    Long exp = 0L;
    exp = commonCodeRepository.findExp(code);
    return exp;
  }

  @Override
  public Solution createSolution(SolutionRequest solutionRequest) {
    User user =
        userRepository.findById(solutionRequest.getUserId()).orElseThrow(RuntimeException::new);
    LevelTier levelTier = levelTierRepository.findById(solutionRequest.getLevelTierId())
        .orElseThrow(RuntimeException::new);
    Climbing climbing = climbingRepository.findById(solutionRequest.getClimbingId())
        .orElseThrow(RuntimeException::new);
    Solution solution = Solution.builder().user(user).levelTier(levelTier).climbing(climbing)
        .count(solutionRequest.getCount()).date(solutionRequest.getDate()).build();
    log.info("[creatSolution] solution : " + solution);
    return solutionRepository.save(solution);
  }

  @Override
  public Solution updateSolution(SolutionRequest solutionRequest) {
    Solution originSolution = solutionRepository.findById(solutionRequest.getSolutionId())
        .orElseThrow(RuntimeException::new);
    User user =
        userRepository.findById(solutionRequest.getUserId()).orElseThrow(RuntimeException::new);
    LevelTier levelTier = levelTierRepository.findById(solutionRequest.getLevelTierId())
        .orElseThrow(RuntimeException::new);
    Climbing climbing = climbingRepository.findById(solutionRequest.getClimbingId())
        .orElseThrow(RuntimeException::new);
    Solution solution = Solution.builder().id(solutionRequest.getSolutionId()).user(user)
        .levelTier(levelTier)
        .climbing(climbing).count(solutionRequest.getCount()).date(solutionRequest.getDate())
        .deleteYN(originSolution.getDeleteYN())
        .accumulateReport(originSolution.getAccumulateReport()).build();
    log.info("[updateSolution] solution : " + solution);
    return solutionRepository.save(solution);
  }
}
