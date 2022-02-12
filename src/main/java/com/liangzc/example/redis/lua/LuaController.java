package com.liangzc.example.redis.lua;

import org.redisson.api.RFuture;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * 使用lua执行redis命令实现访问限流
 * 注意：要想测试这个类，请使用classpath下的lua_config配置文件进行测试，否则失败，可能是必须按照yaml方式指定配置文件
 * 并且RedissonConfig类要注释，原因可能为：这样会覆盖配置文件redisson.yml里面的序列化方式，因为配置类中并没有指定序列化方式
 *
 *  忽略以上说明：在java配置类RedissonConfig中也可以指定序列化方式。之间报错的原因就是因为，没有指定序列化方式。
 */

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
        List<Object> keyList = Arrays.asList("Limit:"+id);
        RScript rScript = redissonClient.getScript();
        /**
         *         10 ： 表示参数1 ——> ARGV[1] 时间10秒
         *         3 ： 表示参数2 ——> ARGV[2] 访问次数
         */
        RFuture<Object> rFuture = rScript.evalAsync(RScript.Mode.READ_WRITE,
                LIMIT_LUA, RScript.ReturnType.INTEGER, keyList, 10, 3);
        return rFuture.get().toString();
    }

}
