package com.ssafy.gumison.api.service;

import com.ssafy.gumison.common.dto.SessionUser;
import javax.servlet.http.HttpSession;

public interface OauthService  {
    SessionUser getCurrentUser(HttpSession httpSession);
}
