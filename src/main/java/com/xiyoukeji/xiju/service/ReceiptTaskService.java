package com.xiyoukeji.xiju.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiyoukeji.xiju.model.Receipt;


@Service
public class ReceiptTaskService {
	static Integer orderStart = 10000000;
	
	private static final long DEFAULTTIMEOUT = 1000 * 60 * 30L;
	static Logger logger =Logger.getLogger(ReceiptTaskService.class);
	@Autowired
	ReceiptService receiptService;
	
	@Autowired
	ReceiptJobService jobService;
	@PostConstruct
	public void addReceiptJob() throws SchedulerException{
		logger.info("========receipt add receipt task======");
		List<Receipt> list =receiptService.getNotPaidReceipt();
		for(Receipt r:list){
			logger.info("add receipt to scheduler " + r.getId());
			String order_no = String.valueOf(orderStart + r.getId());
			long uselessTime = ((long)r.getCreateTime())*1000 + DEFAULTTIMEOUT;
			if(System.currentTimeMillis()>uselessTime){
				uselessTime=System.currentTimeMillis()+20*1000L;
			}
			jobService.addJob(order_no, "receipt", r.getId(), uselessTime);
		}
	}
}
