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
public class BankCardController extends BaseController {

	
	@RequestMapping("/bankCard/add.do")
	public void add(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="userId",required=true)Integer userId,
			@RequestParam(value = "owner", required = true) String owner,
			@RequestParam(value = "openNumber", required = true) String openNumber,
			@RequestParam(value = "openBank", required = true) String openBank,
			@RequestParam(value = "cellphone", required = true) String cellphone){
			JSONObject jv = new JSONObject();
			bankCardService.add(userId, owner, openNumber, openBank, cellphone);
			jv.put("code", Const.SUCCESS);
			Utils.writeBack(request, response, jv);
	}
	
	@RequestMapping("/bankCard/change.do")
	public void change(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="id",required=true)Integer id,
			@RequestParam(value = "owner", required = true) String owner,
			@RequestParam(value = "openNumber", required = true) String openNumber,
			@RequestParam(value = "openBank", required = true) String openBank,
			@RequestParam(value = "cellphone", required = true) String cellphone){
		JSONObject jv = new JSONObject();
		bankCardService.change(id, owner, openNumber, openBank, cellphone);
		jv.put("code", Const.SUCCESS);
		Utils.writeBack(request, response, jv);

	}
	
	
	@RequestMapping("/bankCard/setDefault.do")
	public void setDefault(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="id",required=true)Integer id,
			@RequestParam(value = "isDefault", required = true) Integer isDefault){
		JSONObject jv = new JSONObject();
		bankCardService.setDefault(id, isDefault);
		jv.put("code", Const.SUCCESS);
		Utils.writeBack(request, response, jv);

	}
	
	
	@RequestMapping("/bankCard/getById.do")
	public void getById(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="id",required=true)Integer id){
		JSONObject jv = new JSONObject();
		
		jv.put("info",bankCardService.getById(id));
		Utils.writeBack(request, response, jv);
	}
	
	
	@RequestMapping("/bankCard/getListByUserId.do")
	public void getListByUserId(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="userId",required=true)Integer userId){
		JSONObject jv = new JSONObject();
		
		jv.put("info",bankCardService.getListByUserId(userId));
		Utils.writeBack(request, response, jv);
	}
	
	@RequestMapping("/bankCard/deleteById.do")
	public void deleteById(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="id",required=true)Integer id){
		JSONObject jv = new JSONObject();
		bankCardService.deleteById(id);
		jv.put("code", Const.SUCCESS);
		Utils.writeBack(request, response, jv);
	}
	
	
}
