package com.example.demo.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = AppConfig.class)
class AppConfigTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void redisConnections() {
        ValueOperations<Object, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set("0", "1");

        Object o = valueOperations.get("0");
        assertEquals(o.toString(), "1");
        System.out.printf("Result : " + o.toString());
    }
}