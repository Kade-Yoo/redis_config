package com.example.demo.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/redis")
public class RedisController {

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("")
    public void get() {
        List<RedisResponse> responses = new ArrayList<>();
        RedisRequest key = new RedisRequest();

        RedisResponse response = new RedisResponse(1,"b", 3, "d");
        for (int i = 0; i < 1000000; i++) {
            responses.add(response);
        }

        ValueOperations<RedisRequest, List<RedisResponse>> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, responses);
    }

    @GetMapping("")
    public void getA() {
        RedisRequest key = new RedisRequest();
        ValueOperations<RedisRequest, List<RedisResponse>> valueOperations = redisTemplate.opsForValue();
        List<RedisResponse> redisResponses = valueOperations.get(key);
        System.out.printf("A : " + redisResponses);
    }
}
