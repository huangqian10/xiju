package com.xiyoukeji.xiju.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.xiyoukeji.xiju.core.enums.CommentType;
import com.xiyoukeji.xiju.core.enums.Const;
import com.xiyoukeji.xiju.core.enums.ReceiptShowStatus;
import com.xiyoukeji.xiju.core.enums.ReceiptStatus;
import com.xiyoukeji.xiju.core.utils.Utils;
import com.xiyoukeji.xiju.domain.PayMsg;
import com.xiyoukeji.xiju.model.Comment;
import com.xiyoukeji.xiju.model.Receipt;

@Controller
public class ReceiptController extends BaseController {
	static Integer orderStart = 10000000;

	@RequestMapping("/user/getReceipt.do")
	public void getReceipt(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "receiptId", required = true) Integer receiptId) {
		JSONObject jv = new JSONObject();
		Integer realId = receiptId - orderStart;
		Integer userId = getUserId(request);
		Receipt receipt = receiptService.getReceiptById(realId);
		if (receipt != null && receipt.getUserId().equals(userId)) {
			receipt = transStatus(receipt);
			jv.put("info", receipt);
			jv.put("code", Const.SUCCESS);
		} else {
			jv.put("code", Const.RECEIPT_ERROR);
		}
		Utils.writeBack(request, response, jv);
	}

	@RequestMapping("/user/getAllReceipt.do")
	public void getAllReceipt(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject jv = new JSONObject();
		Integer userId = getUserId(request);
		List<Receipt> resReceipt = new ArrayList<Receipt>();
		List<Receipt> receiptList = receiptService.getMyReceipt(userId);
		for (Receipt receipt : receiptList) {
			receipt = transStatus(receipt);
			resReceipt.add(receipt);
		}
		jv.put("allReceipt", resReceipt);
		Utils.writeBack(request, response, jv);
	}

	@RequestMapping("/user/shouhuo.do")
	public void shouHuo(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "receiptId", required = true) Integer receiptId) {
		Receipt receipt = receiptService.getReceiptById(receiptId);
		JSONObject jv = new JSONObject();
		Integer userId = getUserId(request);
		if (receipt.getUserId().equals(userId)
				&& receipt.getStatus().equals(ReceiptStatus.PAID.value())
				&& !(receipt.getIsReceipt() != null && receipt.getIsReceipt()
						.equals(Receipt.ISRECEIPT))) {
			receipt.setIsReceipt(Receipt.ISRECEIPT);// 确认收货
			jv.put("code", Const.SUCCESS);
			// 增加评论表
			List<PayMsg> payMsgList = format(receipt.getPayJson());
			for (PayMsg payMsg : payMsgList) {
				Comment comment = new Comment();
				comment.setCommentType(CommentType.GOOD.value());
				comment.setIsComment(Comment.NOTCOMMENT);
				comment.setUserId(userId);
				comment.setCommentTypeId(payMsg.getGoodsId());
				comment.setReceiptId(receiptId);
				commentService.save(comment);
			}

		} else {
			jv.put("code", Const.DATA_ERROR);
		}
		Utils.writeBack(request, response, jv);
	}

	@RequestMapping("/user/getReceiptComment.do")
	public void getReceiptComment(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "receiptId", required = true) Integer receiptId) {
		JSONObject jv = new JSONObject();
		Integer userId = getUserId(request);
		Receipt receipt = receiptService.getReceiptById(receiptId);
		if (receipt.getUserId().equals(userId)
				&& transStatus(receipt).getShowStatus().equals(
						ReceiptShowStatus.NEED_COMMENT.value())) {
			List<Comment> commentList = commentService.getByReceipt(receiptId);
			jv.put("code", Const.SUCCESS);
			jv.put("info", commentList);
		} else {
			jv.put("code", Const.RECEIPT_ERROR);
		}
		Utils.writeBack(request, response, jv);
	}

	@RequestMapping("/user/getReceiptInfo.do")
	public void getReceiptInfo(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "receiptId", required = true) Integer receiptId) {
		Integer userId = getUserId(request);
		JSONObject jv = new JSONObject();
		Receipt receipt = receiptService.getReceiptById(receiptId);
		if (receipt != null && receipt.getUserId().equals(userId)) {
			if (receipt.getVoucherId() != null) {
				jv.put("voucher",
						voucherService.getVoucherById(receipt.getVoucherId()));
			}
			receipt = transStatus(receipt);
			jv.put("receipt", receipt);
		} else {
			jv.put("code", Const.RECEIPT_ERROR);
		}
		Utils.writeBack(request, response, jv);
	}

	@RequestMapping("/user/cancelReceipt.do")
	public void cancelReceipt(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "receiptId", required = true) Integer receiptId) {
		Integer userId = getUserId(request);
		JSONObject jv = new JSONObject();
		Receipt receipt = receiptService.getReceiptById(receiptId);
		if (receipt.getUserId().equals(userId)) {
			logger.info("========" + receipt.getPayJson() + "======"
					+ receipt.getStatus());

			if (receipt.getStatus().equals(ReceiptStatus.NOTPAY.value())) {
				// 订单设置为失效
				receipt.setStatus(ReceiptStatus.USELESS.value());
				receiptService.update(receipt);
				List<PayMsg> payMsgList = format(receipt.getPayJson());
				// 商品库存还原
				for (PayMsg payMsg : payMsgList) {
					goodsColorStyleService.addCount(
							payMsg.getGoodsColorStyleId(),
							payMsg.getGoodsAmount());
				}
			}
			jv.put("code", Const.SUCCESS);
		} else {
			jv.put("code", Const.RECEIPT_ERROR);
		}
		Utils.writeBack(request, response, jv);
	}
	
	@RequestMapping("/admin/getReceipt.do")
	public void getAllReceipt(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="payStatus",required=false)Integer payStatus){
		JSONObject jv = new JSONObject();
		List<Receipt> resReceipt = new ArrayList<Receipt>();
		List<Receipt> receiptList = receiptService.getAll(payStatus);
		for (Receipt receipt : receiptList) {
			receipt = transStatus(receipt);
			resReceipt.add(receipt);
		}
		jv.put("allReceipt", resReceipt);
		Utils.writeBack(request, response, jv);
	}

	@RequestMapping("/admin/addExpressNo.do")
	public void addExpressNo(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="id",required=true)Integer id,
			@RequestParam(value="expressNo",required=true)String expressNo){
		JSONObject jv = new JSONObject();
		Receipt r =receiptService.getReceiptById(id);
		if(r!=null&&transStatus(r).getShowStatus().equals(ReceiptShowStatus.PAID.value())){
			r.setExpressNo(expressNo);
			receiptService.update(r);
			jv.put("code", Const.SUCCESS);
		}else{
			jv.put("code", Const.RECEIPT_ERROR);
		}
		Utils.writeBack(request, response, jv);
	}
	
	@RequestMapping("/admin/getReceiptInfo.do")
	public void adminGetReceiptInfo(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "receiptId", required = true) Integer receiptId) {
		JSONObject jv = new JSONObject();
		Receipt receipt = receiptService.getReceiptById(receiptId);
		if (receipt != null) {
			if (receipt.getVoucherId() != null) {
				jv.put("voucher",
						voucherService.getVoucherById(receipt.getVoucherId()));
			}
			receipt = transStatus(receipt);
			jv.put("receipt", receipt);
		} else {
			jv.put("code", Const.RECEIPT_ERROR);
		}
		Utils.writeBack(request, response, jv);
	}
	private Receipt transStatus(Receipt receipt) {
		if (receipt.getStatus().equals(ReceiptStatus.NOTPAY.value())) {
			receipt.setShowStatus(ReceiptShowStatus.NOTPAY.value());
		} else {
			if (receipt.getStatus().equals(ReceiptStatus.PAID.value())) {
				if (receipt.getIsReceipt() != null
						&& receipt.getIsReceipt() == Receipt.ISRECEIPT) {
					if (receipt.getIsComment() != null
							&& receipt.getIsComment() == Receipt.ISCOMMENTED) {
						receipt.setShowStatus(ReceiptShowStatus.FINISH.value());
					} else {
						receipt.setShowStatus(ReceiptShowStatus.NEED_COMMENT
								.value());
					}
				} else {
					receipt.setShowStatus(ReceiptShowStatus.PAID.value());
				}
			} else {
				receipt.setShowStatus(ReceiptShowStatus.USELESS.value());
			}
		}
		logger.info("receipt id=" + receipt.getId() + " status="
				+ receipt.getShowStatus());
		return receipt;
	}

}
