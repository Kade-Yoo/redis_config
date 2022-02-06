package com.example.demo.redis.controller;

import com.example.demo.redis.RedisRequest;
import com.example.demo.redis.RedisResponse;
import com.example.demo.redis.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/redis")
public class RedisController {

    @Autowired
    private RedisService redisService;

    @PostMapping("")
    public void setRedis() {
        redisService.setRedis();
    }

    @GetMapping("")
    public List<RedisResponse> getA() {
        return redisService.getRedis();
    }
}
