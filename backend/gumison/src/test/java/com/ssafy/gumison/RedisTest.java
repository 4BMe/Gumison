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
	public void 값_설정_및_조회_삭제_기능을_수행한다() {
		final String KEY = "testKey";
		final String VALUE = "testValue";

		ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();

		valueOperations.set(KEY, VALUE);

		Assertions.assertEquals(valueOperations.get(KEY), VALUE);
		stringRedisTemplate.delete(KEY);
		Assertions.assertNull(valueOperations.get(KEY));
	}

}
