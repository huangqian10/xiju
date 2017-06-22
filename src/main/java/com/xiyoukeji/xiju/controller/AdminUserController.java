package com.xiyoukeji.xiju.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.xiyoukeji.xiju.model.PowerInfo;


import com.alibaba.fastjson.JSONObject;
import com.xiyoukeji.xiju.core.enums.Const;
import com.xiyoukeji.xiju.core.utils.MD5Util;
import com.xiyoukeji.xiju.core.utils.Utils;
import com.xiyoukeji.xiju.model.AdminUser;
import com.xiyoukeji.xiju.model.UrlInfo;

@Controller
public class AdminUserController extends BaseController {
	
	private final static String DEFAULT_PASS="xiju2016";
	
	private final Integer SUPERADMIN=1;
	
	@RequestMapping("/front/adminLogin.do")
	public void adminLogin(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "password", required = true) String password) {
		JSONObject jv = new JSONObject();
		AdminUser user = adminUserService.judgeLogin(userName, MD5Util.MD5(password));
		if (user != null) {
			request.getSession().setAttribute(Const.LOGIN_ADMIN, user);
			jv.put("code", Const.SUCCESS);
		} else {
			jv.put("code", Const.LOGIN_ERROR);
		}
		Utils.writeBack(request, response, jv);
	}

	@RequestMapping("/admin/adminLogout.do")
	public void adminLogOut(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject jv = new JSONObject();
		request.getSession().removeAttribute(Const.LOGIN_ADMIN);
		jv.put("code", Const.SUCCESS);
		Utils.writeBack(request, response, jv);
	}

	@RequestMapping("/admin/getAdminUserList.do")
	public void getAdminUserList(HttpServletRequest request,HttpServletResponse response){
		JSONObject jv = new JSONObject();
		jv.put("info", adminUserService.getAll());
		Utils.writeBack(request, response, jv);
	}

	@RequestMapping("/admin/changePassword.do")
	public void changePassword(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value="oldPass",required=true)String oldPass,
			@RequestParam(value="newPass",required=true)String newPass) {
		JSONObject jv = new JSONObject();
		AdminUser sessionUser=(AdminUser) request.getSession().getAttribute(Const.LOGIN_ADMIN);
		AdminUser user = adminUserService.judgeLogin(sessionUser.getUserName(), oldPass);
		if(user!=null){
			user.setPassword(MD5Util.MD5(newPass));
			adminUserService.update(user);
		}else{
			jv.put("code", Const.LOGIN_ERROR);
		}
		Utils.writeBack(request, response, jv);
	}
	
	@RequestMapping("/admin/addAdminUser.do")
	public void addAdminUser(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="username",required=true)String username,
			@RequestParam(value="status",required=true)Integer status,
			@RequestParam(value="powerId",required=true)Integer powerId){
		JSONObject jv = new JSONObject();
		AdminUser adminUser = new AdminUser();
		adminUser.setPassword(MD5Util.MD5(DEFAULT_PASS));
		adminUser.setPowerId(powerId);
		adminUser.setStatus(status);
		adminUser.setUserName(username);
		adminUserService.save(adminUser);
		jv.put("code", Const.SUCCESS);
		Utils.writeBack(request, response, jv);
	}
	
	
	@RequestMapping("/admin/updateAdminUser.do")
	public void updateAdminUser(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="status",required=false)Integer status,
			@RequestParam(value="powerId",required=false)Integer powerId,
			@RequestParam(value="adminId",required=true)Integer adminId){
		JSONObject jv = new JSONObject();
		AdminUser adminUser = adminUserService.getAdminUserById(adminId);
		if(adminUser!=null){
			if(adminUser.getPowerId()==SUPERADMIN){
				jv.put("code", Const.POWER_LIMIT);
			}else{
				if(status!=null){
					adminUser.setStatus(status);
				}
				if(powerId!=null){
					adminUser.setPowerId(powerId);
				}
				adminUserService.update(adminUser);
				jv.put("code", Const.SUCCESS);
			}

		}else{
			jv.put("code", Const.NO_SUCH_USER);
		}
		Utils.writeBack(request, response, jv);
	}
	@RequestMapping("/admin/resetAdminUser.do")
	public void resetAdminUser(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="adminId",required=true)Integer adminId){
		JSONObject jv = new JSONObject();
		AdminUser adminUser =adminUserService.getAdminUserById(adminId);
		if(adminUser!=null){
			if(adminUser.getPowerId()==SUPERADMIN){
				jv.put("code", Const.POWER_LIMIT);
			}else{
				adminUser.setPassword(MD5Util.MD5(DEFAULT_PASS));
				adminUserService.update(adminUser);
				jv.put("code", Const.SUCCESS);
			}

		}else{
			jv.put("code", Const.NO_SUCH_USER);
		}
		Utils.writeBack(request, response, jv);
	}
	
	@RequestMapping("/admin/saveOrUpdateUrl.do")
	public void saveOrUpdateUrl(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="urlName",required=true)String urlName,
			@RequestParam(value="url",required=true)String url,
			@RequestParam(value="id",required=false)Integer id){
		JSONObject jv = new JSONObject();
		if(id!=null){
			UrlInfo urlInfo =urlInfoService.getById(id);
			if(urlInfo!=null){
				urlInfo.setUrl(url);
				urlInfo.setUrlName(urlName);
				urlInfoService.update(urlInfo);
			}
		}else{
			UrlInfo urlInfo = new UrlInfo();
			urlInfo.setUrl(url);
			urlInfo.setUrlName(urlName);
			urlInfoService.save(urlInfo);
		}
		jv.put("code", Const.SUCCESS);
		Utils.writeBack(request, response, jv);
	}
	
	@RequestMapping("/admin/getUrlList.do")
	public void getUrlList(HttpServletRequest request,HttpServletResponse response){
		JSONObject jv = new JSONObject();
		jv.put("info", urlInfoService.getAll());
		Utils.writeBack(request, response, jv);
	}
	@RequestMapping("/admin/getPowerList.do")
	public void getPowerList(HttpServletResponse response,HttpServletRequest request){
		JSONObject jv = new JSONObject();
		jv.put("info", powerInfoService.getAll());
		Utils.writeBack(request, response, jv);
	}
	
	@RequestMapping("/admin/saveOrUpdatePower.do")
	public void saveOrUpdatePower(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="id",required=false)Integer id,
			@RequestParam(value="powerName",required=true)String powerName,
			@RequestParam(value="urlInfoIds",required=true)String urlInfoIds){
		JSONObject jv = new JSONObject();
		if(id!=null){
			PowerInfo powerInfo=powerInfoService.getById(id);
			if(powerInfo!=null){
				powerInfo.setPowerName(powerName);
				powerInfo.setUrlInfoIds(urlInfoIds);
				powerInfoService.update(powerInfo);
			}
		}else{
			PowerInfo powerInfo = new PowerInfo();
			powerInfo.setPowerName(powerName);
			powerInfo.setUrlInfoIds(urlInfoIds);
			powerInfoService.save(powerInfo);
			
		}
		jv.put("code", Const.SUCCESS);
	}
}
