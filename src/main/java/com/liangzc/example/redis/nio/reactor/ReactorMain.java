package com.liangzc.example.redis.nio.reactor;

import java.io.IOException;

public class ReactorMain {

    public static void main(String[] args) throws IOException {

        Reactor reactor = new Reactor(8888);
        new Thread(reactor).start();
    }
}
