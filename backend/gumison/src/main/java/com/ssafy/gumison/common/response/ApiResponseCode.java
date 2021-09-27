package com.ssafy.gumison.common.response;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApiResponseCode {

  SUCCESS("success"),
  FAIL("fail"),
  UNAUTHORIZED("unauthorized");

  private final String message;

  public String getId() {
    return this.name();
  }

  public String getText() {
    return message;
  }

  @JsonValue
  public String toJson() {
    return this.name().toLowerCase();
  }
}