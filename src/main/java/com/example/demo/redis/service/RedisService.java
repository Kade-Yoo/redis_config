package com.example.demo.redis.service;

import com.example.demo.redis.RedisResponse;

import java.util.List;

public interface RedisService {

    void setRedis();
    List<RedisResponse> getRedis();
}
