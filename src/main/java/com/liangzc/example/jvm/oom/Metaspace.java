package com.liangzc.example.jvm.oom;

public class Metaspace {


    public Class<?> defineClass(String s, byte[] code, int i, int length) {
        return Metaspace.class;
    }
}
