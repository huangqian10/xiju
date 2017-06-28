/**
 * 
 */
package com.xiyoukeji.xiju.controller;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import com.alibaba.fastjson.JSONObject;
import com.xiyoukeji.xiju.core.utils.Utils;

/**
 * @author hq
 *
 */
@Controller
public class WithdrawMoneyController extends BaseController{

	
	@RequestMapping("/withdrawMoney/doWithdrawMoney.do")
	public void  withdrawMoney(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value="userId",required=true)Integer userId,
			@RequestParam(value="receiptIdList",required=true)String receiptIdList,
			@RequestParam(value="bankCardId",required=true)Integer bankCardId, 
			@RequestParam(value="totalMoney",required=true)Integer totalMoney){
		JSONObject jv = new JSONObject();
		jv.put("code", withdrawMoneyService.doWithdrawMoney(userId, receiptIdList,bankCardId,totalMoney));
		Utils.writeBack(request, response, jv);
	}
	
	@RequestMapping("/withdrawMoney/getWithdrawMoneyById.do")
	public void  getWithdrawMoneyById(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="id",required=true)Integer id){
		JSONObject jv = new JSONObject();
		jv.put("info",withdrawMoneyService.getWithdrawMoneyById(id) );
		Utils.writeBack(request, response, jv);
		
	}
	
	
	@RequestMapping("/withdrawMoney/getWithdrawMoneyByUserId.do")
	public void  getWithdrawMoneyByUserId(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="userId",required=true)Integer userId){
		JSONObject jv = new JSONObject();
		jv.put("info",withdrawMoneyService.getWithdrawMoneyByUserId(userId) );
		Utils.writeBack(request, response, jv);
		
	}
	
	@RequestMapping("/withdrawMoney/changeWithdrawMoneyStatusById.do")
	public void  getWithdrawMoneyByUserId(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="id",required=true)Integer id,
			@RequestParam(value="status",required=true)Integer status){
		JSONObject jv = new JSONObject();
		jv.put("info",withdrawMoneyService.changeWithdrawMoneyStatusById(id,status));
		Utils.writeBack(request, response, jv);
	}
	

}
