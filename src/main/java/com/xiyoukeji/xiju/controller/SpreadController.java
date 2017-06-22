package com.xiyoukeji.xiju.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.xiyoukeji.xiju.core.utils.Utils;
import com.xiyoukeji.xiju.model.ClientInfo;
import com.xiyoukeji.xiju.model.Spread;

@Controller
public class SpreadController extends BaseController{
	@RequestMapping("/front/getSpread.do")
	public void getSpread(HttpServletRequest request,HttpServletResponse response){
		JSONObject jv = new JSONObject();
		ClientInfo clientInfo= clientInfoService.getInfo();
		jv.put("code", clientInfo.getSpreadUrl());
		Utils.writeBack(request, response, jv);
	}
	
	@RequestMapping("/admin/updateSpread.do")
	public void updateSpread(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="id",required=true)Integer id,
			@RequestParam(value="spreadUrl",required=true)String spreadUrl){
		Spread s = new Spread();
		s.setId(id);
		s.setSpreadUrl(spreadUrl);
		spreadService.save(s);
	}
}
