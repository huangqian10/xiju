package com.xiyoukeji.xiju.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.xiyoukeji.xiju.core.enums.Const;
import com.xiyoukeji.xiju.core.utils.Utils;
import com.xiyoukeji.xiju.model.Content;


@Controller
public class ContentController extends BaseController{
	
	@RequestMapping("/admin/saveOrUpdateContent.do")
	public void saveOrUpdateContent(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="picUrl",required=true)String picUrl,
			@RequestParam(value="intro",required=true)String intro,
			@RequestParam(value="id",required=false)Integer id){
		JSONObject jv = new JSONObject();
		if(id!=null){//update
			Content content =contentService.getById(id);
			if(content!=null){
				content.setPicUrl(picUrl);
				content.setIntro(intro);
				contentService.update(content);
				jv.put("code", Const.SUCCESS);
			}else{
				jv.put("code", Const.NO_SUCH_CONTENT);
			}
		}else{//save
			Content content = new Content();
			content.setIntro(intro);
			content.setPicUrl(picUrl);
			contentService.save(content);
			jv.put("code", Const.SUCCESS);
			jv.put("id", content.getId());
		}
		Utils.writeBack(request, response, jv);
	}
}
