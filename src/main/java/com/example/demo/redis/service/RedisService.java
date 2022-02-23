package com.example.demo.redis.service;

import com.example.demo.redis.Product;

import java.util.List;

public interface RedisService {

    <T> void setValueOperations(String key, List<T> values);
    <T> List<T> getValueOperation(String key, Class<T> classType);
    <T> void addZSetOperation(String key, List<T> value);
    <T> List<T> reverseRangeZSetOperation(String key, long start, long end, Class<T> classType);
}
