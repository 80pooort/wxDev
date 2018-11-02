package com.fykj.wxDev.job;


import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

/**
 * @Author: wujian
 * @Date: 2018/11/2 22:55
 */
@Component
public class QuartzJobTemplate implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("quartzJobTemplate job 执行...");
    }
}
