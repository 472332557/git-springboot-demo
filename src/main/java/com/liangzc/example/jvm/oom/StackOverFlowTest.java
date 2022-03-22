package com.liangzc.example.jvm.oom;

public class StackOverFlowTest {


    public static long count = 0;

    public static void main(String[] args) {
        method();
    }

    public static void method(){
        System.out.println(count ++);
        method();
    }
}
