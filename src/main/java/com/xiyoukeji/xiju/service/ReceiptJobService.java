package com.xiyoukeji.xiju.service;

import java.util.Date;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;


@Service
public class ReceiptJobService {
	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;

	public void addJob(String jobName, String jobGroup, Integer id, long time) throws SchedulerException {
		Date date = new Date();
		date.setTime(time);
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);

		SimpleTrigger trigger = (SimpleTrigger) scheduler
				.getTrigger(triggerKey);

		Class<ReceiptJob> clazz = ReceiptJob.class;

		JobDetail jobDetail = JobBuilder.newJob(clazz)
				.withIdentity(jobName, jobGroup).build();

		jobDetail.getJobDataMap().put("receipt_id", id);

		trigger = (SimpleTrigger) TriggerBuilder
				.newTrigger().withIdentity(jobName, jobGroup).startAt(date).build();

		scheduler.scheduleJob(jobDetail, trigger);
	}
}
