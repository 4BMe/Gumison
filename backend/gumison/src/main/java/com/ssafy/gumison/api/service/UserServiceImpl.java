package com.ssafy.gumison.api.service;

import com.ssafy.gumison.common.exception.ResourceNotFoundException;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.ssafy.gumison.api.response.UserSearchRes;
import com.ssafy.gumison.common.dto.SessionUserDto;
import com.ssafy.gumison.common.dto.UserSearchDto;
import com.ssafy.gumison.db.entity.CommonCode;
import com.ssafy.gumison.db.entity.Solution;
import com.ssafy.gumison.db.entity.User;
import com.ssafy.gumison.db.repository.CommonCodeRepository;
import com.ssafy.gumison.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("UserService")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final CommonCodeRepository commonCodeRepository;

  @Override
  public UserSearchRes getUserList(String nickname, int pageNumber) {
    PageRequest page = PageRequest.of(pageNumber, 10);
    List<User> userList = userRepository.findByNicknameContaining(nickname, page);

    UserSearchRes userSearchRes = new UserSearchRes();
    for (User user : userList) {
      userSearchRes.getUsers().add(getUserSearchDtoByUser(user));
    }

    log.info("Set user search response: {}", userSearchRes);

    return userSearchRes;
  }

  @Override
  public SessionUserDto getCurrentUser(HttpSession httpSession) {
    SessionUserDto sessionUserDto = (SessionUserDto) httpSession.getAttribute("user");
    log.info("Load session user {}", sessionUserDto);
    return sessionUserDto;
  }

  /**
   * 유저 닉네임으로 UserSearcDto 반환
   *
   * @param nickname 사용자 닉네임
   * @return 유저 정보 중 닉네임, 프로필, 티어코드, 문제 해결 숫자 반환
   */
  @Override
  public UserSearchDto getUserSearchDtoByNickname(String nickname) {
    User user = userRepository.findByNickname(nickname)
        .orElseThrow(() -> new ResourceNotFoundException("User", nickname, "nickname"));
    return getUserSearchDtoByUser(user);
  }

  /**
   * 키워드로 검색한 유저의 전체 수를 반환
   *
   * @param keyword 검색 키워드
   * @return 해당 키워드를 포함한 유저의 수 카운트
   */
  @Override
  public Long getUserCountByKeyword(String keyword) {
    return userRepository.countByNicknameContaining(keyword);
  }

  /**
   * 유저 정보로 UserSearchDto 반환
   *
   * @param user 유저 정보
   * @return 유저 정보 중 닉네임, 프로필, 티어코드, 문제 해결 숫자 반환
   * @throws RuntimeException 해당 유저의 코드티어에 저장된 값이 CommonCode에 없을 경우
   */
  private UserSearchDto getUserSearchDtoByUser(User user) {
    CommonCode code = commonCodeRepository.findById(user.getTierCode())
        .orElseThrow(RuntimeException::new);

    long solvedCount = 0;

    for (Solution solution : user.getSolutionList()) {
      solvedCount += solution.getCount();
    }

    return UserSearchDto.builder()
        .nickname(user.getNickname())
        .profile(user.getProfile())
        .tier(code.getName().toLowerCase())
        .solCnt(solvedCount)
        .build();
  }

}




