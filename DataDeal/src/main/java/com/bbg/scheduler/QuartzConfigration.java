package com.bbg.scheduler;

import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;



@Configuration
public class QuartzConfigration {
	
	/** 
     * attention: 
     * Details：配置定时任务 
     */  
    @Bean(name = "jobDetail")  
    public MethodInvokingJobDetailFactoryBean update_LP_REAL_BFXS_HZ_FactoryBean(BhbiScheduler task) {// ScheduleTask为需要执行的任务  
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();  
        jobDetail.setConcurrent(false);  
        jobDetail.setName("update_LP_REAL_BFXS_HZ");
        jobDetail.setGroup("bhbi");
        jobDetail.setTargetObject(task);  
        jobDetail.setTargetMethod("update_LP_REAL_BFXS_HZ");  
        return jobDetail;  
    }  
    
   

    /** 
     * attention: 
     * Details：配置定时任务的触发器，也就是什么时候触发执行定时任务 
     */  
    @Bean(name = "jobTrigger")  
    public CronTriggerFactoryBean cronJobTrigger(JobDetail  jobDetail) {  
        CronTriggerFactoryBean tigger = new CronTriggerFactoryBean();  
        tigger.setJobDetail(jobDetail);  
        tigger.setCronExpression("0 */1 * * * ?");// 初始时的cron表达式  
        tigger.setName("update_LP_REAL_BFXS_HZ_tigger");// trigger的name  
        return tigger;  
  
    } 
  
    /** 
     * attention: 
     * Details：定义quartz调度工厂 
     */  
    @Bean(name = "scheduler")  
    public SchedulerFactoryBean schedulerFactory(Trigger jobTrigger) {  
        SchedulerFactoryBean bean = new SchedulerFactoryBean();  
        bean.setOverwriteExistingJobs(true);  
        bean.setStartupDelay(20);  
        bean.setTriggers(jobTrigger);
        return bean;  
    }  
 
}