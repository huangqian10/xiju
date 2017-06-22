package com.xiyoukeji.xiju.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.xiyoukeji.xiju.core.enums.Const;
import com.xiyoukeji.xiju.core.utils.HttpUtils;
import com.xiyoukeji.xiju.core.utils.Utils;
import com.xiyoukeji.xiju.model.Scene;

@Controller
public class SceneController extends BaseController{
	
	Logger logger =Logger.getLogger(SceneController.class);
	@RequestMapping("/front/getSceneList.do")
	public void getSceneList(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject jv = new JSONObject();
		List<Scene> sceneList=sceneService.getAll();
		jv.put("info", sceneList);
		Utils.writeBack(request, response, jv);
	}
	
	@RequestMapping("/front/getip.do")
	public void getip(HttpServletRequest request,HttpServletResponse response){
		String ip=HttpUtils.getRemoteAddr(request);
		logger.info(ip);
	}
	@RequestMapping("/admin/saveOrUpdateScene.do")
	public void saveOrUpdateScene(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="scene",required=true)String scene,
			@RequestParam(value="id",required=false)Integer id){
		JSONObject jv = new JSONObject();
		if(sceneService.isExistName(scene)){
			jv.put("code", Const.EXIST_SCENE);
		}else{
			if(id!=null){//update
				Scene sc =sceneService.getById(id);
				if(sc!=null){
					sc.setScene(scene);
					sceneService.update(sc);
					jv.put("code", Const.SUCCESS);
				}else{
					jv.put("code", Const.NO_SUCH_SCENE);
				}
			}else{//save
				Scene sc = new Scene();
				sc.setScene(scene);
				sceneService.save(sc);
				jv.put("code", Const.SUCCESS);
				jv.put("sceneId", sc.getId());
			}
		}
		Utils.writeBack(request, response, jv);
	}
}
