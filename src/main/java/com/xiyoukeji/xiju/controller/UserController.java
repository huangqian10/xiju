package com.xiyoukeji.xiju.controller;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.xiyoukeji.xiju.core.enums.Const;
import com.xiyoukeji.xiju.core.utils.MD5Util;
import com.xiyoukeji.xiju.core.utils.Utils;
import com.xiyoukeji.xiju.domain.Regcode;
import com.xiyoukeji.xiju.model.User;

@Controller
public class UserController extends BaseController {

	Logger logger = Logger.getLogger(UserController.class);

	public static final String FOTGETSIGN = "forgetSign";

	private static final int TIME_SEND_LIMIT = 60;

	private static final int DEFAULT_OVERDUE_TIME = 600;

	@RequestMapping("/front/reg.do")
	public void reg(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "mobile", required = true) String mobile,
			@RequestParam(value = "registCode", required = true) String registCode) {
		String realKey = getRealKey(mobile);
		Integer nowTime = (int) (System.currentTimeMillis() / 1000);
		Regcode regcode = (Regcode) redisCache.getObject(realKey);

		JSONObject jv = new JSONObject();
		if (regcode != null
				&& (nowTime - regcode.getSendTime() <= DEFAULT_OVERDUE_TIME)
				&& (registCode.equals(regcode.getRegistCode()))) {
			if (userService.isExist(mobile)) {
				jv.put("code", Const.HAS_REG);
			} else {
				String uid=(String) request.getSession().getAttribute(Const.LOGIN_UID);
				if(StringUtils.isEmpty(uid)){//是普通注册
					User user = new User();
					user.setHeadUrl("/xijufile/headUrl/deafault.jpg");
					user.setCreateTime(nowTime);
					user.setMobile(mobile);
					user.setPassword(MD5Util.MD5(password));
					user.setUsername(mobile);
					userService.save(user);
					logger.info("regSuccess" + password + "||" + mobile + "||"
							+ registCode + "||" + user.getId());
					request.getSession().setAttribute(Const.LOGIN_USER_ID,
							user.getId());
				}else{//是绑定手机号
					User user=userService.getByUid(uid);
					user.setMobile(mobile);
					user.setPassword(password);
					userService.update(user);
					logger.info("regSuccess" + password + "||" + mobile + "||"
							+ registCode + "||" + user.getId());
					request.getSession().setAttribute(Const.LOGIN_USER_ID,
							user.getId());
				}
				redisCache.delObject(realKey);
				jv.put("code", Const.SUCCESS);

			}
		} else {
			jv.put("code", Const.REG_CODE_ERROR);
		}
		Utils.writeBack(request, response, jv);
	}

	@RequestMapping("/user/updatUserName.do")
	public void updateUsername(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "username", required = true) String username) {
		Integer userId = getUserId(request);
		JSONObject jv = new JSONObject();
		User user = userService.getUserById(userId);
		user.setUsername(username);
		userService.update(user);
		jv.put("code", Const.SUCCESS);
		Utils.writeBack(request, response, jv);
	}

	@RequestMapping("/user/updatePassword.do")
	public void updatePassword(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "oldPassword", required = true) String oldPassword,
			@RequestParam(value = "newPassword", required = true) String newPassword) {
		Integer userId = getUserId(request);
		JSONObject jv = new JSONObject();
		User user = userService.getUserById(userId);
		if (user.getPassword().equals(MD5Util.MD5(oldPassword))) {
			jv.put("code", Const.SUCCESS);
			user.setPassword(MD5Util.MD5(newPassword));
			userService.update(user);
		} else {
			jv.put("code", Const.PASSWORD_ERROR);
			request.getSession()
					.setAttribute(Const.LOGIN_USER_ID, user.getId());
		}
		Utils.writeBack(request, response, jv);

	}

	@RequestMapping("/front/login.do")
	public void login(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "mobile", required = true) String mobile,
			@RequestParam(value = "password", required = true) String password) {
		JSONObject jv = new JSONObject();
		User user = userService.judgeLogin(mobile, MD5Util.MD5(password));
		if (user != null) {
			logger.info("jsessionId=" + request.getSession().getId());
			request.getSession()
					.setAttribute(Const.LOGIN_USER_ID, user.getId());
			jv.put("code", Const.SUCCESS);
		} else {
			jv.put("code", Const.LOGIN_ERROR);
		}
		Utils.writeBack(request, response, jv);
	}

	@RequestMapping("/user/logout.do")
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		JSONObject jv = new JSONObject();
		request.getSession().removeAttribute(Const.LOGIN_USER_ID);
		request.getSession().removeAttribute(Const.LOGIN_UID);
		jv.put("code", Const.SUCCESS);
		Utils.writeBack(request, response, jv);
	}

	@RequestMapping("/front/getCode.do")
	public void getCode(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "mobile", required = true) String mobile) {
		String realKey = getRealKey(mobile);
		JSONObject jv = new JSONObject();
		if (userService.isExist(mobile)) {
			jv.put("code", Const.HAS_REG);
		} else {
			int code = Const.SUCCESS;
			Regcode regcode = null;
			int nowTime = (int) (System.currentTimeMillis() / 1000);
			String registCode = String.format("%06d",
					new Random().nextInt(999999));
			regcode = (Regcode) redisCache.getObject(realKey);
			if (regcode == null) {
				logger.info("mobile=" + realKey.getBytes() + " and code="
						+ registCode);
				regcode = new Regcode(registCode, nowTime);
				logger.info(regcode.toString());
				redisCache.setObject(realKey, regcode, DEFAULT_OVERDUE_TIME);
			} else {
				if (nowTime - regcode.getSendTime() > TIME_SEND_LIMIT) {
					logger.info("mobile=" + realKey.getBytes() + " and code="
							+ registCode);
					regcode = new Regcode(registCode, nowTime);
					redisCache.del(realKey);
					redisCache
							.setObject(realKey, regcode, DEFAULT_OVERDUE_TIME);
				} else {
					code = Const.TIME_LIMIT;
				}
			}
			if (code == Const.SUCCESS) {
				try {
					smsService.sendSms(mobile,
							transCode(regcode.getRegistCode()));
				} catch (Exception e) {
					logger.info("send sms error");
					code = Const.SYSTEM_ERROR;
				}
			}
			jv.put("code", code);
		}
		Utils.writeBack(request, response, jv);

	}

	@RequestMapping("/front/getForgetCode.do")
	public void getForgetCode(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "mobile", required = true) String mobile) {
		String forgetKey = getForgetKey(mobile);
		JSONObject jv = new JSONObject();
		int code = Const.SUCCESS;
		Regcode regcode = null;
		int nowTime = (int) (System.currentTimeMillis() / 1000);
		String registCode = String.format("%06d", new Random().nextInt(999999));
		regcode = (Regcode) redisCache.getObject(forgetKey);
		if (regcode == null) {
			logger.info("mobile=" + forgetKey + " and code=" + registCode);
			regcode = new Regcode(registCode, nowTime);
			logger.info(regcode.toString());
			redisCache.setObject(forgetKey, regcode, DEFAULT_OVERDUE_TIME);
		} else {
			if (nowTime - regcode.getSendTime() > TIME_SEND_LIMIT) {
				logger.info("mobile=" + forgetKey + " and code=" + registCode);
				regcode = new Regcode(registCode, nowTime);
				redisCache.del(forgetKey);
				redisCache.setObject(forgetKey, regcode, DEFAULT_OVERDUE_TIME);
			} else {
				code = Const.TIME_LIMIT;
			}
		}
		if (code == Const.SUCCESS) {
			try {
				smsService.sendSms(mobile, transCode(regcode.getRegistCode()));
			} catch (Exception e) {
				logger.info("send sms error");
				code = Const.SYSTEM_ERROR;
			}
		}
		jv.put("code", code);
		Utils.writeBack(request, response, jv);
	}

	@RequestMapping("/front/forgetPass.do")
	public void forgetPass(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "password", required = true) String password) {
		JSONObject jv = new JSONObject();
		String mobile = (String) request.getSession().getAttribute(FOTGETSIGN);
		if (mobile != null) {
			User user = userService.getByMobile(mobile);
			if (user == null) {
				jv.put("code", Const.NO_SUCH_USER);
			} else {
				user.setPassword(MD5Util.MD5(password));
				userService.update(user);
				request.getSession().removeAttribute(FOTGETSIGN);
				request.getSession().setAttribute(Const.LOGIN_USER_ID,
						user.getId());
				jv.put("code", Const.SUCCESS);
			}
		} else {
			jv.put("code", Const.REG_CODE_ERROR);
		}
		Utils.writeBack(request, response, jv);
	}

	@RequestMapping("/front/validateForgetCode.do")
	public void validateForgetCode(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "mobile", required = true) String mobile,
			@RequestParam(value = "registCode", required = true) String registCode) {
		JSONObject jv = new JSONObject();
		String forgetKey = getForgetKey(mobile);
		Regcode regcode = (Regcode) redisCache.getObject(forgetKey);
		Integer nowTime = (int) (System.currentTimeMillis() / 1000);
		if (regcode != null
				&& (nowTime - regcode.getSendTime() <= DEFAULT_OVERDUE_TIME)
				&& (registCode.equals(regcode.getRegistCode()))) {
			jv.put("code", Const.SUCCESS);
			redisCache.delObject(forgetKey);
			request.getSession().setAttribute(FOTGETSIGN, mobile);
		} else {
			jv.put("code", Const.REG_CODE_ERROR);
		}
		Utils.writeBack(request, response, jv);

	}

	public static void main(String[] args) {
		System.out.println(MD5Util.MD5("123456"));
	}

	@RequestMapping("/user/getMyInfo.do")
	public void getMyInfo(HttpServletRequest request,
			HttpServletResponse response) {
		Integer userId = getUserId(request);
		User user = userService.getUserById(userId);
		JSONObject jv = new JSONObject();
		jv.put("username", user.getUsername());
		jv.put("headUrl", user.getHeadUrl());
		jv.put("mobile", user.getMobile());
		Utils.writeBack(request, response, jv);

	}

	@RequestMapping("/user/updateHeadUrl.do")
	public void updateHeadUrl(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "baseStr", required = true) String baseStr) {
		Integer userId = getUserId(request);
		JSONObject jv = new JSONObject();
		try {
			String url = fileService.GenerateImage(baseStr, "headUrl");
			logger.info("base64 transUrl=" + url);
			User user = userService.getUserById(userId);
			user.setHeadUrl(url);
			userService.update(user);
			jv.put("code", Const.SUCCESS);
			jv.put("url", url);
		} catch (Exception e) {
			jv.put("code", Const.DATA_ERROR);
		}
		Utils.writeBack(request, response, jv);
	}

	@RequestMapping("/front/loginByUid.do")
	public void loginByUid(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="uid",required=true)String uid){
		JSONObject jv = new JSONObject();
		User user = userService.getByUid(uid);
		if(user==null){
			jv.put("code", Const.LOGIN_ERROR);
		}else{
			request.getSession().setAttribute(Const.LOGIN_USER_ID, user.getId());
			request.getSession().setAttribute(Const.LOGIN_UID, uid);
			jv.put("code", Const.SUCCESS);
		}
		Utils.writeBack(request, response, jv);
	}
	
	@RequestMapping("/front/regByUid.do")
	public void regByUid(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="uid",required=true)String uid,
			@RequestParam(value="headUrl",required=true)String headUrl,
			@RequestParam(value="nickname",required=true)String nickname){
		JSONObject jv = new JSONObject();
		User user =userService.getByUid(uid);
		if(user==null){//第一次第三方登陆
			user = new User();
			user.setCreateTime((int)(System.currentTimeMillis()/1000));
			user.setHeadUrl(headUrl);
			user.setUsername(nickname);
			user.setUid(uid);
			userService.save(user);
		}
		//之前已注册过及注册成功，则设置登陆态
		request.getSession().setAttribute(Const.LOGIN_USER_ID, user.getId());
		request.getSession().setAttribute(Const.LOGIN_UID, uid);
		jv.put("code", Const.SUCCESS);
		Utils.writeBack(request, response, jv);
	}
	
	
	@RequestMapping("/admin/getUserList.do")
	public void getUserList(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject jv = new JSONObject();
		jv.put("info", userService.getAll());
		Utils.writeBack(request, response, jv);
	}

	public String getRealKey(String mobile) {
		return Const.REGSMS + mobile;
	}

	public String getForgetKey(String mobile) {
		return Const.FORGETSMS + mobile;
	}

	public String transCode(String code) {
		return "【嘻居家居】您的验证码是" + code;
	}
}
