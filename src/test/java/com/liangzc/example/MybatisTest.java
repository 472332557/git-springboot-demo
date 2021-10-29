package com.liangzc.example;

import com.liangzc.example.start.demo.bean.ConsultConfigArea;
import com.liangzc.example.start.demo.dao.CommonMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.HashMap;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootApp.class)
public class MybatisTest {

    private static final Logger logger = LoggerFactory.getLogger(MybatisTest.class);

    @Autowired
    private CommonMapper commonMapper;


    @Test
    public void mapper() {
        List<ConsultConfigArea> consultConfigAreas = commonMapper.qryArea(new HashMap());
        for (ConsultConfigArea consultConfigArea : consultConfigAreas) {
            logger.info(consultConfigArea.getAreaCode() + "   " + consultConfigArea.getAreaName() + "  " + consultConfigArea.getState());
        }
    }


}
