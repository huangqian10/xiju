package com.xiyoukeji.xiju.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.xiyoukeji.xiju.core.enums.Const;
import com.xiyoukeji.xiju.core.utils.Utils;
import com.xiyoukeji.xiju.domain.FaxianActivityShow;
import com.xiyoukeji.xiju.domain.GoodsListShow;
import com.xiyoukeji.xiju.model.Collect;
import com.xiyoukeji.xiju.model.Content;
import com.xiyoukeji.xiju.model.FaxianActivity;
import com.xiyoukeji.xiju.model.Goods;

@Controller
public class FaxianActivityController extends BaseController {

	@RequestMapping("/front/getFaxianActivity.do")
	public void getFaxianActivity(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "id", required = true) Integer id) {
		Integer userId = getUserId(request);
		JSONObject jv = new JSONObject();
		FaxianActivity faxianActivity = faxianActivityService.getById(id);
		List<GoodsListShow> goodsListShowList = new ArrayList<GoodsListShow>();
		List<Collect> collects = collectService.getByUserId(userId);
		List<Content> contentList = new ArrayList<Content>();
		Map<Integer, Integer> collectMap = new HashMap<Integer, Integer>();
		for (Collect collect : collects) {
			collectMap.put(collect.getGoodsId(), 1);
		}
		if (faxianActivity != null) {
			String goodsId = faxianActivity.getGoodsIds();
			if (!StringUtils.isEmpty(goodsId)) {
				List<Goods> goodsList = goodsService.getByIdList(goodsId);
				for (Goods goods : goodsList) {
					Integer isCollect = collectMap.get(goods.getId()) == null ? 0
							: 1;
					GoodsListShow goodsListShow = new GoodsListShow(goods,
							isCollect);
					goodsListShowList.add(goodsListShow);
				}
			}
			if (!StringUtils.isEmpty(faxianActivity.getContentIds())) {
				contentList = contentService.getByIds(faxianActivity
						.getContentIds());
			}
		}
		FaxianActivityShow faxianActivityShow = new FaxianActivityShow(
				faxianActivity, goodsListShowList, contentList);
		collectMap = null;
		jv.put("info", faxianActivityShow);
		Utils.writeBack(request, response, jv);
	}

	@RequestMapping("/admin/getFaxianActivity.do")
	public void adminGetFaxianActivity(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "id", required = true) Integer id) {
		JSONObject jv = new JSONObject();
		FaxianActivity faxianActivity = faxianActivityService.getById(id);
		List<GoodsListShow> goodsListShowList = new ArrayList<GoodsListShow>();
		List<Content> contentList = new ArrayList<Content>();
		if (faxianActivity != null) {
			String goodsId = faxianActivity.getGoodsIds();
			if (!StringUtils.isEmpty(goodsId)) {
				List<Goods> goodsList = goodsService.getByIdList(goodsId);
				for (Goods goods : goodsList) {
					GoodsListShow goodsListShow = new GoodsListShow(goods, 0);
					goodsListShowList.add(goodsListShow);
				}
			}
			if (!StringUtils.isEmpty(faxianActivity.getContentIds())) {
				contentList = contentService.getByIds(faxianActivity
						.getContentIds());
			}
		}
		FaxianActivityShow faxianActivityShow = new FaxianActivityShow(
				faxianActivity, goodsListShowList, contentList);
		jv.put("info", faxianActivityShow);
		Utils.writeBack(request, response, jv);
	}

	@RequestMapping("/admin/saveOrUpdateFaxianActivity.do")
	public void addFaxianActivity(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "faxianName", required = true) String faxianName,
			@RequestParam(value = "posterUrl", required = true) String posterUrl,
			@RequestParam(value = "posterType", required = true) Integer posterType,
			@RequestParam(value = "goodsIds", required = true) String goodsIds,
			@RequestParam(value = "detail", required = true) String detail,
			@RequestParam(value = "title", required = true) String title,
			@RequestParam(value = "price", required = true) Integer price,
			@RequestParam(value = "contentIds", required = true) String contentIds,
			@RequestParam(value = "id", required = false) Integer id) {
		JSONObject jv = new JSONObject();
		if (id != null) {// 修改
			FaxianActivity faxianActivity = faxianActivityService.getById(id);
			if (faxianActivity != null) {
				faxianActivity.setContentIds(contentIds);
				faxianActivity.setDetail(detail);
				faxianActivity.setFaxianName(faxianName);
				faxianActivity.setGoodsIds(goodsIds);
				faxianActivity.setPosterType(posterType);
				faxianActivity.setPosterUrl(posterUrl);
				faxianActivity.setPrice(price);
				faxianActivity.setTitle(title);
				faxianActivityService.update(faxianActivity);
				jv.put("code", Const.SUCCESS);
			} else {
				jv.put("code", Const.NO_SUCH_FAXIAN);
			}
		} else {// 增加
			FaxianActivity faxianActivity = new FaxianActivity();
			faxianActivity.setContentIds(contentIds);
			faxianActivity.setDetail(detail);
			faxianActivity.setFaxianName(faxianName);
			faxianActivity.setGoodsIds(goodsIds);
			faxianActivity.setPosterType(posterType);
			faxianActivity.setPosterUrl(posterUrl);
			faxianActivity.setPrice(price);
			faxianActivity.setTitle(title);
			faxianActivityService.save(faxianActivity);
			jv.put("code", Const.SUCCESS);
			jv.put("faxianId", faxianActivity.getId());
		}
		Utils.writeBack(request, response, jv);
	}

	@RequestMapping("/admin/getFaxianActivityList.do")
	public void getFaxianActivityList(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject jv = new JSONObject();
		List<FaxianActivity> list = faxianActivityService.getAll();
		jv.put("info", list);
		Utils.writeBack(request, response, jv);
	}
}
