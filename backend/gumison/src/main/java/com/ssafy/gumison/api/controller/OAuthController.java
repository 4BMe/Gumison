package com.ssafy.gumison.api.controller;

import com.ssafy.gumison.api.service.OauthService;
import com.ssafy.gumison.common.dto.SessionUser;
import com.sun.security.auth.UserPrincipal;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;

@Api(value="소셜 로그인")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users/oauth2/login")
public class OAuthController {

    private final OauthService oauthService;
    private final HttpSession httpSession;
    private final Logger log = LoggerFactory.getLogger(OAuthController.class);

    @GetMapping("/google")
    @ApiOperation(value = "소셜 로그인", notes = "구글 로그인을 합니다.")
    @ApiResponses({@ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"), @ApiResponse(code = 404, message = "페이지 없음"),
            @ApiResponse(code = 500, message = "서버 오류")})
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<SessionUser> getCurrentUser() {
        SessionUser sessionUser=oauthService.getCurrentUser(httpSession);
        log.info("Get user profile - {}",  sessionUser);
        return ResponseEntity.status(200).body(sessionUser);
    }
}
