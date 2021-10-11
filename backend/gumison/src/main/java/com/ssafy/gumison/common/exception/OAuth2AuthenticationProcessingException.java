package com.ssafy.gumison.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OAuth2AuthenticationProcessingException extends RuntimeException {

  public OAuth2AuthenticationProcessingException(String message) {
    super(message);
  }
}
