package com.tokyo3rd.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tokyo3rd.service.BangumiApiService;
import com.tokyo3rd.util.RedisCacheUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Tests {
    /*
    @Test
    public void login() {
        try {
            BangumiApiService bangumiApiService = new BangumiApiService();
            JSONObject json = bangumiApiService.login("your bangumi appname","your bangumi username","your bangumi password");
            JSONArray collection = bangumiApiService.collection(json.getString("id"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void redisPut() {
        RedisCacheUtil redisCacheUtil = RedisCacheUtil.getRedisCacheUtil("127.0.0.1",6379,null);
        HashMap<String,String> map = new HashMap<>(2);
        map.put("test","test");
        redisCacheUtil.hmset("test",map);
    }
    */
}
