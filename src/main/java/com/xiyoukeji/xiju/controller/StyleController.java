package com.xiyoukeji.xiju.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.xiyoukeji.xiju.core.enums.Const;
import com.xiyoukeji.xiju.core.utils.Utils;
import com.xiyoukeji.xiju.model.Style;

//风格
@Controller
public class StyleController extends BaseController {

	@RequestMapping("/front/getStyleList.do")
	public void getStyleList(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject jv = new JSONObject();
		List<Style> styleList = styleService.getAll();
		jv.put("info", styleList);
		Utils.writeBack(request, response, jv);
	}

	@RequestMapping("/admin/saveOrUpdateStyle.do")
	public void saveOrUpdateStyle(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "id", required = false) Integer id) {
		JSONObject jv = new JSONObject();
		if(styleService.isExistName(name,id)){
			jv.put("code", Const.EXIST_STYLE);
		}else{
			if(id!=null){//update
				Style style=styleService.getById(id);
				if(style!=null){
					style.setName(name);
					styleService.update(style);
					jv.put("code", Const.SUCCESS);
				}else{
					jv.put("code", Const.NO_SUCH_STYLE);
				}
			}else{//save
				Style style =new Style();
				style.setName(name);
				styleService.save(style);
				jv.put("code", Const.SUCCESS);
				jv.put("id", style.getId());
			}
		}
		Utils.writeBack(request, response, jv);
	}
}
