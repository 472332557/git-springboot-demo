package com.liangzc.example.redis.lua;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RFuture;
import org.redisson.api.RLock;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


/**
 * 使用lua执行redis命令实现访问限流
 * 注意：要想测试这个类，请使用classpath下的lua_config配置文件进行测试，否则失败，可能是必须按照yaml方式指定配置文件
 * 并且RedissonConfig类要注释，原因可能为：这样会覆盖配置文件redisson.yml里面的序列化方式，因为配置类中并没有指定序列化方式
 * <p>
 * 忽略以上说明：在java配置类RedissonConfig中也可以指定序列化方式。之间报错的原因就是因为，没有指定序列化方式。
 */

@Slf4j
@RestController
@RequestMapping("/luaTest")
public class LuaController {

    @Autowired
    private RedissonClient redissonClient;

    private static String LIMIT_LUA = "local times=redis.call('incr',KEYS[1])\n" +
            "if times==1 then\n" +
            "    redis.call('expire',KEYS[1],ARGV[1])\n" +
            "end\n" +
            "if times > tonumber(ARGV[2]) then\n" +
            "    return 0\n" +
            "end\n" +
            "return 1";

    @RequestMapping("/lua/{id}")
    public String luaTest(@PathVariable("id") Integer id) throws ExecutionException, InterruptedException {
        List<Object> keyList = Arrays.asList("Limit:" + id);
        RScript rScript = redissonClient.getScript();
        /**
         *         10 ： 表示参数1 ——> ARGV[1] 时间10秒
         *         3 ： 表示参数2 ——> ARGV[2] 访问次数
         */
        RFuture<Object> rFuture = rScript.evalAsync(RScript.Mode.READ_WRITE,
                LIMIT_LUA, RScript.ReturnType.INTEGER, keyList, 10, 3);
        return rFuture.get().toString();
    }

    /**
     * Redisson 分布式锁示例
     * 模拟扣减库存场景：多线程并发访问时，只有一个线程能获取到锁，防止超卖
     */
    @RequestMapping("/lock/{productId}")
    public String distributedLockTest(@PathVariable("productId") String productId) {
        String lockKey = "product_lock:" + productId;
        RLock lock = redissonClient.getLock(lockKey);

        try {
            // tryLock(waitTime, leaseTime, unit):
            //   - 指定 leaseTime 时，看门狗不会续期，到期锁自动释放
            //   - 不指定 leaseTime（只用 tryLock(waitTime, unit)），看门狗生效，默认30s锁期，每10s自动续期
            // 此处使用看门狗模式，最多等待3秒获取锁，锁持有期间由看门狗自动续期
            boolean acquired = lock.tryLock(3, TimeUnit.SECONDS);
            if (!acquired) {
                log.info("线程:{} 获取锁失败，产品:{}", Thread.currentThread().getName(), productId);
                return "获取锁失败，请稍后重试";
            }

            log.info("线程:{} 获取锁成功，产品:{}", Thread.currentThread().getName(), productId);

            // 模拟业务操作：扣减库存
            String stockKey = "product_stock:" + productId;
            String stockStr = redissonClient.getBucket(stockKey).get() == null ? null
                    : redissonClient.getBucket(stockKey).get().toString();

            int stock = stockStr == null ? 100 : Integer.parseInt(stockStr);

            if (stock <= 0) {
                log.info("库存不足，产品:{}", productId);
                return "库存不足";
            }

            stock--;
            redissonClient.getBucket(stockKey).set(stock);
            log.info("扣减库存成功，产品:{}，剩余库存:{}", productId, stock);
            return "扣减成功，剩余库存：" + stock;

        } catch (InterruptedException e) {
            log.error("获取锁异常", e);
            Thread.currentThread().interrupt();
            return "获取锁异常";
        } finally {
            // 只有当前线程持有锁时才释放
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
                log.info("线程:{} 释放锁，产品:{}", Thread.currentThread().getName(), productId);
            }
        }
    }

}
