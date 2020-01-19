package com.tokyo3rd.config;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tokyo3rd.service.BangumiApiService;
import com.tokyo3rd.util.RedisCacheUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @Author: CodeCRH
 * @Date: 2020/1/19
 */
@Order(1)
@Configuration
public class InitConfig {

    private static Logger log = LoggerFactory.getLogger(InitConfig.class);

    @Value("${system.appname}")
    private String appName;

    @Value("${system.username}")
    private String userName;

    @Value("${system.password}")
    private String password;

    @Value("${system.redishost}")
    private String redishost;

    @Value("${system.redisport}")
    private Integer redisPort;

    @Value("${system.redispassword}")
    private String redispassword;

    @Value("${system.rediskey}")
    private String rediskey;

    @Resource
    private BangumiApiService bangumiApiService;

    @Bean
    public void init() {
        log.info("----  Init Serivce Start ------");
        try {
            log.info(" Appname : {}", appName);
            log.info(" Username : {}", userName);
            log.info(" Password is not Null : {}", !StringUtils.isEmpty(password));
            log.info(" Redis host : {}", redishost);
            log.info(" Redis port : {}", redisPort);
            log.info(" Redis key : {}", rediskey);
            initBangumi();
        } catch (Exception e) {
            log.warn("----  Init Serivce Error ------");
            log.warn(" {} ", e);
        }
    }


    public void initBangumi() {
        try {
            RedisCacheUtil redisCacheUtil = RedisCacheUtil.getRedisCacheUtil(redishost, redisPort, redispassword);

            JSONObject user = bangumiApiService.login(appName, userName, password);

            if (user == null) {
                log.warn("user is null");
                return;
            }

            JSONArray collection = bangumiApiService.collection(user.getString("id"));
            if (collection == null) {
                log.warn("collection data is null");
                return;
            }

            Integer collectionSize = collection.size();
            Integer collectionLoop = 0;
            HashMap<String, String> map = new HashMap<>(collectionSize);
            while (collectionLoop < collectionSize) {
                JSONObject collectionData = collection.getJSONObject(collectionLoop);
                JSONObject progressData = bangumiApiService.progress(user.getString("id"), collectionData.getString("subject_id"), appName, user.getString("auth_encode"));
                JSONObject simpleSubject = bangumiApiService.simpleSubject(collectionData.getString("subject_id"));
                JSONObject largeSubject = bangumiApiService.largeSubject(collectionData.getString("subject_id"));
                collectionData.put("progressData", progressData);
                collectionData.put("simpleSubject", simpleSubject.getString("summary"));
                collectionData.put("largeSubject", largeSubject.getString("summary"));
                map.put(simpleSubject.getString("name_cn"), collectionData.toJSONString());
                redisCacheUtil.hmset(rediskey, map);
                collectionLoop++;
            }
            log.info("--- Get bangumi data end , get data count : {} ---", collectionLoop);
        } catch (Exception e) {
            log.warn(" Init error {}", e);
        }
    }


}
