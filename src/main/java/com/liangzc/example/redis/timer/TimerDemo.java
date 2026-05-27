package com.liangzc.example.redis.timer;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.Timer;


import java.util.concurrent.TimeUnit;

/**
 * @author liangzc
 * @date 2026/5/26 10:21
 */
public class TimerDemo {

    public static void main(String[] args) {
        Timer timer = new HashedWheelTimer();
        Timeout timeout1 = timer.newTimeout(timeout -> System.out.println("定时任务10s执行了"), 10, TimeUnit.SECONDS);
        timer.newTimeout(timeout -> System.out.println("定时任务20s执行了"), 20, TimeUnit.SECONDS);
    }
}

