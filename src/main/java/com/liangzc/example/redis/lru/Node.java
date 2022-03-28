package com.liangzc.example.redis.lru;

import lombok.Data;

@Data
public class Node {

    private String key;
    private String value;
    Node prev;
    Node next;

    public Node() {
    }

    public Node(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
