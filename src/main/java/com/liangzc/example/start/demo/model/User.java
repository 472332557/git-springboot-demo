package com.liangzc.example.start.demo.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.io.Serializable;

@Data
public class User implements Serializable {

    private String name;

    private String age;

    private String gender;

}
