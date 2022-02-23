package com.example.demo.redis.service.impl;

import com.example.demo.redis.Product;
import com.example.demo.redis.service.RedisService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {

    private final RedisTemplate<String, String> redisTemplate;
    private ZSetOperations<String, String> zSetOperations;
    private ValueOperations<String, String> valueOperations;

    @PostConstruct
    public void init() {
        this.zSetOperations = redisTemplate.opsForZSet();
        this.valueOperations = redisTemplate.opsForValue();
    }

    /**
     * Redis value set
     *
     * @param key 키
     * @param values 값
     */
    @Override
    public <T> void setValueOperations(String key, List<T> values) {
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            String valueAsString = objectMapper.writeValueAsString(values);
            valueOperations.set(key, valueAsString);
        } catch (JsonProcessingException e) {
            log.debug("Redis Set ValueOperation JSON Parsing Exception : {}", e);
        }
    }

    /**
     * Redis value set
     *
     * @param key 키
     * @param classType 모델 타입
     */
    @Override
    public <T> List<T> getValueOperation(String key, Class<T> classType) {
        String value = valueOperations.get(key);
        if (!StringUtils.hasLength(value)) {
            return Collections.emptyList();
        }

        List<T> result = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            T readValue = objectMapper.readValue(value, classType);

            if (readValue instanceof Collection) {
                result.addAll((ArrayList) readValue);
            }
        } catch (JsonProcessingException e) {
            log.debug("Redis Get ValueOperation JSON Parsing Exception : {}", e);
        }
        return result;
    }

    /**
     * ZSet add
     *
     * @param key 키
     * @param values 값
     */
    @Override
    public <T> void addZSetOperation(String key, List<T> values) {
        final ObjectMapper objectMapper = new ObjectMapper();
        values.forEach(value -> {
            try {
                zSetOperations.add(key, objectMapper.writeValueAsString(value), 0);
            } catch (JsonProcessingException e) {
                log.debug("Redis add ZSetOperation JSON Parsing Exception : {}", e);
            }
        });
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
    @Override
    public <T> List<T> reverseRangeZSetOperation(String key, long start, long end, Class<T> classType) {
        List<T> result = new ArrayList<>(Collections.emptyList());
        Set<String> strings = zSetOperations.reverseRange(key, start, end);
        ObjectMapper objectMapper = new ObjectMapper();
        Objects.requireNonNull(strings).forEach(str -> {
            try {
                result.add(objectMapper.readValue(str, classType));
            } catch (JsonProcessingException e) {
                log.debug("Redis reverseRange ZSetOperation JSON Parsing Exception : {}", e);
            }
        });
        return result;
    }
}
