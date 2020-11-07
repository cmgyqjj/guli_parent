package com.atguigu.staservice.schedule;

import com.atguigu.staservice.service.StatisticsDailyService;
import com.atguigu.staservice.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScheduledTask {

    @Autowired
    private StatisticsDailyService statisticsDailyService;
//
//
//
////    表示每隔5秒执行一次这个方法
//    @Scheduled(cron = "0/5 * * * * ?")
//    public void task1(){
//        System.out.println("task1执行了");
//    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void task2(){
        String day = DateUtil.formatDate(DateUtil.addDays(new Date(),-1));
        statisticsDailyService.registerCount(day);
    }

}
