package com.liangzc.example.start.demo.controller;

import com.alibaba.fastjson.JSON;
import com.liangzc.example.redis.BloomFilterCache;
import com.liangzc.example.start.demo.bean.ConsultConfigArea;
import com.liangzc.example.start.demo.model.User;
import com.liangzc.example.start.demo.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
public class WebController {

    @Autowired
    private CommonService commonService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    @RequestMapping("/start")
    @ResponseBody
    public String start(@RequestParam("id") Integer id){
        return "hello lzc start"+id;
    }


    @RequestMapping("/filter")
    @ResponseBody
    public String filter(@RequestParam("id") Integer id){
        return "hello lzc"+id;
    }

    @PostMapping("/postTest")
    @ResponseBody
    public String postDemo(@RequestParam("id") Integer id){
        return "post"+ id;
    }

    @PostMapping("/postJsonTest")
    @ResponseBody
    public String postDemo(@RequestBody User user){
        return "post"+ user;
    }


    @PostMapping("/responseTest")
    @ResponseBody
    public String responseTest(@RequestBody User user) {
        log.info("==================user:{},person:{}", JSON.toJSONString(user));

        return "post" + user;
    }


    /**
     * 将数据添加进redis
     * @param code
     * @return
     */
    @GetMapping("/redisTest/{code}")
    @ResponseBody
    public String resdisTest(@PathVariable("code") String code){
        List<ConsultConfigArea> consultConfigAreas = commonService.queryConfigArea();
        log.info("============返回mysql查询信息{}",JSON.toJSONString(consultConfigAreas));
        if(!consultConfigAreas.isEmpty()){
            consultConfigAreas.parallelStream().forEach( conf ->{
                redisTemplate.opsForValue().set(conf.getAreaCode()+ BloomFilterCache.KEY_FLAG,JSON.toJSONString(conf));
//                redisTemplate.opsForHash().put(conf.getAreaCode()+":hash",conf.getAreaCode(),conf );
            });
        }
        return "SUCCESS";
    }


    /**
     * 通过布隆过滤器来校验是否存在，存在则从redis获取
     * @param code
     * @return
     */
    @GetMapping("/getMsgByRedis/{code}")
    @ResponseBody
    public String getMsgByRedis(@PathVariable("code") String code){
        String key = code + BloomFilterCache.KEY_FLAG;
        if (BloomFilterCache.bloomFilter.mightContain(key)){
            return redisTemplate.opsForValue().get(key).toString();
        }
        return "数据未存在缓存中";
    }


    /**
     * 搭配redisson实现分布式锁来操作redis数据
     * @param code
     * @return
     */
    @GetMapping("/redissonTest/{code}")
    @ResponseBody
    public String resdissonTest(@PathVariable("code") String code){
        List<ConsultConfigArea> consultConfigAreas = commonService.queryConfigArea();
        log.info("============返回mysql查询信息{}",JSON.toJSONString(consultConfigAreas));

        RLock lock = redissonClient.getLock("testLock");
        /**
         * tryLock()：不会阻塞进程
         *
         */
        boolean tryLock = lock.tryLock();
        if(tryLock){
            log.info("========================获取锁成功================");
            try {
                Thread.sleep(20000);
                if(!consultConfigAreas.isEmpty()){
                    consultConfigAreas.parallelStream().forEach( conf ->{
                        redisTemplate.opsForValue().set(conf.getAreaCode()+ BloomFilterCache.KEY_LOCK_FLAG,JSON.toJSONString(conf));
//
                    });
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                log.info("========================释放锁================");
                lock.unlock();
            }

        }
        return "SUCCESS";
    }

}
