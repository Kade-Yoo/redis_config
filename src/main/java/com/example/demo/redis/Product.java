package com.example.demo.redis;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Product {

    private int order;
    private String productNo;
    private int productAmount;

    public Product() {}

    public Product(int order, String productNo, int productAmount) {
        this.order = order;
        this.productAmount = productAmount;
        this.productNo = productNo;
    }
}
