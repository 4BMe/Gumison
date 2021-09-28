package com.ssafy.gumison.api.service;

import java.util.LinkedList;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.ssafy.gumison.api.response.SolutionListItem;
import com.ssafy.gumison.api.response.SolutionListRes;
import com.ssafy.gumison.api.response.SolutionRes;
import com.ssafy.gumison.api.response.UserHistoryRes;
import com.ssafy.gumison.db.entity.CommonCode;
import com.ssafy.gumison.db.entity.Solution;
import com.ssafy.gumison.db.entity.User;
import com.ssafy.gumison.db.repository.CommonCodeRepository;
import com.ssafy.gumison.db.repository.SolutionRepository;
import com.ssafy.gumison.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {

  private final UserRepository userRepository;
  private final SolutionRepository solutionRepository;
  private final CommonCodeRepository commonCodeRepository;

  @Override
  public UserHistoryRes history(String nickname) {
    User user = userRepository.findByNickname(nickname).orElseThrow(RuntimeException::new);
    List<Solution> solutionList = getSolutionList(user.getId(), 0);
    CommonCode code =
        commonCodeRepository.findById(user.getTierCode()).orElseThrow(RuntimeException::new);
    String tier = codeNameConvert(code.getCode());
    List<SolutionListItem> solutionListItems = solutionListConvert(solutionList);
    return UserHistoryRes.of(user, tier, user.getAccumulateExp(), solutionListItems);
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
    String tier = commonCodeRepository.findTier(user.getAccumulateExp());
    String tierName = codeNameConvert(solution.getLevelTier().getTierCode());
    String levelName = codeNameConvert(solution.getLevelTier().getLevelCode());
    return SolutionRes.of(user, tier, solution, tierName, levelName);
  }

  private List<SolutionListItem> solutionListConvert(List<Solution> solutionList) {
    List<SolutionListItem> list = new LinkedList<SolutionListItem>();
    solutionList.forEach(solution -> {
      list.add(
          SolutionListItem.builder().tier(codeNameConvert(solution.getLevelTier().getTierCode()))
              .climbingName(solution.getClimbing().getClimbingName())
              .level(codeNameConvert(solution.getLevelTier().getLevelCode()))
              .cnt(solution.getCount()).date(solution.getDate().toLocalDate()).build());
    });
    return list;
  }

  private List<Solution> getSolutionList(Long userId, int pageNumber) {
    List<Solution> solutionList = new LinkedList<Solution>();
    PageRequest page = PageRequest.of(pageNumber, 10, Sort.by(Sort.Direction.DESC, "date"));
    solutionList = solutionRepository.findByUserId(userId, page);
    return solutionList;
  }

  private String codeNameConvert(Long code) {
    String name = "";
    name = commonCodeRepository.findName(code);
    return name;
  }
}
