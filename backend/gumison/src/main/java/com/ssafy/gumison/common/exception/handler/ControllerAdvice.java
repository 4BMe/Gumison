package com.ssafy.gumison.common.exception.handler;

import com.ssafy.gumison.common.exception.ResourceNotFoundException;
import com.ssafy.gumison.common.response.ApiResponseDto;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {
  @ExceptionHandler(ResourceNotFoundException.class)
  public ApiResponseDto<?> handleResourceNotFoundException(ResourceNotFoundException e){
    return ApiResponseDto.fail(404, e.getMessage());
  }
}
