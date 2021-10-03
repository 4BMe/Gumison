package com.ssafy.gumison.config;

import com.ssafy.gumison.security.TokenAuthenticationFilter;
import com.ssafy.gumison.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.ssafy.gumison.security.oauth2.OAuth2AuthenticationFailureHandler;
import com.ssafy.gumison.security.oauth2.OAuth2AuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.ssafy.gumison.security.oauth2.CustomOAuth2UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity // Spring Security 설정 활성화
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final CustomOAuth2UserService customOAuth2UserService;

  private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

  private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .cors()
        .and()
        .csrf().disable()
        .headers().frameOptions().disable() // h2-console 화면을 사용하기 위해 해당 옵션 disable
        .and()
        .authorizeRequests()// URL별 권한 권리
        //.antMatchers("/api/users/detail/**").hasRole("USER") // /api/v1/** 은 USER권한만 접근 가능
        .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/api/**")
        .permitAll()
        .anyRequest()
        .authenticated() // anyRequest : 설정된 값들 이외 나머지 URL 나타냄, authenticated : 인증된 사용자
        .and()
        .oauth2Login()
        .authorizationEndpoint()
        .baseUri("/oauth2/authorize")
        .authorizationRequestRepository(cookieAuthorizationRequestRepository())
        .and()
        .redirectionEndpoint()//플랫폼(naver)에서 access token을 받아왔을 때 서버에서 처리할 url
        .baseUri("/oauth2/callback/*")
        .and()
        //.defaultSuccessUrl("http://localhost:8081/") // 로그인 성공 후 url 이동
        .userInfoEndpoint()// oauth2 로그인 성공 후 가져올 때의 설정들
        // 소셜로그인 성공 시 후속 조치를 진행할 UserService 인터페이스 구현체 등록
        .userService(customOAuth2UserService)// 리소스 서버에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능 명시
        .and()
        .successHandler(oAuth2AuthenticationSuccessHandler)// 토큰을 파라미터로 넘겨줌
        .failureHandler(oAuth2AuthenticationFailureHandler);//리다이렉트 하면서 에러메시지 전달

    // Add our custom Token based authentication filter
    http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
  }


  //swagger 관련 리소스 시큐리티 필터 제거
  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html",
        "/webjars/**", "/swagger/**", "/swagger-ui/**");
  }

  //JWT
  @Bean
  public TokenAuthenticationFilter tokenAuthenticationFilter() {
    return new TokenAuthenticationFilter();
  }

  @Bean
  public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
    return new HttpCookieOAuth2AuthorizationRequestRepository();
  }
}