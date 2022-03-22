package com.liangzc.example.jvm;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {

    private String name;

    private String age;

    private String gender;

}
