package com.ssafy.gumison;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;

@SpringBootTest
public class RedisConnectionTest {

  @Autowired
  private StringRedisTemplate stringRedisTemplate;

  @Autowired
  private RedisTemplate<String, Object> redisTemplate;

  @Test
  public void 값_설정_및_조회_삭제_기능을_수행한다() {
    //given
    final String KEY = "testKey";
    final String VALUE = "testValue";

    ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();

    //when
    valueOperations.set(KEY, VALUE);

    //then
    assertEquals(valueOperations.get(KEY), VALUE);
    stringRedisTemplate.delete(KEY);
    assertNull(valueOperations.get(KEY));
  }

  @Test
  public void ZSET에_값_설정_조회_삭제_기능을_수행한다() {

    //given
    final String KEY = "TEST KEY";
    final Integer[] SCORES = {300, 500, 100};
    final String[] VALUES = {"testVal 0", "testVal 1", "testVal 2"};

    ZSetOperations<String, Object> zSetOperations = redisTemplate.opsForZSet();

    //when
    for (int i = 0; i < SCORES.length; i++) {
      assertTrue(zSetOperations.add(KEY, VALUES[i], SCORES[i]).booleanValue());
    }

    //then
    assertEquals(zSetOperations.rank(KEY, VALUES[2]), 0);

    for (int i = 0; i < SCORES.length; i++) {
      assertNotNull(zSetOperations.remove(KEY, VALUES[i]));
    }

  }
}
