package com.xiyoukeji.xiju.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.xiyoukeji.xiju.core.enums.Const;
import com.xiyoukeji.xiju.core.utils.Utils;

@Controller
public class FileController extends BaseController {
	@RequestMapping("/user/uploadCommentPic.do")
	public void uploadCommentPic(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "baseStr", required = true) String baseStr) {
		JSONObject jv = new JSONObject();
		try {
			String url = fileService.GenerateImage(baseStr, "comment");
			jv.put("code", Const.SUCCESS);
			jv.put("url", url);
		} catch (Exception e) {
			jv.put("code", Const.DATA_ERROR);
		}
		Utils.writeBack(request, response, jv);
	}
	
	@RequestMapping("/admin/uploadGoodsPicBase64.do")
	public void uploadGoodsPicBase64(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "baseStr", required = true) String baseStr) {
		JSONObject jv = new JSONObject();
		try {
			String url = fileService.GenerateImage(baseStr, "goods");
			logger.info("base64 transUrl=" + url);
			jv.put("code", Const.SUCCESS);
			jv.put("url", url);
		} catch (Exception e) {
			jv.put("code", Const.DATA_ERROR);
		}
		Utils.writeBack(request, response, jv);
	}
	
	@RequestMapping("/admin/uploadGoodsPic.do")
	public void uploadGoodsPic(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="file")MultipartFile file ){
		JSONObject jv = new JSONObject();
		try {
			String url=fileService.uploadFile(file, "goods");
			jv.put("url", url);
			jv.put("code", Const.SUCCESS);
		} catch (IOException e) {
			jv.put("code", Const.DATA_ERROR);
		}
		Utils.writeBack(request, response, jv);
		
	}

}
