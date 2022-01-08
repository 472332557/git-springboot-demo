package com.liangzc.example.start.demo.bean;

import java.io.Serializable;

//@Alias("ConsultConfigArea")
public class ConsultConfigArea implements Serializable {

    public String areaCode;

    public String areaName;

    public Integer state;
    
    public String getAreaCode() {
        return areaCode;
    }
    
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
    
    public String getAreaName() {
        return areaName;
    }
    
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
    
    public Integer getState() {
        return state;
    }
    
    public void setState(Integer state) {
        this.state = state;
    }
}
