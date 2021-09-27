package com.ssafy.gumison.api.service;

import java.util.List;

import com.ssafy.gumison.common.dto.SessionUserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.ssafy.gumison.api.response.UserSearchRes;
import com.ssafy.gumison.common.dto.UserSearchDto;
import com.ssafy.gumison.db.entity.CommonCode;
import com.ssafy.gumison.db.entity.User;
import com.ssafy.gumison.db.repository.CommonCodeRepository;
import com.ssafy.gumison.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpSession;

@Service("UserService")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final CommonCodeRepository commonCodeRepository;
  private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

  @Override
  public UserSearchRes getUserList(String nickname, int pageNumber) {
    PageRequest page = PageRequest.of(pageNumber, 10);
    List<User> userList = userRepository.findByNicknameContaining(nickname, page);

    UserSearchRes userSearchResponse = new UserSearchRes();
    for (User user : userList) {
      CommonCode code = commonCodeRepository.findById(user.getTierCode())
          .orElseThrow(RuntimeException::new);
      userSearchResponse.getUsers().add(new UserSearchDto(user, code.getName()));
    }

    return userSearchResponse;
  }

  @Override
  public SessionUserDto getCurrentUser(HttpSession httpSession) {
    SessionUserDto sessionUserDto = (SessionUserDto) httpSession.getAttribute("user");
    log.info("Load session user {}", sessionUserDto);
    return sessionUserDto;
  }

}