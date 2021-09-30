package com.ssafy.gumison.security.oauth2;

import com.ssafy.gumison.common.exception.OAuth2AuthenticationProcessingException;
import com.ssafy.gumison.db.entity.User;
import com.ssafy.gumison.db.repository.UserRepository;
import com.ssafy.gumison.security.UserPrincipal;
import com.ssafy.gumison.security.oauth2.user.OAuth2UserInfo;
import com.ssafy.gumison.security.oauth2.user.OAuth2UserInfoFactory;
import java.security.AuthProvider;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

  private final UserRepository userRepository;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest)
      throws OAuth2AuthenticationException {
    OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

    try {
      return processOAuth2User(oAuth2UserRequest, oAuth2User);
    } catch (AuthenticationException ex) {
      throw ex;
    } catch (Exception ex) {
      // Throwing an instance of AuthenticationException will trigger the
      // OAuth2AuthenticationFailureHandler
      throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
    }
  }

  private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
    OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(
        oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
    if (StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
      throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
    }

    //Optional<User> userOptional = userRepository.findByEmail(oAuth2UserInfo.getEmail());
    Optional<User> userOptional = userRepository.findByOauthId(oAuth2UserInfo.getId());
    User user;
    if (userOptional.isPresent()) {
      user = userOptional.get();
      if (!user.getOauthType().equals(
          oAuth2UserRequest.getClientRegistration().getRegistrationId())) {
        throw new OAuth2AuthenticationProcessingException(
            "Looks like you're signed up with " + user.getOauthType() + " account. Please use your "
                + user.getOauthType() + " account to login.");
      }
      user = updateExistingUser(user, oAuth2UserInfo);
    } else {
      user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
    }

    return UserPrincipal.create(user, oAuth2User.getAttributes());
  }

  private User registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
    log.info("Register new user {}", oAuth2UserInfo.getEmail());
    
    User user = new User();

    user.setOauthType(oAuth2UserRequest.getClientRegistration().getRegistrationId());
    user.setOauthId(oAuth2UserInfo.getId());
    //user.setName(oAuth2UserInfo.getName());
    //user.setEmail(oAuth2UserInfo.getEmail());
    user.setProfile(oAuth2UserInfo.getImageUrl());
    return userRepository.save(user);
  }

  private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
    log.info("update user {}", oAuth2UserInfo.getEmail());
    
    //existingUser.setName(oAuth2UserInfo.getName());
    existingUser.setProfile(oAuth2UserInfo.getImageUrl());
    return userRepository.save(existingUser);
  }

}
