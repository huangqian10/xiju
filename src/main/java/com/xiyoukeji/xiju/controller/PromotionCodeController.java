/**
 * 
 */
package com.xiyoukeji.xiju.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.xiyoukeji.xiju.core.enums.Const;
import com.xiyoukeji.xiju.core.utils.Utils;

/**
 * @author hq
 *
 */
@Controller
public class PromotionCodeController extends BaseController {
	
	@RequestMapping("/promotionCode/addPromotionCode.do")
	public void addPromotionCode(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="userId",required=true)Integer userId,
			@RequestParam(value="name",required=true)String name,
			@RequestParam(value="cellphone",required=true)String cellphone,
			@RequestParam(value="address",required=true)String address,
			@RequestParam(value="community",required=true)String community){
		

		Utils.writeBack(request, response,  promotionCodeService.addPromotionCode(userId, name, cellphone, address, community));
		
	}
	
	@RequestMapping("/promotionCode/usecPromotionCode.do")
	public void usecPromotionCode(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="promotionCode",required=true)String  promotionCode){
		JSONObject jv= new JSONObject();
		
		jv.put("code", promotionCodeService.usecPromotionCode(promotionCode));
		Utils.writeBack(request, response, jv);
	}
	
	
	@RequestMapping("/promotionCode/getPromotionCodeRecord.do")
	public void getPromotionCodeRecord(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="userId",required=true)Integer userId){
		JSONObject jv= new JSONObject();
		jv.put("code",Const.SUCCESS );
		jv.put("info", promotionCodeService.getPromotionCodeRecord(userId));
		Utils.writeBack(request, response, jv);
		
	}
	
	
}
