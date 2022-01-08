package com.liangzc.example.redis;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.liangzc.example.start.demo.bean.ConsultConfigArea;
import com.liangzc.example.start.demo.service.CommonService;
import kotlin.text.Charsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 使用布隆过滤器，将数据在应用启动时加载进来
 */
@Component
@Slf4j
public class BloomFilterDataOfApplicationRunner implements ApplicationRunner {

    @Autowired
    private CommonService commonService;
    
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("初始化数据加载到布隆过滤器中=========>start");
        List<ConsultConfigArea> consultConfigAreas = commonService.queryConfigArea();
        BloomFilter<String> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), 10000, 0.02);
        consultConfigAreas.parallelStream().forEach(conf ->{
            //将数据加载进布隆过滤器中
            bloomFilter.put(conf.getAreaCode()+BloomFilterCache.KEY_FLAG);
        });
        //初始化布隆过滤器
        BloomFilterCache.bloomFilter = bloomFilter;
    }
}
