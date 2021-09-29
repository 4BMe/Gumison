package com.ssafy.gumison;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
/*
  스케줄러 Enable
 */
@EnableScheduling
public class GumisonApplication {

  public static void main(String[] args) {
    SpringApplication.run(GumisonApplication.class, args);
  }
}
