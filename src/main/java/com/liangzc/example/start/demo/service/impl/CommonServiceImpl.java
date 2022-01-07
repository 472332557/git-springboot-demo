package com.liangzc.example.start.demo.service.impl;

import com.liangzc.example.start.demo.bean.ConsultConfigArea;
import com.liangzc.example.start.demo.dao.CommonMapper;
import com.liangzc.example.start.demo.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class CommonServiceImpl implements CommonService {


    @Autowired
    private CommonMapper commonMapper;


    @Override
    public List<ConsultConfigArea> queryConfigArea() {
        return commonMapper.qryArea(new HashMap());
    }
}
