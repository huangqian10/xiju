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
import com.xiyoukeji.xiju.core.utils.Utils;

/**
 * @author hq
 *
 */
@Controller
public class WithdrawalProportionController extends BaseController{
		
	@RequestMapping("/withdrawalProportion/setProportion.do")
	public void setProportion(	HttpServletRequest request,
			HttpServletResponse response,	@RequestParam(value = "proportion", required = true) Integer proportion){
		JSONObject jv = new JSONObject();
		jv.put("code", withdrawalProportionService.setProportion(proportion));
		Utils.writeBack(request, response, jv);
		
	}
	
	@RequestMapping("/withdrawalProportion/getProportion.do")
	public void setProportion(	HttpServletRequest request,
			HttpServletResponse response){
		JSONObject jv = new JSONObject();
		jv.put("info", withdrawalProportionService.getProportion());
		Utils.writeBack(request, response, jv);
	}
		
}
