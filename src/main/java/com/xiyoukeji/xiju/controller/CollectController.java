package com.xiyoukeji.xiju.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.xiyoukeji.xiju.core.enums.Const;
import com.xiyoukeji.xiju.core.utils.Utils;
import com.xiyoukeji.xiju.domain.GoodsListShow;
import com.xiyoukeji.xiju.model.Collect;
import com.xiyoukeji.xiju.model.Goods;

@Controller
public class CollectController extends BaseController{
	@RequestMapping("/user/addCollect.do")
	public void addCollect(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="goodsId",required=true)Integer goodsId){
		JSONObject jv = new JSONObject();
		Integer userId=getUserId(request);
		Collect collect =collectService.getByUserIdAndGoodsId(userId, goodsId);
		if(collect==null){
			collect = new Collect();
			collect.setCollectTime((int)(System.currentTimeMillis()/1000));
			collect.setGoodsId(goodsId);
			collect.setUserId(userId);
			collectService.save(collect);
		}
		jv.put("code", Const.SUCCESS);
		Utils.writeBack(request, response, jv);
	}
	
	@RequestMapping("/user/deleteCollect.do")
	public void deleteCollect(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="goodsId",required=true)Integer goodsId){
		JSONObject jv = new JSONObject();
		Integer userId=getUserId(request);
		Collect collect =collectService.getByUserIdAndGoodsId(userId, goodsId);
		if(collect!=null){
			collectService.del(collect.getId());
		}
		jv.put("code", Const.SUCCESS);
		Utils.writeBack(request, response, jv);
	}
	@RequestMapping("/user/getCollect.do")
	public void getCollect(HttpServletRequest request,HttpServletResponse response){
		JSONObject jv = new JSONObject();
		Integer userId= getUserId(request);
		List<Collect> collectList =collectService.getByUserId(userId);
		List<Integer> goodsIds = new ArrayList<Integer>();
		List<GoodsListShow> goodsListShow = new ArrayList<GoodsListShow>();
		for(Collect collect:collectList){
			goodsIds.add(collect.getGoodsId());
		}
		if(goodsIds.size()>0){
			List<Goods> goodsList =goodsService.getAllByIds(goodsIds);
			for(Goods goods:goodsList){
				GoodsListShow goodsShow = new GoodsListShow(goods,1);
				goodsListShow.add(goodsShow);
			}
			
		}
		jv.put("info", goodsListShow);
		Utils.writeBack(request, response, jv);
	}
	
	@RequestMapping("/user/isCollected.do")
	public void isCollected(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="goodsId")Integer goodsId){
		JSONObject jv = new JSONObject();
		Integer userId=getUserId(request);
		Collect collect =collectService.getByUserIdAndGoodsId(userId, goodsId);
		jv.put("res", collect==null?"false":"true");
		Utils.writeBack(request, response, jv);
	}
}
