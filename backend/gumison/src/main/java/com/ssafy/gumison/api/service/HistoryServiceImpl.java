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
import com.ssafy.gumison.db.entity.Solution;
import com.ssafy.gumison.db.entity.User;
import com.ssafy.gumison.db.repository.CommonCodeRepository;
import com.ssafy.gumison.db.repository.SolutionRepository;
import com.ssafy.gumison.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {

  private final UserRepository userRepository;
  private final SolutionRepository solutionRepository;
  private final CommonCodeRepository commonCodeRepository;

  @Override
  public UserHistoryRes history(String nickname) {
    User user = userRepository.findByNickname(nickname).orElseThrow(RuntimeException::new);
    PageRequest page = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "date"));
    List<Solution> solutionList = solutionRepository.findByUserId(user.getId(), page);
    String tier = commonCodeRepository.findtier(user.getAccumulateExp());
    List<SolutionListItem> solutionListItems = solutionListConvert(solutionList);

    return UserHistoryRes.of(user, tier, user.getAccumulateExp(), solutionListItems);
  }

  @Override
  public SolutionListRes solutionList(String nickname, int pageNumber) {

    return null;
  }

  @Override
  public SolutionRes solution(String solutionId) {
    Solution solution =
        solutionRepository.findById(Long.parseLong(solutionId)).orElseThrow(RuntimeException::new);

    return null;
  }

  private List<SolutionListItem> solutionListConvert(List<Solution> solutionList) {
    List<SolutionListItem> list = new LinkedList<SolutionListItem>();
    solutionList.forEach(solution -> {
      list.add(new SolutionListItem(solution.getLevelTier().getTierCode(),
          solution.getClimbing().getClimbingName(), solution.getLevelTier().getLevelCode(),
          solution.getCount(), solution.getDate().toLocalDate()));
    });
    return list;
  }

}
