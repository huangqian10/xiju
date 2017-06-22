package com.xiyoukeji.xiju.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.xiyoukeji.xiju.core.enums.ActivityType;
import com.xiyoukeji.xiju.core.enums.Const;
import com.xiyoukeji.xiju.core.utils.Utils;
import com.xiyoukeji.xiju.model.Activity;
import com.xiyoukeji.xiju.model.FaxianActivity;
import com.xiyoukeji.xiju.model.Goods;

@Controller
public class ActivityController extends BaseController {

	/**
	 * 首页获取活动列表
	 */
	@RequestMapping("/front/getActivity.do")
	public void getActivity(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "type", required = false) Integer type) {
		JSONObject jv = new JSONObject();
		List<Activity> acList = activityService.getActivity(true, type);
		jv.put("info", acList);
		Utils.writeBack(request, response, jv);
	}

	/**
	 * 后台管理获取活动列表
	 */
	@RequestMapping("/admin/getActivity.do")
	public void getActivityByAdmin(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject jv = new JSONObject();
		List<Activity> acList = activityService.getActivity(false, null);
		jv.put("info", acList);
		Utils.writeBack(request, response, jv);
	}

	/**
	 * 后台获取活动详情
	 */
	@RequestMapping("/admin/getActivityInfo.do")
	public void getActivityInfoByAdmin(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="id",required=true)Integer id){
		JSONObject jv = new JSONObject();
		jv.put("info", activityService.getById(id));
		Utils.writeBack(request, response, jv);
	}
	
	/**
	 * 增加活动,修改活动
	 */
	@RequestMapping(value = { "/admin/addActivity.do",
			"/admin/updateActivity.do" })
	public void addActivity(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "activityType", required = true) Integer activityType,
			@RequestParam(value = "beginTime", required = true) Integer beginTime,
			@RequestParam(value = "endTime", required = true) Integer endTime,
			@RequestParam(value = "xuangouGoodsId", required = false) Integer xuangouGoodsId,
			@RequestParam(value = "xuangouPic", required = false) String xuangouPic,
			@RequestParam(value = "xuangouSmallUrl", required = false) String xuangouSmallUrl,
			@RequestParam(value = "faxianId", required = false) Integer faxianId,
			@RequestParam(value = "faxianPic", required = false) String faxianPic,
			@RequestParam(value = "chuanyiId", required = false) Integer chuanyiId,
			@RequestParam(value = "chuanyiPic", required = false) String chuanyiPic,
			@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "detail", required = false) String detail,
			@RequestParam(value = "price", required = false) Integer price) {
		JSONObject jv = new JSONObject();
		String title2 = "";
		String detail2 = "";
		Integer price2 = -1;
		boolean isupdate = request.getRequestURI().contains("update");
		boolean valiadate = true;
		if (isupdate && id == null) {
			jv.put("code", Const.ACTIVITY_DATA_ERROR);
			jv.put("desc", "id empty");
		} else {
			if (activityType == ActivityType.XUANGOU.value()) {
				if (xuangouGoodsId == null || StringUtils.isEmpty(xuangouPic)
						|| StringUtils.isEmpty(xuangouSmallUrl)) {
					jv.put("code", Const.ACTIVITY_DATA_ERROR);
					jv.put("desc", "xuangou params error");
					valiadate = false;
				}
				if (valiadate) {
					Goods goods = goodsService.getById(xuangouGoodsId);
					if (goods == null) {
						valiadate = false;
						jv.put("code", Const.ACTIVITY_DATA_ERROR);
					} else {
						title2 = goods.getTitle();
						detail2 = goods.getDetail();
						price2 = goods.getGoodsSalePrice();
					}
				}
			} else if (activityType == ActivityType.FAXIAN.value()) {
				if (faxianId == null || StringUtils.isEmpty(faxianPic)) {
					jv.put("code", Const.ACTIVITY_DATA_ERROR);
					jv.put("desc", "faxian params error");
					valiadate = false;
				}
				if (valiadate) {
					FaxianActivity faxianActivity = faxianActivityService
							.getById(faxianId);
					if (faxianActivity == null) {
						valiadate = false;
						jv.put("code", Const.ACTIVITY_DATA_ERROR);
					} else {
						title2 = faxianActivity.getTitle();
						detail2 = faxianActivity.getDetail();
						price2 = faxianActivity.getPrice();
					}
				}
			} else if (activityType == ActivityType.CHUANYI.value()) {
				if (chuanyiId == null || StringUtils.isEmpty(chuanyiPic)) {
					jv.put("code", Const.ACTIVITY_DATA_ERROR);
					jv.put("desc", "chuanyi params error");
					valiadate = false;
				}
			} else {
				jv.put("code", Const.ACTIVITY_DATA_ERROR);
				jv.put("desc", "type error");
				valiadate = false;
			}
			if (valiadate) {
				jv.put("code", Const.SUCCESS);
				Activity activity = new Activity();
				activity.setActivityType(activityType);
				activity.setBeginTime(beginTime);
				activity.setChuanyiId(chuanyiId);
				activity.setChuanyiPic(chuanyiPic);
				activity.setEndTime(endTime);
				activity.setFaxianId(faxianId);
				activity.setFaxianPic(faxianPic);
				activity.setXuangouGoodsId(xuangouGoodsId);
				activity.setXuangouPic(xuangouPic);
				activity.setXuangouSmallUrl(xuangouSmallUrl);
				if (!StringUtils.isEmpty(title)) {
					activity.setTitle(title);
				} else {
					activity.setTitle(title2);
				}
				if (!StringUtils.isEmpty(detail)) {
					activity.setDetail(detail);
				} else {
					activity.setDetail(detail2);
				}
				if (price != null) {
					activity.setPrice(price);
				} else {
					activity.setPrice(price2);
				}
				if (isupdate) {
					activity.setId(id);
					activityService.update(activity);
				} else {
					activityService.save(activity);
				}

			}
		}
		Utils.writeBack(request, response, jv);
	}
}
