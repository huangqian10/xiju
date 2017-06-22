package com.xiyoukeji.xiju.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.xiyoukeji.xiju.core.enums.CommentType;
import com.xiyoukeji.xiju.core.enums.Const;
import com.xiyoukeji.xiju.core.enums.GoodsStatus;
import com.xiyoukeji.xiju.core.utils.Utils;
import com.xiyoukeji.xiju.domain.GoodsColorStyleShow;
import com.xiyoukeji.xiju.domain.GoodsListShow;
import com.xiyoukeji.xiju.model.Collect;
import com.xiyoukeji.xiju.model.Comment;
import com.xiyoukeji.xiju.model.Content;
import com.xiyoukeji.xiju.model.Goods;
import com.xiyoukeji.xiju.model.GoodsColorStyle;
import com.xiyoukeji.xiju.model.GoodsParams;
import com.xiyoukeji.xiju.model.GoodsScene;
import com.xiyoukeji.xiju.model.Params;

@Controller
public class GoodsController extends BaseController {
	/**
	 * 获取列表
	 */
	@RequestMapping("/front/getGoodsList.do")
	public void getGoodsList(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "colorId", required = false) Integer colorId,
			@RequestParam(value = "typeId", required = false) Integer typeId,
			@RequestParam(value = "sceneId", required = false) Integer sceneId) {
		Integer userId = getUserId(request);
		JSONObject jv = new JSONObject();
		List<Integer> goodsId1 = null;
		List<Goods> goods = new ArrayList<Goods>();
		if (typeId != null) {
			goodsId1 = goodsService.getByTypeId(typeId);
		}
		if (colorId != null) {
			goodsId1 = goodsColorStyleService.getByColorId(colorId, goodsId1);
		}
		if (sceneId != null) {
			goodsId1 = goodsSceneService.getBySceneId(sceneId, goodsId1);
		}

		if (goodsId1 != null && goodsId1.size() == 0) {
			jv.put("info", goods);
		} else {
			List<Collect> collects = collectService.getByUserId(userId);
			Map<Integer, Integer> collectMap = new HashMap<Integer, Integer>();
			for (Collect collect : collects) {
				collectMap.put(collect.getGoodsId(), 1);
			}
			goods = goodsService.getAllUseful(goodsId1);
			List<GoodsListShow> showList = new ArrayList<GoodsListShow>();
			for (Goods good : goods) {
				Integer isCollect = collectMap.get(good.getId()) == null ? 0
						: 1;
				showList.add(new GoodsListShow(good, isCollect));
			}
			jv.put("info", showList);
		}
		Utils.writeBack(request, response, jv);
	}

	/**
	 * 详情页
	 */
	@RequestMapping("/front/getGoods.do")
	public void getGoods(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "goodsId", required = true) Integer goodsId) {
		Integer userId = getUserId(request);
		JSONObject jv = new JSONObject();
		Goods goods = goodsService.getById(goodsId);
		if (goods != null
				&& goods.getStatus().equals(GoodsStatus.ONLINE.value())) {
			Integer isCollet = collectService.getByUserIdAndGoodsId(userId,
					goodsId) == null ? 0 : 1;
			jv.put("code", Const.SUCCESS);
			List<Comment> comment = commentService.getByTypeAndTypeId(
					CommentType.GOOD.value(), goodsId);
			jv.put("goodsInfo", goods);
			if (!StringUtils.isEmpty(goods.getTuijianGoodsId())) {
				jv.put("tuijian", goodsService.getByIds(
						goods.getTuijianGoodsId(), userId));
			} else {
				jv.put("tuijian", new ArrayList<GoodsListShow>());
			}
			if (!StringUtils.isEmpty(goods.getContentIds())) {
				jv.put("content",
						contentService.getByIds(goods.getContentIds()));
			} else {
				jv.put("content", new ArrayList<Content>());
			}
			jv.put("params", goodsParamsService.getByGoodsId(goods.getId()));
			jv.put("colorAndstyle",
					goodsColorStyleService.getByGoodsId(goods.getId()));
			jv.put("comment", comment);
			jv.put("isCollect", isCollet);
		} else {
			jv.put("code", Const.NO_SUCH_GOODS);
		}
		Utils.writeBack(request, response, jv);
	}

	/**
	 * 管理平台获取商品列表
	 */
	@RequestMapping("/admin/getGoodsList.do")
	public void getGoodsList(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "typeId", required = false) Integer typeId,
			@RequestParam(value = "status", required = false) Integer status,
			@RequestParam(value = "pageSize", required = true) Integer pageSize,
			@RequestParam(value = "pageNo", required = true) Integer pageNo) {
		List<Goods> goodsList = goodsService.getAllByAdmin(typeId, status);
		List<Goods> resList = new ArrayList<Goods>();
		JSONObject jv = new JSONObject();
		int dblength = goodsList.size();
		for (int i = pageSize * (pageNo - 1); i < pageSize * pageNo
				&& i < dblength; i++) {
			Goods goods = goodsList.get(i);
			goods.setTypeName(typeService.getById(goods.getTypeId())
					.getTypeName());
			resList.add(goods);
		}
		jv.put("info", resList);
		Utils.writeBack(request, response, jv);
	}

	/**
	 * 后台管理获取商品详情
	 */
	@RequestMapping("/admin/getGoods.do")
	public void adminGetGoods(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "goodsId", required = true) Integer goodsId) {
		JSONObject jv = new JSONObject();
		Goods goods = goodsService.getById(goodsId);
		if (goods != null) {
			List<GoodsColorStyleShow> goodsColorStyle = goodsColorStyleService
					.getByGoodsId(goods.getId());
			if (!StringUtils.isEmpty(goods.getTuijianGoodsId())) {// 推荐商品
				List<GoodsListShow> tuijianGoods = goodsService.getByIds(goods
						.getTuijianGoodsId());
				jv.put("tuijian", tuijianGoods);
			}
			if(!StringUtils.isEmpty(goods.getContentIds())){
				jv.put("content",
						contentService.getByIds(goods.getContentIds()));
			}
			List<GoodsScene> goodsScenes= goodsSceneService.getByGoodsId(goods.getId());
			jv.put("scene", goodsScenes);
			
			List<GoodsParams> goodsParams = goodsParamsService.getWithGoodsId(goods.getId());
			List<GoodsParams> resgoodsParams = new ArrayList<GoodsParams>();
			List<Params> params = paramsService.getAll();
			for(GoodsParams gp:goodsParams){
				for(Params p:params){
					if(gp.getParamsId().equals(p.getId())){
						gp.setIcon(p.getIcon());
						gp.setName(p.getName());
						resgoodsParams.add(gp);
						break;
					}
				}
			}
			jv.put("params", resgoodsParams);
			jv.put("goodsColorStyle", goodsColorStyle);
			jv.put("goods", goods);
		}
		Utils.writeBack(request, response, jv);
	}

	/**
	 * 后台增加商品
	 */
	@RequestMapping("/admin/addGoods.do")
	public void addGoods(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "poster", required = true) String poster,
			@RequestParam(value = "posterType", required = true) Integer posterType,
			@RequestParam(value = "title", required = true) String title,
			@RequestParam(value = "detail", required = true) String detail,
			@RequestParam(value = "goodsPrice", required = true) Integer goodsPrice,
			@RequestParam(value = "goodsSalePrice", required = true) Integer goodsSalePrice,
			@RequestParam(value = "smallUrl", required = true) String smallUrl,
			@RequestParam(value = "tuijianGoodsId", required = false) String tuijianGoodsId,
			@RequestParam(value = "contentIds", required = false) String contentIds,
			@RequestParam(value = "typeId", required = true) Integer typeId,
			@RequestParam(value = "status", required = true) Integer status,
			@RequestParam(value = "sceneIds", required = true) String sceneIds,
			@RequestParam(value = "paramsJson", required = true) String paramsJson,
			@RequestParam(value = "goodsStyleJson", required = true) String goodsStyleJson,
			@RequestParam(value = "sizePic", required = true) String sizePic) {
		JSONObject jv = new JSONObject();
		List<GoodsParams> list = formatGoodsParams(paramsJson);// 属性列表
		List<GoodsColorStyle> goodsColorStyleList = formatGoodsColorStyle(goodsStyleJson);
		String[] sceneId = sceneIds.split(";");// 场景列表
		Goods goods = new Goods();
		goods.setContentIds(contentIds);
		goods.setDetail(detail);
		goods.setGoodsPrice(goodsPrice);
		goods.setGoodsSalePrice(goodsSalePrice);
		goods.setPoster(poster);
		goods.setPosterType(posterType);
		goods.setSmallUrl(smallUrl);
		goods.setStatus(status);
		goods.setTitle(title);
		goods.setTuijianGoodsId(tuijianGoodsId);
		goods.setTypeId(typeId);
		goods.setSizePic(sizePic);
		goodsService.save(goods);
		for (int i = 0; i < sceneId.length; i++) {// 场景数据加入
			Integer sId = Integer.parseInt(sceneId[i]);
			GoodsScene goodsScene = new GoodsScene();
			goodsScene.setGoodsId(goods.getId());
			goodsScene.setSceneId(sId);
			goodsSceneService.save(goodsScene);
		}
		for (GoodsParams params : list) {// 参数数据加入
			params.setGoodsId(goods.getId());
			goodsParamsService.save(params);
		}
		for (GoodsColorStyle style : goodsColorStyleList) {// 颜色类型数据加入
			style.setGoodsId(goods.getId());
			goodsColorStyleService.save(style);
		}
		jv.put("code", Const.SUCCESS);
		Utils.writeBack(request, response, jv);
	}

	/**
	 * 后台下架商品
	 */
	@RequestMapping("/admin/changeStatus.do")
	public void offlineGoods(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="goodsId",required=true)Integer goodsId,
			@RequestParam(value="status",required=true)Integer status){
		JSONObject jv = new JSONObject();
		Goods goods =goodsService.getById(goodsId);
		if(goods!=null){
			if(status.equals(GoodsStatus.OFFLINE.value())){
				goods.setStatus(GoodsStatus.OFFLINE.value());
			}else{
				goods.setStatus(GoodsStatus.ONLINE.value());
			}
			goodsService.update(goods);
			jv.put("code", Const.SUCCESS);
		}
		Utils.writeBack(request, response, jv);
	}
	/**
	 * 后台修改商品
	 */
	@RequestMapping("/admin/updateGoods.do")
	public void updateGoods(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "poster", required = true) String poster,
			@RequestParam(value = "posterType", required = true) Integer posterType,
			@RequestParam(value = "title", required = true) String title,
			@RequestParam(value = "detail", required = true) String detail,
			@RequestParam(value = "goodsPrice", required = true) Integer goodsPrice,
			@RequestParam(value = "goodsSalePrice", required = true) Integer goodsSalePrice,
			@RequestParam(value = "smallUrl", required = true) String smallUrl,
			@RequestParam(value = "tuijianGoodsId", required = false) String tuijianGoodsId,
			@RequestParam(value = "contentIds", required = false) String contentIds,
			@RequestParam(value = "typeId", required = true) Integer typeId,
			@RequestParam(value = "status", required = true) Integer status,
			@RequestParam(value = "sceneIds", required = true) String sceneIds,
			@RequestParam(value = "paramsJson", required = true) String paramsJson,
			@RequestParam(value = "id", required = true) Integer id,
			@RequestParam(value = "goodsStyleJson", required = true) String goodsStyleJson,
			@RequestParam(value = "sizePic", required = true) String sizePic) {
		JSONObject jv = new JSONObject();
		List<GoodsParams> list = formatGoodsParams(paramsJson);// 属性列表
		List<GoodsColorStyle> goodsColorStyleList = formatGoodsColorStyle(goodsStyleJson);
		String[] sceneId = sceneIds.split(";");// 场景列表
		Goods goods = goodsService.getById(id);
		if (goods != null) {
			goods.setContentIds(contentIds);
			goods.setDetail(detail);
			goods.setGoodsPrice(goodsPrice);
			goods.setGoodsSalePrice(goodsSalePrice);
			goods.setPoster(poster);
			goods.setPosterType(posterType);
			goods.setSmallUrl(smallUrl);
			goods.setStatus(status);
			goods.setTitle(title);
			goods.setTuijianGoodsId(tuijianGoodsId);
			goods.setTypeId(typeId);
			goods.setSizePic(sizePic);
			goodsService.update(goods);
			/*** 场景处理 ***/
			List<GoodsScene> goodsSceneList = goodsSceneService
					.getByGoodsId(goods.getId());// 原先存入的场景数据
			for (int i = 0; i < sceneId.length; i++) {// 场景数据加入
				Integer sId = Integer.parseInt(sceneId[i]);
				boolean hasExist = false;
				for (GoodsScene goodsScene : goodsSceneList) {
					if (goodsScene.getSceneId().equals(sId)) {
						hasExist = true;
						break;
					}
				}
				if (!hasExist) {// 说明新增了这个场景
					GoodsScene goodsScene = new GoodsScene();
					goodsScene.setGoodsId(goods.getId());
					goodsScene.setSceneId(sId);
					goodsSceneService.save(goodsScene);
				}
			}
			for (GoodsScene goodsScene : goodsSceneList) {// 场景数据删除
				boolean hasExist = false;
				for (int i = 0; i < sceneId.length; i++) {
					Integer sId = Integer.parseInt(sceneId[i]);
					if (goodsScene.getSceneId().equals(sId)) {
						hasExist = true;
						break;
					}
				}
				if (!hasExist) {// 说明这个场景被作废了
					goodsSceneService.del(goodsScene);
				}
			}
			/*** 属性处理 ***/
			List<GoodsParams> dbList = goodsParamsService.getWithGoodsId(goods
					.getId());
			for (GoodsParams params : list) {// 参数数据加入
				boolean hasExist = false;
				for (GoodsParams params2 : dbList) {
					if (params.getParamsId().equals(params2.getParamsId())) {
						hasExist = true;
						params2.setContent(params.getContent());
						goodsParamsService.update(params2);
						break;
					}
				}
				if (!hasExist) {// 新增属性
					params.setGoodsId(goods.getId());
					goodsParamsService.save(params);
				}

			}
			for (GoodsParams params2 : dbList) {
				boolean hasExist = false;
				for (GoodsParams params : list) {
					if (params.getParamsId().equals(params2.getParamsId())) {
						hasExist = true;
						break;
					}
				}
				if (!hasExist) {// 删除属性
					goodsParamsService.del(params2);
				}
			}
			/** 颜色类型处理 */
			List<GoodsColorStyle> goodsColorStyleDbList = goodsColorStyleService
					.getListByGoodsId(goods.getId());
			for (GoodsColorStyle goodsColorStyle : goodsColorStyleList) {
				boolean hasExist = false;
				for (GoodsColorStyle dbGoodsColorStyle : goodsColorStyleDbList) {
					if (goodsColorStyle.getColorId().equals(
							dbGoodsColorStyle.getColorId())
							&& goodsColorStyle.getStyleId().equals(
									dbGoodsColorStyle.getStyleId())) {
						hasExist = true;
						dbGoodsColorStyle.setColorId(goodsColorStyle.getColorId());
						dbGoodsColorStyle.setStyleId(goodsColorStyle.getStyleId());
						dbGoodsColorStyle.setLeftCount(goodsColorStyle.getLeftCount());
						dbGoodsColorStyle.setPicUrl(goodsColorStyle.getPicUrl());
						dbGoodsColorStyle.setPrice(goodsColorStyle.getPrice());
						dbGoodsColorStyle.setSalePrice(goodsColorStyle.getSalePrice());
						dbGoodsColorStyle.setSid(goodsColorStyle.getSid());
						goodsColorStyleService.update(dbGoodsColorStyle);
						break;
					}
				}
				if (!hasExist) {
					goodsColorStyle.setGoodsId(goods.getId());
					goodsColorStyleService.save(goodsColorStyle);
				}
			}
			jv.put("code", Const.SUCCESS);
		} else {
			jv.put("code", Const.NO_SUCH_GOODS);
		}

		Utils.writeBack(request, response, jv);
	}

	private List<GoodsParams> formatGoodsParams(String paramsJson) {
		JSONObject paramsJv = JSONObject.parseObject(paramsJson);
		String paramsJsonStr = paramsJv.getString("paramsJson");
		List<GoodsParams> goodsParams = JSONArray.parseArray(paramsJsonStr,
				GoodsParams.class);
		return goodsParams;
	}

	private List<GoodsColorStyle> formatGoodsColorStyle(String goodsStyleJson) {
		JSONObject goodsColorStyleJv = JSONObject.parseObject(goodsStyleJson);
		String goodsColorStyleJsonStr = goodsColorStyleJv
				.getString("goodsStyleJson");
		List<GoodsColorStyle> goodsColorStyles = JSONArray.parseArray(
				goodsColorStyleJsonStr, GoodsColorStyle.class);
		return goodsColorStyles;
	}

	/*
	 * public static void main(String[] args) {
	 * 
	 * List<GoodsParams> list = new ArrayList<GoodsParams>(); GoodsParams prams
	 * = new GoodsParams(); prams.setContent("conten1"); prams.setParamsId(1);
	 * GoodsParams prams2 = new GoodsParams(); prams2.setContent("conten2");
	 * prams2.setParamsId(2); list.add(prams2); list.add(prams); JSONObject jv =
	 * new JSONObject(); jv.put("paramsJson", list);
	 * System.out.println(jv.toJSONString()); }
	 */

}
