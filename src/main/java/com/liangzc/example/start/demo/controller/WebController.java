package com.liangzc.example.start.demo.controller;

import com.liangzc.example.start.demo.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class WebController {

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
}
