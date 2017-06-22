package com.xiyoukeji.xiju.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.pingplusplus.model.Charge;
import com.xiyoukeji.xiju.core.enums.Const;
import com.xiyoukeji.xiju.core.enums.GoodsStatus;
import com.xiyoukeji.xiju.core.enums.ReceiptStatus;
import com.xiyoukeji.xiju.core.enums.VoucherState;
import com.xiyoukeji.xiju.core.utils.Utils;
import com.xiyoukeji.xiju.domain.PayMsg;
import com.xiyoukeji.xiju.domain.PingppSuccess;
import com.xiyoukeji.xiju.model.Goods;
import com.xiyoukeji.xiju.model.GoodsColorStyle;
import com.xiyoukeji.xiju.model.Receipt;
import com.xiyoukeji.xiju.model.ReceiptAddress;
import com.xiyoukeji.xiju.model.Voucher;

@Controller
public class PayController extends BaseController {

	private static final long DEFAULTTIMEOUT = 1000 * 60 * 30L;
	static Logger logger = Logger.getLogger(PayController.class);
	static Integer orderStart = 10000000;

	@RequestMapping("/user/pay.do")
	public void pay(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "payMsg", required = true) String payMsg,
			@RequestParam(value = "voucherId", required = false) Integer voucherId,
			@RequestParam(value = "payMethod", required = true) String payMethod,
			@RequestParam(value = "receiptAddressId", required = true) Integer receiptAddressId,
			@RequestParam(value = "fapiao", required = false) String fapiao,
			@RequestParam(value = "memo", required = false) String memo,
			@RequestParam(value = "cartIds", required = false) String cartIds)
			throws Exception {
		long uselessTime = System.currentTimeMillis() + DEFAULTTIMEOUT;
		Integer userId = getUserId(request);
		JSONObject jv = new JSONObject();
		List<PayMsg> payMsgList = format(payMsg);
		Receipt r = new Receipt();
		int code = validate(payMsgList, voucherId, r, payMsg, payMethod,
				userId, receiptAddressId, false);
		if (code == Const.SUCCESS) {
			r.setFapiao(fapiao);
			r.setMemo(memo);
			receiptService.save(r);
			String order_no = String.valueOf(orderStart + r.getId());
			try {
				Charge charge = pingppService.CreateOrder(order_no,
						r.getNetAmount(), payMethod, "嘻居家居", "内容", null, null,
						(int) (uselessTime / 1000 + 1000));
				jv.put("code", Const.SUCCESS);
				jv.put("charge", charge);
				logger.info("add receipt to scheduler " + r.getId());
				jobService.addJob(order_no, "receipt", r.getId(), uselessTime);
				// 红包设置为失效
				if (voucherId != null) {
					voucherService.uselessVoucher(voucherId);
				}
				// 削减库存
				for (PayMsg payMsgInfo : payMsgList) {
					goodsColorStyleService.devCount(
							payMsgInfo.getGoodsColorStyleId(),
							payMsgInfo.getGoodsAmount());
				}
				//删除购物车信息
				if(!StringUtils.isEmpty(cartIds)){
					Integer[] cIds =StringtoInt(cartIds);
					for(Integer cId:cIds){
						if(cartService.isCart(userId, cId)){
							cartService.delete(cId);
						}
					}
				}
			} catch (Exception e) {
				logger.info("pay error");
				receiptService.del(r.getId());
			}
		} else {
			jv.put("code", code);
		}
		Utils.writeBack(request, response, jv);
	}

	@RequestMapping("/user/repay.do")
	public void repay(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "receiptId", required = true) Integer receiptId)
			throws Exception {
		logger.info("receiptId=" + receiptId);
		long uselessTime = System.currentTimeMillis() + DEFAULTTIMEOUT;
		JSONObject jv = new JSONObject();
		Integer userId = getUserId(request);
		Receipt r = receiptService.getReceiptById(receiptId);
		if (r == null) {
			jv.put("code", Const.PAY_ERROR);
		} else {
			if (r.getUserId().equals(userId)
					&& r.getStatus().equals(ReceiptStatus.NOTPAY.value())) {
				List<PayMsg> payMsgList = format(r.getPayJson());
				int code = validate(payMsgList, r.getVoucherId(), r,
						r.getPayJson(), r.getPayMethod(), userId,
						r.getReceiptAddressId(), true);
				if (code == Const.SUCCESS) {
					receiptService.update(r);
					String order_no = String.valueOf(orderStart + r.getId());
					Charge charge = pingppService.CreateOrder(order_no,
							r.getNetAmount(), r.getPayMethod(), "嘻居家居", "内容",
							null, null, (int) (uselessTime / 1000));
					jv.put("code", Const.SUCCESS);
					jv.put("charge", charge);
				} else {
					jv.put("code", code);
				}
			} else {
				jv.put("code", Const.REPAY_RECEIPT_ERROR);
			}

		}
		Utils.writeBack(request, response, jv);
	}

	@RequestMapping("/callback/payresponse.do")
	public void payResponse(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PingppSuccess pingppSuccess = pingppService.Webhooks();
		logger.info("pay success oid=" + pingppSuccess.getOrder_no()
				+ " paymount=" + pingppSuccess.getAmount());
		// 支付成功后操作
		// 1、将订单置为成功
		Integer receiptId = (int) (Long.parseLong(pingppSuccess.getOrder_no()) - orderStart);
		Receipt receipt = receiptService.getReceiptById(receiptId);
		if (!receipt.getStatus().equals(ReceiptStatus.PAID.value())) {
			receipt.setPayTime((int) (System.currentTimeMillis() / 1000));
			receipt.setStatus(ReceiptStatus.PAID.value());
			receiptService.update(receipt);
		}
		response.getWriter().write("200");
	}

	private int validate(List<PayMsg> payMsgList, Integer voucherId, Receipt r,
			String payJson, String payMethod, Integer userId,
			Integer receiptAddressId, boolean isRepay) {
		int nowTime = (int) (System.currentTimeMillis() / 1000);
		int amount = 0;
		List<PayMsg> payMsgList2 = new ArrayList<PayMsg>();
		if (payMsgList == null || payMsgList != null && payMsgList.size() == 0) {
			return Const.PAY_MSG_ERROR;
		}
		Map<Integer, Integer> payAmountMap = new HashMap<Integer, Integer>();
		for (PayMsg payMsg : payMsgList) {
			Goods goods = goodsService.getById(payMsg.getGoodsId());
			if (goods.getStatus() == GoodsStatus.OFFLINE.value()) {
				return Const.GOODS_OFFLINE;
			}
			if (payMsg.getGoodsColorStyleId() == null) {
				return Const.SYSTEM_ERROR;
			}
			GoodsColorStyle goodsColorStyle = goodsColorStyleService
					.getById(payMsg.getGoodsColorStyleId());
			Integer buyAmount = payAmountMap.get(payMsg.getGoodsColorStyleId());
			if (buyAmount != null) {
				buyAmount += payMsg.getGoodsAmount();
			} else {
				buyAmount = payMsg.getGoodsAmount();
			}
			payAmountMap.put(payMsg.getGoodsColorStyleId(), buyAmount);
			payMsg.setPrice(goodsColorStyle.getSalePrice()
					* payMsg.getGoodsAmount());
			payMsgList2.add(payMsg);
		}
		Iterator<Integer> it = payAmountMap.keySet().iterator();
		while (it.hasNext()) {
			Integer key = it.next();
			Integer buyAmount = payAmountMap.get(key);
			GoodsColorStyle goodsColorStyle = goodsColorStyleService
					.getById(key);
			amount += goodsColorStyle.getSalePrice() * buyAmount;
			if (buyAmount > goodsColorStyle.getLeftCount()) {
				return Const.LEFT_COUNT_NOT_ENOUGH;
			}
		}
		Voucher voucher = null;
		int netAmount = amount;
		if (voucherId != null && !isRepay) {
			voucher = voucherService.getVoucherById(voucherId);
			if (voucher == null) {
				logger.info("voucher is null " + userId);
				return Const.VOUCHER_ERROR;
			}
			if (voucher.getVoucherStatus() != VoucherState.USEFUL.value()
					|| !(nowTime >= voucher.getVoucherBeginTime() && nowTime <= voucher
							.getVoucherEndTime())
					|| !(userId.equals(voucher.getUserId()))) {
				logger.info("voucher status=" + voucher.getVoucherStatus()
						+ " userId" + userId + " voucherUserId="
						+ voucher.getUserId());
				return Const.VOUCHER_ERROR;
			}
			if (amount < voucher.getVoucherLimit()) {
				logger.info("amount=" + amount + " voucher amount limit="
						+ voucher.getVoucherLimit());
				return Const.VOUCHER_AMOUNT_ERROR;
			}
			netAmount = amount - voucher.getVoucherAmount() < 0 ? 0 : amount
					- voucher.getVoucherAmount();
		}
		ReceiptAddress address = receiptAddressService.getReceiptAddressById(
				receiptAddressId, userId);
		if (address == null) {
			return Const.RECEIPT_ADDRESS_ERROR;
		}
		JSONObject jv = new JSONObject();
		jv.put("payMsg", payMsgList2);
		if (!isRepay) {
			r.setPayJson(payJson);
			r.setCreateTime((int) (System.currentTimeMillis() / 1000));
			r.setAmount(amount);
			r.setNetAmount(netAmount);
			r.setPayMethod(payMethod);
			r.setUserId(userId);
			r.setStatus(ReceiptStatus.NOTPAY.value());
			r.setReceiptAddressId(receiptAddressId);
			r.setAddress(address.getLocal()+" "+address.getAddress());
			r.setPayJson(jv.toJSONString());
			r.setMobile(address.getMobile());
			r.setName(address.getName());
			r.setVoucherId(voucherId);
		} else {
			r.setPayJson(jv.toJSONString());
		}
		return Const.SUCCESS;
	}

	public static void main(String[] args) {
		List<PayMsg> msg = new ArrayList<PayMsg>();
		PayMsg payMsg = new PayMsg();
		payMsg.setGoodsAmount(1);
		payMsg.setGoodsColor("#FFFFFF");
		payMsg.setGoodsId(1);
		payMsg.setGoodsStyle("圆形");
		payMsg.setColorName("goodsName");
		payMsg.setGoodsColorStyleId(2);
		payMsg.setGoodsSmallUrl("smallUrl");
		msg.add(payMsg);
		JSONObject jv = new JSONObject();
		jv.put("payMsg", msg);
		System.out.println(jv.toJSONString());
	}
}
