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
import com.xiyoukeji.xiju.core.enums.GoodsStatus;
import com.xiyoukeji.xiju.core.utils.Utils;
import com.xiyoukeji.xiju.domain.CartShow;
import com.xiyoukeji.xiju.domain.PayMsg;
import com.xiyoukeji.xiju.model.Cart;
import com.xiyoukeji.xiju.model.Goods;
import com.xiyoukeji.xiju.model.GoodsColorStyle;

@Controller
public class CartController extends BaseController {

	@RequestMapping("/user/saveOrUpdateCart.do")
	public void saveOrUpdateCart(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "goodsId", required = true) Integer goodsId,
			@RequestParam(value = "payMsg", required = true) String payJson,
			@RequestParam(value = "cartId", required = false) Integer cartId) {
		List<PayMsg> payMsgList = format(payJson);
		Integer userId = getUserId(request);
		Cart cart = new Cart();
		Integer code = Const.SUCCESS;
		JSONObject jv = new JSONObject();
		Goods goods = goodsService.getById(goodsId);
		if (goods.getStatus() == GoodsStatus.OFFLINE.value()) {
			code = Const.GOODS_OFFLINE;
		}
		for (PayMsg payMsg : payMsgList) {
			if ((payMsg.getGoodsColorStyleId() != null)
					&& !goodsColorStyleService.isEnough(
							payMsg.getGoodsColorStyleId(),
							payMsg.getGoodsAmount())) {
				code = Const.LEFT_COUNT_NOT_ENOUGH;
			}
		}
		if (Const.SUCCESS.equals(code)) {
			if (cartId != null) {
				cart.setId(cartId);
				cart.setGoodsId(goodsId);
				cart.setPayJson(payJson);
				cart.setUserId(userId);
				cart.setCreateTime((int) (System.currentTimeMillis() / 1000));
				cartService.update(cart);
			} else {
				cart.setGoodsId(goodsId);
				cart.setPayJson(payJson);
				cart.setUserId(userId);
				cart.setCreateTime((int) (System.currentTimeMillis() / 1000));
				cartService.save(cart);
			}
			jv.put("code", Const.SUCCESS);
		}
		Utils.writeBack(request, response, jv);
	}
	@RequestMapping("/user/delCart.do")
	public void delCart(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="cardId",required=true)Integer cardId){
		JSONObject jv = new JSONObject();
		jv.put("code", Const.SUCCESS);
		cartService.delete(cardId);
		Utils.writeBack(request, response, jv);
	}
	
	@RequestMapping("/user/getMycart.do")
	public void getMyCart(HttpServletRequest request,HttpServletResponse response){
		Integer userId =getUserId(request);
		JSONObject jv = new JSONObject();
		List<CartShow> cartShowList = new ArrayList<CartShow>();
		List<Cart> cartList=cartService.getByUserId(userId);
		for(Cart cart:cartList){
			int amount=0;
			String leftEnough="true";
			CartShow cartShow =  new CartShow();
			cartShow.setCart(cart);
			List<PayMsg> payMsgList = format(cart.getPayJson());
			for (PayMsg payMsg : payMsgList) {
				GoodsColorStyle goodsColorStyle=goodsColorStyleService.getById(payMsg.getGoodsColorStyleId());
				amount+=goodsColorStyle.getSalePrice()*payMsg.getGoodsAmount();
				if(payMsg.getGoodsAmount()>goodsColorStyle.getLeftCount()){
					leftEnough="false";
				}
			}
			cartShow.setLeftEnough(leftEnough);
			cartShow.setPrice(amount);
			cartShowList.add(cartShow);
		}
		jv.put("info", cartShowList);
		Utils.writeBack(request, response, jv);
	}
}
