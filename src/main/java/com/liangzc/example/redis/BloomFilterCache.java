package com.liangzc.example.redis;

import com.google.common.hash.BloomFilter;

public class BloomFilterCache {

    public static BloomFilter<String> bloomFilter;

    public static String KEY_FLAG = ":config";

}
