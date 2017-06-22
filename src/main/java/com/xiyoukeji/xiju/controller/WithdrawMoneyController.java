/**
 * 
 */
package com.xiyoukeji.xiju.controller;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
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

	
	@ResourceMapping("doWithdrawMoney")
	public void  withdrawMoney(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value="userId",required=true)Integer userId,
			@RequestParam(value="receiptIdList",required=true)String receiptIdList,
			@RequestParam(value="money",required=true)BigDecimal money ){
		JSONObject jv = new JSONObject();
		
		
		jv.put("code", withdrawMoneyService.doWithdrawMoney(userId, receiptIdList));
		Utils.writeBack(request, response, jv);
		
		
	}
	
	
}
