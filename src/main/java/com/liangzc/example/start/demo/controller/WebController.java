package com.liangzc.example.start.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WebController {

    @RequestMapping("/start")
    @ResponseBody
    public String start(@RequestParam("id") Integer id){
        return "hello lzc"+id;
    }


    @RequestMapping("/filter")
    @ResponseBody
    public String filter(@RequestParam("id") Integer id){
        return "hello lzc"+id;
    }
}
