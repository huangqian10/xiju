/**
 * 
 */
package com.xiyoukeji.xiju.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
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
public class StylistController extends BaseController{
	
	@RequestMapping("/stylist/authentication.do")
	public void authentication(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value="userId",required=true)Integer userId,
			@RequestParam(value="stylistCard",required=true)String stylistCard,
			@RequestParam(value="stylistCertificate",required=true)String stylistCertificate,
			@RequestParam(value="businessLicense",required=true)String businessLicense){
		JSONObject jv = new JSONObject();
		String stylistCardUrl=fileService.GenerateImage(stylistCard,"stylistCardUrl");
		String stylistCertificateUrl=fileService.GenerateImage(stylistCertificate,"stylistCertificateUrl");
		String businessLicenseUrl=fileService.GenerateImage(businessLicense,"businessLicenseUrl");

		stylistService.authentication(userId, stylistCardUrl, stylistCertificateUrl, businessLicenseUrl);
		jv.put("code", Const.SUCCESS);
		Utils.writeBack(request, response, jv);
	}
	
	
	@RequestMapping("/stylist/getStylistByUserId.do")
	public void getStylistByUserId(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value="userId",required=true)Integer userId){
		
		JSONObject jv = new JSONObject();
		jv.put("info",stylistService.getStylistByUserId(userId));
		Utils.writeBack(request, response, jv);
	}
	
	@RequestMapping("/stylist/changeStylistStatus.do")
	public void changeStylistStatus(HttpServletRequest request,HttpServletResponse response,
	@RequestParam(value="userId",required=true)Integer userId,
	@RequestParam(value="status",required=true)Integer status){
		JSONObject jv = new JSONObject();
		stylistService.changeStylistStatus(userId, status);
		jv.put("code", Const.SUCCESS);
		Utils.writeBack(request, response, jv);
	}

}
