package com.xiyoukeji.xiju.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.xiyoukeji.xiju.core.enums.Const;
import com.xiyoukeji.xiju.core.utils.Utils;
import com.xiyoukeji.xiju.model.Message;
import com.xiyoukeji.xiju.model.User;
import com.xiyoukeji.xiju.model.UserMsg;

@Controller
public class MessageController extends BaseController{
	
	@RequestMapping("/user/getMyMsg.do")
	public void getMyMsg(HttpServletRequest request,HttpServletResponse response){
		Integer userId= getUserId(request);
		JSONObject jv = new JSONObject();
		jv.put("info", userMsgService.getUserMsgByUserId(userId));
		Utils.writeBack(request, response, jv);
	}
	
	@RequestMapping("/user/readMsg.do")
	public void readMsg(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="id",required=true)Integer msgId){
		JSONObject jv = new JSONObject();
		UserMsg userMsg =userMsgService.getById(msgId);
		Integer userId =getUserId(request);
		if(userMsg!=null&&userMsg.getUserId().equals(userId)){
			userMsg.setStatus(UserMsg.READ);
			userMsgService.update(userMsg);
		}
		jv.put("code", Const.SUCCESS);
		Utils.writeBack(request, response, jv);
	}
	
	@RequestMapping("/admin/getMsgAll.do")
	public void getAll(HttpServletRequest request,HttpServletResponse response){
		JSONObject jv = new JSONObject();
		jv.put("info",messageService.getAll());
		Utils.writeBack(request, response, jv);
	}
	
	@RequestMapping("/admin/saveMsg.do")
	public void saveMsg(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="content",required=true)String content,
			@RequestParam(value="title",required=true)String title,
			@RequestParam(value="type",required=true)Integer type,
			@RequestParam(value="turnId",required=false)Integer turnId,
			@RequestParam(value="goodsName",required=false)String goodsName){
		JSONObject jv = new JSONObject();
		Message message = new Message();
		if(type==Message.TYPE_GOODS){
			if(turnId==null&&StringUtils.isEmpty(goodsName)){
				jv.put("code", Const.DATA_ERROR);
			}else{
				message.setContent(content);
				message.setGoodsName(goodsName);
				message.setTitle(title);
				message.setTurnId(turnId);
				message.setType(type);
				jv.put("code",Const.SUCCESS);
				messageService.save(message);
			}
		}else if(type==Message.TYPE_CHUANYI){
			if(turnId==null){
				jv.put("code", Const.DATA_ERROR);
			}else{
				message.setContent(content);
				message.setTitle(title);
				message.setTurnId(turnId);
				message.setType(type);
				jv.put("code",Const.SUCCESS);
				messageService.save(message);

			}
		}else if(type==Message.TYPE_WORD){
			message.setContent(content);
			message.setTitle(title);
			message.setType(type);
			jv.put("code",Const.SUCCESS);
			messageService.save(message);
		}
		Utils.writeBack(request, response, jv);
	}
	
	@RequestMapping("/admin/addUserMsg.do")
	public void addUserMsg(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="msgId",required=true)Integer msgId){
		JSONObject jv = new JSONObject();
		Message message = messageService.getById(msgId);
		if(message!=null){
			List<User> userList =userService.getAll();
			for(User u:userList){
				userMsgService.save(message, u.getId());
			}
			jv.put("code", Const.SUCCESS);
		}else{
			jv.put("code", Const.DATA_ERROR);
		}
		Utils.writeBack(request, response, jv);
	}
}
