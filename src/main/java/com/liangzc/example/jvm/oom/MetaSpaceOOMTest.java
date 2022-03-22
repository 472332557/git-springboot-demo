package com.liangzc.example.jvm.oom;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MetaSpaceOOMTest {


    List<Class<?>> list = new ArrayList<>();

    @GetMapping("/noheap")
    public void nonheap(){
        System.out.println("----------------------------------------------");
     while(true){
         list.addAll(MyMetaspace.createClasses());
         }
    }

}
