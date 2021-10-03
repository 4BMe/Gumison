package com.ssafy.gumison.common.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {

  private String resource;
  private String key;
  private Object value;

  public ResourceNotFoundException(String resource, String key, Object value) {
    super(String
        .format("resource not found : resource - %s, key - %s value - '%s'", resource, key, value));
    this.resource = resource;
    this.key = key;
    this.value = value;
  }
}
