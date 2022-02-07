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

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    private ZSetOperations<String, String> zSetOperations;

    @PostConstruct
    public void init() {
        this.zSetOperations = redisTemplate.opsForZSet();
    }

    @Override
    public void setRedis() {
        List<RedisResponse> responses = new ArrayList<>();
        RedisRequest key = new RedisRequest();

        for (int i = 0; i < 100; i++) {
            responses.add(new RedisResponse(1 + i, "b" + i, 3 + i, "d" + i));
        }

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
        return reverseRange("firstKey", 0, -1, RedisResponse.class);
    }

    /**
     * Redis ZSet reverseRange
     *
     * @param key 키
     * @param start 범위 시작
     * @param end 범위 끝
     * @param classType 클래스타입
     * @return 범위에 해당하는 List<T>조회
     */
    private <T> List<T> reverseRange(String key, long start, long end, Class<T> classType) {
        List<T> result = new ArrayList<>(Collections.emptyList());
        Set<String> strings = zSetOperations.reverseRange(key, start, end);
        ObjectMapper objectMapper = new ObjectMapper();
        Objects.requireNonNull(strings).forEach(str -> {
            try {
                result.add(objectMapper.readValue(str, classType));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
        return result;
    }
}
