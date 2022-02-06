package com.example.demo.redis.service.impl;

import com.example.demo.redis.RedisRequest;
import com.example.demo.redis.RedisResponse;
import com.example.demo.redis.service.RedisService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate<?, ?> redisTemplate;

    @Override
    public void setRedis() {
        List<RedisResponse> responses = new ArrayList<>();
        RedisRequest key = new RedisRequest();

        for (int i = 0; i < 100; i++) {
            responses.add(new RedisResponse(1 + i, "b" + i, 3 + i, "d" + i));
        }

        ZSetOperations<String , String> zSetOperations = (ZSetOperations<String , String>) redisTemplate.opsForZSet();
        ObjectMapper objectMapper = new ObjectMapper();
        for (RedisResponse respons : responses) {
            if (Objects.isNull(respons)) {
                continue;
            }

            try {
                zSetOperations.add("firstKey", objectMapper.writeValueAsString(respons), respons.getA());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<RedisResponse> getRedis() {
        List<RedisResponse> result = new ArrayList<>(Collections.emptyList());
        ZSetOperations<String , String> zSetOperations = (ZSetOperations<String , String>) redisTemplate.opsForZSet();
        Set<String> firstKey = zSetOperations.reverseRange("firstKey", 0, -1);

        ObjectMapper objectMapper = new ObjectMapper();
        if (firstKey instanceof LinkedHashSet<?>) {
            firstKey.forEach(value -> {
                try {
                    result.add(objectMapper.readValue(value, RedisResponse.class));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            });
        }
        return result;
    }
}
