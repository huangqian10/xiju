package com.xiyoukeji.xiju.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.xiyoukeji.xiju.core.enums.Const;
import com.xiyoukeji.xiju.core.utils.Utils;
import com.xiyoukeji.xiju.model.Color;

@Controller
public class ColorController extends BaseController {
	@RequestMapping("/front/getColorList.do")
	public void getColorList(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject jv = new JSONObject();
		jv.put("info", colorService.getALl());
		Utils.writeBack(request, response, jv);
	}

	@RequestMapping("/admin/saveOrUpdateColor.do")
	public void saveOrUpdateColor(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "colorName", required = true) String colorName,
			@RequestParam(value = "colorContent", required = true) String colorContent,
			@RequestParam(value = "id", required = false) Integer id) {
		JSONObject jv = new JSONObject();
		if (colorService.isExistColorName(colorName,id)) {
			jv.put("code", Const.EXIST_COLOR);
		} else {
			if (id != null) {// update
				Color color = colorService.getById(id);
				if (color != null) {
					color.setColorContent(colorContent);
					color.setColorName(colorName);
					colorService.update(color);
					jv.put("code", Const.SUCCESS);
				} else {
					jv.put("code", Const.NO_SUCH_COLOR);
				}
			} else {// save
				Color color = new Color();
				color.setColorContent(colorContent);
				color.setColorName(colorName);
				colorService.save(color);
				jv.put("code", Const.SUCCESS);
				jv.put("colorId", color.getId());
			}
		}
		Utils.writeBack(request, response, jv);
	}
}
