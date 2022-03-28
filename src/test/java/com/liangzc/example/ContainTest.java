package com.liangzc.example;

public class ContainTest {

    public static void main(String[] args) {


        String name = "张**三*";

        System.out.println(name.contains("*"));
        System.out.println(name.indexOf("*"));

    }
}
