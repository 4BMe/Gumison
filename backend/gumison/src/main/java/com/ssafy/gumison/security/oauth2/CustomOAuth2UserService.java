package com.ssafy.gumison.security.oauth2;

import com.ssafy.gumison.api.service.OauthServiceimpl;
import com.ssafy.gumison.common.dto.SessionUser;
import com.ssafy.gumison.db.entity.User;
import com.ssafy.gumison.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpSession;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;
    private final Logger log = LoggerFactory.getLogger(CustomOAuth2UserService.class);
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // OAuth2 서비스 id (구글, 카카오, 네이버)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        log.info("[loadUser] registrationId: {}", registrationId);
        // OAuth2 로그인 진행 시 키가 되는 필드 값(PK)
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        // OAuth2UserService
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(user)); // SessionUser (직렬화된 dto 클래스 사용)


        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    // 유저 생성 및 수정 서비스 로직
    private User saveOrUpdate(OAuthAttributes attributes){
        log.info("[saveOrUpdate] attributes: {}", attributes);

        User user = userRepository.findByOauthId(attributes.getOauthId())
                .map(entity -> entity.update(attributes.getPicture()))
                .orElse(new User(attributes.getOauthId(), attributes.getOauthType(), attributes.getPicture()));

        log.info("[saveOrUpdate] user: {}", user);
        return userRepository.save(user);
    }
}