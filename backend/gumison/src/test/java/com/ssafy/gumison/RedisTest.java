package com.ssafy.gumison;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@SpringBootTest
public class RedisTest {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Test
	public void 기본_값_설정_조회_기능() {
		final String KEY = "testKey";
		final String VALUE = "testValue";

		ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();

		valueOperations.set(KEY, VALUE);
		Assertions.assertEquals(valueOperations.get(KEY), VALUE);


	}

}
