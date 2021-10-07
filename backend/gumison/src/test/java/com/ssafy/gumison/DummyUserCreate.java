package com.ssafy.gumison;

import com.ssafy.gumison.common.util.DummyUserGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DummyUserCreate {

  @Autowired
  DummyUserGenerator dummyUserGenerator;

  @Test
  public void dummyUserCreate(){
    dummyUserGenerator.generateUser();
  }
}
