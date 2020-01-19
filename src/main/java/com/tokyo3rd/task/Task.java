package com.tokyo3rd.task;

import com.tokyo3rd.config.InitConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author: CodeCRH
 * @Date: 2020/1/19
 */
@Component
@EnableScheduling
public class Task {

    private static Logger log = LoggerFactory.getLogger(Task.class);

    @Resource
    private InitConfig initConfig;

    @Scheduled(cron = "0 0 * * * ?")
    public void task() {
        log.info("--- task start ---");
        initConfig.initBangumi();
        log.info("--- task end ---");
    }
}
