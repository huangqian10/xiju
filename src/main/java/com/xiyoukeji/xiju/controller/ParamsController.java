package com.xiyoukeji.xiju.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.xiyoukeji.xiju.core.enums.Const;
import com.xiyoukeji.xiju.core.utils.Utils;
import com.xiyoukeji.xiju.model.Params;

@Controller
public class ParamsController extends BaseController {

	@RequestMapping("/admin/saveOrUpdateParams.do")
	public void saveOrUpdate(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "icon", required = true) String icon,
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "id", required = false) Integer id) {
		JSONObject jv = new JSONObject();
		if (!paramsService.IsExistParamsName(name,id)) {
			if (id != null) {// update
				Params params = paramsService.getParamsById(id);
				if (params != null) {
					params.setIcon(icon);
					params.setName(name);
					paramsService.update(params);
					jv.put("code", Const.SUCCESS);
				} else {
					jv.put("code", Const.NO_SUCH_PARAMS);
				}
			} else {// add
				Params params = new Params();
				params.setIcon(icon);
				params.setId(id);
				params.setName(name);
				paramsService.save(params);
				jv.put("code", Const.SUCCESS);
				jv.put("id", params.getId());
			}
		} else {
			jv.put("code", Const.EXIST_PARAMS);
		}
		Utils.writeBack(request, response, jv);
	}

	@RequestMapping("/admin/getParamsList.do")
	public void getParamsList(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject jv = new JSONObject();
		jv.put("info", paramsService.getAll());
		Utils.writeBack(request, response, jv);
	}
}
