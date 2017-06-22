package com.xiyoukeji.xiju.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.xiyoukeji.xiju.core.enums.CommentType;
import com.xiyoukeji.xiju.core.enums.Const;
import com.xiyoukeji.xiju.core.utils.Utils;
import com.xiyoukeji.xiju.model.ChuanyiActivity;

@Controller
public class ChuanyiActivityController extends BaseController {

	@RequestMapping("/front/getChuanyiActivity.do")
	public void getChuanyiActivity(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "id", required = true) Integer id) {
		JSONObject jv = new JSONObject();
		jv.put("info", chuanyiActivityService.getById(id));
		jv.put("con", commentService.getByTypeAndTypeId(
				CommentType.CHUNAYI.value(), id));
		Utils.writeBack(request, response, jv);
	}

	@RequestMapping("/admin/saveOrUpdateChuanyi.do")
	public void saveOrUpdate(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "chuanyiContent", required = true) String chuanyiContent,
			@RequestParam(value = "chuanyiName", required = true) String chuanyiName,
			@RequestParam(value = "id", required = false) Integer id) {
		JSONObject jv = new JSONObject();
		if (id != null) {// 修改
			ChuanyiActivity chuanyiActivity = chuanyiActivityService
					.getById(id);
			if (chuanyiActivity != null) {
				chuanyiActivity.setChuanyiContent(chuanyiContent);
				chuanyiActivity.setChuanyiName(chuanyiName);
				chuanyiActivityService.update(chuanyiActivity);
				jv.put("code", Const.SUCCESS);
			} else {
				jv.put("code", Const.NO_SUCH_CHUANYI);
			}
		} else {// 新增
			ChuanyiActivity chuanyiActivity = new ChuanyiActivity();
			chuanyiActivity.setChuanyiContent(chuanyiContent);
			chuanyiActivity.setChuanyiName(chuanyiName);
			chuanyiActivityService.save(chuanyiActivity);
			jv.put("code", Const.SUCCESS);
			jv.put("chuanyiId", chuanyiActivity.getId());
		}

		Utils.writeBack(request, response, jv);
	}
	
	@RequestMapping("/admin/getChuangyiActivityList.do")
	public void getChuangyiActivityList(HttpServletRequest request,HttpServletResponse response){
		JSONObject jv = new JSONObject();
		List<ChuanyiActivity> list=chuanyiActivityService.getAll();
		jv.put("info", list);
		Utils.writeBack(request, response, jv);
	}
}
