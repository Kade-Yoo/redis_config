package com.example.demo.redis;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class RedisRequest implements Serializable {

    private String a;
    private String b;
    private String c;
    private String d;

    public RedisRequest() {
        this.a = "a";
        this.b = "b";
        this.c = "c";
        this.d = "d";
    }
}
