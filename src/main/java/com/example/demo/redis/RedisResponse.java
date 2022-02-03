package com.example.demo.redis;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class RedisResponse implements Serializable {

    private int a;
    private String b;
    private int c;
    private String d;

    public RedisResponse(int a, String b, int c, String d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }
}
