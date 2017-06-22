package com.xiyoukeji.xiju.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.sun.xml.internal.ws.handler.ClientSOAPHandlerTube;
import com.xiyoukeji.xiju.core.enums.Const;
import com.xiyoukeji.xiju.core.utils.Utils;
import com.xiyoukeji.xiju.model.ClientInfo;

@Controller
public class ClientInfoController extends BaseController {
	@RequestMapping("/admin/updateClientInfo.do")
	public void updateClientInfo(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "kfMobile", required = false) String kfMobile,
			@RequestParam(value = "userAgreement", required = false) String userAgreement,
			@RequestParam(value = "weibo", required = false) String weibo,
			@RequestParam(value = "xijuIntro", required = false) String xijuIntro,
			@RequestParam(value = "wxPubPic", required = false) String wxPubPic,
			@RequestParam(value = "wxPubUrl", required = false) String wxPubUrl,
			@RequestParam(value = "spreadUrl", required = false) String spreadUrl) {
		ClientInfo clientInfo = clientInfoService.getInfo();
		clientInfo.setKfMobile(kfMobile);
		clientInfo.setSpreadUrl(spreadUrl);
		clientInfo.setUserAgreement(userAgreement);
		clientInfo.setWeibo(weibo);
		clientInfo.setWxPubPic(wxPubPic);
		clientInfo.setWxPubUrl(wxPubUrl);
		clientInfo.setXijuIntro(xijuIntro);
		clientInfoService.update(clientInfo);
		JSONObject jv = new JSONObject();
		jv.put("code", Const.SUCCESS);
		Utils.writeBack(request, response, jv);
	}
	
	@RequestMapping(value={"/front/getClientInfo.do","/admin/getClientInfo.do"})
	public void getClientInfo(HttpServletRequest request,HttpServletResponse response){
		JSONObject jv = new JSONObject();
		jv.put("info", clientInfoService.getInfo());
		Utils.writeBack(request, response, jv);
	}
}
