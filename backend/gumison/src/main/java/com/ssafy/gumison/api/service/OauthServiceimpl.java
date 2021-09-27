package com.ssafy.gumison.api.service;

import com.ssafy.gumison.common.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
@Service("OauthService")
@RequiredArgsConstructor
public class OauthServiceimpl implements OauthService{
    private final Logger log = LoggerFactory.getLogger(OauthServiceimpl.class);

    @Override
    public SessionUser getCurrentUser(HttpSession httpSession) {
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        log.info("Load session user {}",  sessionUser);
        return sessionUser;
    }
}
