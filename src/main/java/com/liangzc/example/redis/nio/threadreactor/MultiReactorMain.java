package com.liangzc.example.redis.nio.threadreactor;

import java.io.IOException;

public class MultiReactorMain {

    public static void main(String[] args) throws IOException {
        MultiReactor multiReactor = new MultiReactor(8888);
        new Thread(multiReactor).start();
    }
}
