package com.xiyoukeji.xiju.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xiyoukeji.xiju.core.enums.ReceiptStatus;
import com.xiyoukeji.xiju.domain.PayMsg;
import com.xiyoukeji.xiju.model.Receipt;

public class ReceiptJob implements Job {
	Logger logger = Logger.getLogger(ReceiptJob.class);

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		Integer receipt_id = (Integer) context.getJobDetail().getJobDataMap()
				.get("receipt_id");
		ApplicationContext appContext=null;
		try {
			appContext=getApplicationContext(context);
			ReceiptService receiptService = appContext
					.getBean(ReceiptService.class);
			GoodsColorStyleService goodsColorStyleService = appContext
					.getBean(GoodsColorStyleService.class);
			logger.info("===========update receiptJob" + receipt_id
					+ "==============" + System.currentTimeMillis() / 1000);
			Receipt receipt = receiptService.getReceiptById(receipt_id);
			
			logger.info("========"+receipt.getPayJson()+"======"+receipt.getStatus());
			if (receipt.getStatus().equals(ReceiptStatus.NOTPAY.value())) {
				// 订单设置为失效
				receipt.setStatus(ReceiptStatus.USELESS.value());
				receiptService.update(receipt);
				List<PayMsg> payMsgList = format(receipt.getPayJson());
				// 商品库存还原
				for (PayMsg payMsg : payMsgList) {
					goodsColorStyleService.addCount(payMsg.getGoodsColorStyleId(),
							payMsg.getGoodsAmount());
				}

			}
			logger.info("===========update receiptJob" + receipt_id
					+ "==============" + System.currentTimeMillis() / 1000 + " end");
		} catch (Exception e) {
			logger.info(e.getMessage());
			logger.info("===========update receiptJob error" + receipt_id
					+ "==============" + System.currentTimeMillis() / 1000 + " end");
		}


	}

	public List<PayMsg> format(String jsonStr) {
		List<PayMsg> payMsgList = new ArrayList<PayMsg>();
		try {
			JSONObject json = JSONObject.parseObject(jsonStr);
			JSONArray resPayList = JSONObject.parseArray(json
					.getString("payMsg"));
			for (Object jv3 : resPayList) {
				JSONObject jv4 = (JSONObject) jv3;
				logger.info(jv4.getString("goodsId") + "||"
						+ jv4.getString("goodsAmount") + "||"
						+ jv4.getString("goodsColor") + "||"
						+ jv4.getString("goodsStyle") + "||"
						+ jv4.getString("goodsColorStyleId") + "||"
						+ jv4.getString("goodsName") + "||"
						+ jv4.getString("goodsSmallUrl") + "||"
						+ jv4.getString("colorName"));

				payMsgList.add(new PayMsg(Integer.parseInt(jv4
						.getString("goodsId")), Integer.parseInt(jv4
						.getString("goodsAmount")),
						jv4.getString("goodsColor"), jv4
								.getString("goodsStyle"), Integer.parseInt(
								jv4.getString("goodsColorStyleId")),
								jv4.getString("goodsName"),
								jv4.getString("goodsSmallUrl"),
								jv4.getString("colorName")));

			}
		} catch (Exception e) {
			return null;
		}
		return payMsgList;
	}

	private static final String APPLICATION_CONTEXT_KEY = "applicationContextKey";

	private ApplicationContext getApplicationContext(JobExecutionContext context)
			throws Exception {
		ApplicationContext appCtx = null;
		appCtx = (ApplicationContext) context.getScheduler().getContext()
				.get(APPLICATION_CONTEXT_KEY);
		if (appCtx == null) {
			throw new JobExecutionException(
					"No application context available in scheduler context for key \""
							+ APPLICATION_CONTEXT_KEY + "\"");
		}
		return appCtx;
	}

}
