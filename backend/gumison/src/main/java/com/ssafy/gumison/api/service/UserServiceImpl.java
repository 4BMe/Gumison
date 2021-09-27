package com.ssafy.gumison.api.service;

import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.ssafy.gumison.api.response.UserSearchRes;
import com.ssafy.gumison.common.dto.UserSearchDto;
import com.ssafy.gumison.db.entity.CommonCode;
import com.ssafy.gumison.db.entity.User;
import com.ssafy.gumison.db.repository.CommonCodeRepository;
import com.ssafy.gumison.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final CommonCodeRepository commonCodeRepository;
  
  @Override
  public UserSearchRes getUserList(String nickname, int pageNumber) {
    PageRequest page = PageRequest.of(pageNumber, 10);
    List<User> userList = userRepository.findByNicknameContaining(nickname, page);
    
    UserSearchRes userSearchResponse = new UserSearchRes();
    for (User user : userList) {
      CommonCode code = commonCodeRepository.findById(user.getTierCode()).orElseThrow(RuntimeException::new);
      userSearchResponse.getUsers().add(new UserSearchDto(user, code.getName()));
    }
    
    return userSearchResponse;
  }

}