package com.liangzc.example.jvm.oom;

import com.liangzc.example.jvm.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HeapOOMTest {


    List<User> list = new ArrayList<>();

    @GetMapping("/heap")
    public void start(){

        while (true){
            list.add(new User());
        }
    }

}
