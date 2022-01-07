package com.liangzc.example.start.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.liangzc.example.start.demo.bean.ConsultConfigArea;
import com.liangzc.example.start.demo.model.Person;
import com.liangzc.example.start.demo.model.User;
import com.liangzc.example.start.demo.service.CommonService;
import lombok.extern.slf4j.Slf4j;
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


    @GetMapping("/redisTest/{code}")
    @ResponseBody
    public String resdisTest(@PathVariable("code") String code){
        List<ConsultConfigArea> consultConfigAreas = commonService.queryConfigArea();
        log.info("============返回mysql查询信息{}",JSON.toJSONString(consultConfigAreas));
        if(!consultConfigAreas.isEmpty()){
            consultConfigAreas.parallelStream().forEach( conf ->{
                redisTemplate.opsForValue().set(conf.getAreaCode(),conf.getAreaName());
            });
        }
        return "SUCCESS";
    }

}
