package com.example.demo.product.service;

import com.example.demo.redis.Product;

import java.util.List;

public interface ProductService {

    List<Product> getProductByRedis();
    void setProductByRedis();
}
