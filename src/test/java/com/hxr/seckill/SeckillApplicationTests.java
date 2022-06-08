package com.hxr.seckill;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class SeckillApplicationTests {
	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private RedisScript<Boolean> redisScript;

	@Test
	public void testLock01(){
		ValueOperations valueOperations = redisTemplate.opsForValue();
		//占位，key不存在才可以设置成功
		boolean isLock = valueOperations.setIfAbsent("k1","v1");
		if (isLock){
			valueOperations.set("name","xxx");
			String name = (String) valueOperations.get("name");
			System.out.println("name: "+name);
			redisTemplate.delete("name");
		}else{
			System.out.println("有线程在使用");
		}
	}

	@Test
	public void testLock02(){
		ValueOperations valueOperations = redisTemplate.opsForValue();
		//占位，key不存在才可以设置成功
		boolean isLock = valueOperations.setIfAbsent("k1","v1",5, TimeUnit.SECONDS);
		if (isLock){
			valueOperations.set("name","xxx");
			String name = (String) valueOperations.get("name");
			System.out.println("name: "+name);
			Integer.parseInt("xxxxxx");
			redisTemplate.delete("name");
		}else{
			System.out.println("有线程在使用");
		}
	}
	@Test
	public void testLock03(){
		ValueOperations valueOperations = redisTemplate.opsForValue();
		String value = UUID.randomUUID().toString();
		//占位，key不存在才可以设置成功
		boolean isLock = valueOperations.setIfAbsent("k1",value,120, TimeUnit.SECONDS);
		if (isLock){
			valueOperations.set("name","xxx");
			String name = (String) valueOperations.get("name");
			System.out.println("name: "+name);
			System.out.println(valueOperations.get("k1"));
			Boolean result = (Boolean) redisTemplate.execute(redisScript, Collections.singletonList("k1"),value);
			System.out.println(result);
		}else{
			System.out.println("有线程在使用");
		}
	}

	@Test
	void contextLoads() {
	}

}
