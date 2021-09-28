package com.ssafy.gumison.api.service;

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
      CommonCode code = commonCodeRepository.findById(user.getTierCode())
          .orElseThrow(RuntimeException::new);
      
      long solCnt = 0;
      
      for (Solution solution : user.getSolutionList()) {
        solCnt += solution.getCount();
      }

      UserSearchDto dto = UserSearchDto.builder()
                          .nickname(user.getNickname())
                          .profile(user.getProfile())
                          .tier(code.getName().toLowerCase())
                          .solCnt(solCnt)
                          .build();
      
      userSearchRes.getUsers().add(dto);
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

}