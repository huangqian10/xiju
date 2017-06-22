package com.xiyoukeji.xiju.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.xiyoukeji.xiju.core.enums.Const;
import com.xiyoukeji.xiju.core.utils.Utils;
import com.xiyoukeji.xiju.model.Feedback;


@Controller
public class FeedBackController extends BaseController{

	@RequestMapping("/user/addFeedback.do")
	public void addFeedBack(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="content",required=true)String content){
		JSONObject jv= new JSONObject();
		Integer userId=getUserId(request);
		Feedback feedback = new Feedback();
		feedback.setContent(content);
		feedback.setCreateTime((int)(System.currentTimeMillis()/1000));
		feedback.setStatus(Feedback.NOTREAD);
		feedback.setUserId(userId);
		feedBackService.save(feedback);
		jv.put("code", Const.SUCCESS);
		Utils.writeBack(request, response, jv);
	}
}
