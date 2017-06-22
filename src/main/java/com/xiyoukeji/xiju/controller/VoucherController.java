package com.xiyoukeji.xiju.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.xiyoukeji.xiju.core.enums.VoucherState;
import com.xiyoukeji.xiju.core.utils.Utils;
import com.xiyoukeji.xiju.model.Voucher;

@Controller
public class VoucherController extends BaseController{
	@RequestMapping("/user/getVoucher.do")
	public void getVoucher(HttpServletRequest request,HttpServletResponse response){
		Integer userId=getUserId(request);
		List<Voucher> list =voucherService.getVoucherByUserId(userId);
		List<Voucher> list2= new ArrayList<Voucher>();
		int nowTime=(int)(System.currentTimeMillis()/1000);
		for(Voucher voucher:list){
			if(!(voucher.getVoucherBeginTime()<=nowTime&&nowTime<=voucher.getVoucherEndTime())){
				voucher.setVoucherStatus(VoucherState.USELESS.value());
			}
			if(voucher.getVoucherStatus()!=VoucherState.USED.value()){
				list2.add(voucher);
			}
		}
		JSONObject jv = new JSONObject();
		jv.put("info", list2);
		Utils.writeBack(request, response, jv);
	}
}
