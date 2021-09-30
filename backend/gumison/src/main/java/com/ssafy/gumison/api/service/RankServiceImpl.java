package com.ssafy.gumison.api.service;

import com.ssafy.gumison.common.dto.UserRankDto;
import com.ssafy.gumison.redis.RankProvider;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("RankService")
@RequiredArgsConstructor
@Slf4j
public class RankServiceImpl implements RankService {

  private final RankProvider rankProvider;

  private final int MAX_USER_PER_PAGE = 10;

  @Override
  public UserRankDto getUserRankByNickname(String nickname) {
    return rankProvider.getUserRankByNickname(nickname);
  }

  @Override
  public List<UserRankDto> getUserRankByPage(int page) {
    if (page > getMaxPageCount() || page <= 0) {
      throw new RuntimeException(String.format("page number %s is invalid", page));
    }
    int startOffset = (page - 1) / MAX_USER_PER_PAGE;
    return rankProvider.getUserRankByStartOffsetAndLimit(startOffset, MAX_USER_PER_PAGE);
  }

  @Override
  public Long getMaxPageCount() {
    return rankProvider.getUserCount() / (long) MAX_USER_PER_PAGE + 1;
  }
}
