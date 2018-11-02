package com.fykj.wxDev.util;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

/**
 * @Author: wujian
 * @Date: 2018/11/2 23:09
 */
@Component
public class QuartzManager {
    private static Logger logger = LoggerFactory.getLogger(QuartzManager.class);
    private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();
    private static final String JOB_GROUP = "wxDevJobGroup";
    private static final String TRIGGER_GROUP = "wxDevTriggerGroup";

    /**
     * 添加一个job
     *
     * @param quartzName
     * @param jobClass
     * @param cron
     */
    public static void addJob(String quartzName, Class jobClass, String cron) {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(quartzName, JOB_GROUP).build();
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            triggerBuilder.withIdentity(quartzName, TRIGGER_GROUP);
            triggerBuilder.startNow();
            triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
            CronTrigger trigger = (CronTrigger) triggerBuilder.build();
            scheduler.scheduleJob(jobDetail, trigger);
            // 启动
            if (!scheduler.isShutdown()) {
                scheduler.start();
            }
        } catch (Exception e) {
            logger.error("add job error :", e);
        }

    }

    /**
     * @param quartzName 触发器名
     * @param cron       时间设置，参考quartz说明文档
     * @Description: 修改一个任务的触发时间
     */
    public static void modifyJobTime(String quartzName, String cron) {
        try {
            Scheduler sched = schedulerFactory.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(quartzName, TRIGGER_GROUP);
            CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerKey);
            if (trigger == null) {
                return;
            }

            String oldTime = trigger.getCronExpression();
            if (!oldTime.equalsIgnoreCase(cron)) {
                /** 方式一 ：调用 rescheduleJob 开始 */
                // 触发器
                TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
                // 触发器名,触发器组
                triggerBuilder.withIdentity(quartzName, TRIGGER_GROUP);
                triggerBuilder.startNow();
                // 触发器时间设定
                triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
                // 创建Trigger对象
                trigger = (CronTrigger) triggerBuilder.build();
                // 方式一 ：修改一个任务的触发时间
                sched.rescheduleJob(triggerKey, trigger);
                /** 方式一 ：调用 rescheduleJob 结束 */

                /** 方式二：先删除，然后在创建一个新的Job  */
                //JobDetail jobDetail = sched.getJobDetail(JobKey.jobKey(jobName, jobGroupName));
                //Class<? extends Job> jobClass = jobDetail.getJobClass();
                //removeJob(jobName, jobGroupName, triggerName, triggerGroupName);
                //addJob(jobName, jobGroupName, triggerName, triggerGroupName, jobClass, cron);
                /** 方式二 ：先删除，然后在创建一个新的Job */
            }
        } catch (Exception e) {
           logger.error("modify job error:",e);
        }
    }

    /**
     * @param quartzName
     * @Description: 移除一个任务
     */
    public static void removeJob(String quartzName) {
        try {
            Scheduler sched = schedulerFactory.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(quartzName, TRIGGER_GROUP);
            sched.pauseTrigger(triggerKey);// 停止触发器
            sched.unscheduleJob(triggerKey);// 移除触发器
            sched.deleteJob(JobKey.jobKey(quartzName, JOB_GROUP));// 删除任务
        } catch (Exception e) {
           logger.error("remove job error:",e);
        }
    }

    /**
     * @Description:关闭所有定时任务
     */
    public static void shutdownJobs() {
        try {
            Scheduler sched = schedulerFactory.getScheduler();
            if (!sched.isShutdown()) {
                sched.shutdown();
            }
        } catch (Exception e) {
           logger.error("shutdown job error",e);
        }
    }

    /**
     * 安全关闭
     *
     * @throws SchedulerException
     */
    public static void safeShutdown() throws SchedulerException {
        Scheduler scheduler = schedulerFactory.getScheduler();
        int executingJobSize = scheduler.getCurrentlyExecutingJobs().size();
        logger.info("安全关闭 当前还有" + executingJobSize + "个任务正在执行，等待完成后关闭");
        //等待任务执行完后安全关闭
        scheduler.shutdown(true);
        logger.info("安全关闭 成功");
    }

}
