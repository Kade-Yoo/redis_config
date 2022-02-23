package com.example.demo.product.service.impl;

import com.example.demo.product.service.ProductService;
import com.example.demo.redis.Product;
import com.example.demo.redis.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final RedisService redisService;

    @Override
    public List<Product> getProductByRedis() {
        return redisService.getValueOperation("" , Product.class);
    }

    @Override
    public void setProductByRedis() {
        redisService.setValueOperations("", getProduct());
    }

    private List<Product> getProduct() {
        List<Product> responses = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            responses.add(new Product(1 + i, "A" + i, 30000 + random.nextInt()));
        }
        return responses;
    }
}
