package com.xiyoukeji.xiju.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.xiyoukeji.xiju.core.enums.Const;
import com.xiyoukeji.xiju.core.enums.IsDefault;
import com.xiyoukeji.xiju.core.utils.Utils;
import com.xiyoukeji.xiju.model.ReceiptAddress;

@Controller
public class ReceiptAddressController extends BaseController {
	
	Logger logger =Logger.getLogger(ReceiptAddressController.class);
	/**
	 * 增加我的收货地址
	 */
	@RequestMapping("/user/addReceiptAddress.do")
	public void addReceiptAddress(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "address", required = true) String address,
			@RequestParam(value = "mobile", required = true) String mobile,
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "isDefault", required = false) Integer isDefault,
			@RequestParam(value = "local", required = true) String local) {
		Integer userId = getUserId(request);
		ReceiptAddress receiptAddress = new ReceiptAddress();
		receiptAddress.setAddress(address);
		receiptAddress.setUserId(userId);
		receiptAddress.setLocal(local);
		receiptAddress.setMobile(mobile);
		receiptAddress.setName(name);
		
		/**
		 * 取消原有的默认地址设置，设为当前地址为默认地址
		 */
		List<ReceiptAddress> rList=receiptAddressService.getReceiptAddress(null, userId);
		if(rList==null||(rList!=null&&rList.size()==0)){
			receiptAddress.setIsDefault(IsDefault.DEFAULT.value());
			receiptAddressService.save(receiptAddress);
		}else{
			if (isDefault != null && isDefault == IsDefault.DEFAULT.value()) {
				receiptAddressService.cancelDefault(userId);
				receiptAddress.setIsDefault(isDefault);
				receiptAddressService.save(receiptAddress);
			} else {
				receiptAddress.setIsDefault(IsDefault.NODEFAULT.value());
				receiptAddressService.save(receiptAddress);
			}
		}
		logger.info(address);
		JSONObject jv = new JSONObject();
		jv.put("code", Const.SUCCESS);
		Utils.writeBack(request, response, jv);
	}
	/**
	 * 修改我的收货地址
	 */
	@RequestMapping("/user/updateReceiptAddress.do")
	public void updateReceiptAddress(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "address", required = true) String address,
			@RequestParam(value = "mobile", required = true) String mobile,
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "isDefault", required = false) Integer isDefault,
			@RequestParam(value = "local", required = true) String local,
			@RequestParam(value = "id", required = true) Integer id) {
		Integer userId = getUserId(request);
		ReceiptAddress receiptAddress = new ReceiptAddress();
		receiptAddress.setAddress(address);
		receiptAddress.setUserId(userId);
		receiptAddress.setLocal(local);
		receiptAddress.setMobile(mobile);
		receiptAddress.setName(name);
		receiptAddress.setId(id);
		receiptAddress.setIsDefault(isDefault);
		/**
		 * 取消原有的默认地址，设置当前地址为默认地址
		 */
		if (receiptAddress.getIsDefault() != null
				&& receiptAddress.getIsDefault() == IsDefault.DEFAULT.value()) {
			receiptAddressService.cancelDefault(userId);
		}
		receiptAddressService.update(receiptAddress);
		JSONObject jv = new JSONObject();
		jv.put("code", Const.SUCCESS);
		Utils.writeBack(request, response, jv);
	}
	/**
	 * 删除我的收货地址
	 */
	@RequestMapping("/user/deleteReceiptAddress.do")
	public void deleteReceiptAddress(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "id", required = true) Integer id) {
		JSONObject jv = new JSONObject();
		Integer userId = getUserId(request);
		ReceiptAddress receiptAddress = receiptAddressService
				.getReceiptAddressById(id,userId);
		if (receiptAddress != null && receiptAddress.getUserId().equals(userId)) {
			receiptAddressService.delete(id);
			if(receiptAddress.getIsDefault()==IsDefault.DEFAULT.value()){
				receiptAddressService.changeDefault(userId);
			}
			jv.put("code", Const.SUCCESS);
		} else {
			jv.put("code", Const.DELETE_RECEIPT_ERROR);
		}
		Utils.writeBack(request, response, jv);
	}
	/**
	 * 设置收货地址为默认
	 */
	@RequestMapping("/user/setDefault.do")
	public void setDefault(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "id", required = true) Integer id) {
		JSONObject jv = new JSONObject();
		Integer userId= getUserId(request);
		ReceiptAddress r = receiptAddressService.getReceiptAddressById(id,userId);
		if (r != null
				&& (r.getIsDefault() == null || (r.getIsDefault() != null && r
						.getIsDefault() == IsDefault.NODEFAULT.value()))) {
				receiptAddressService.cancelDefault(userId);
				r.setIsDefault(IsDefault.DEFAULT.value());
				logger.info(r.getId()+"||"+r.getIsDefault());
				receiptAddressService.update(r);
				jv.put("code", Const.SUCCESS);
		}else{
			jv.put("code", Const.SETDEFAULT_ERROR);
		}
		Utils.writeBack(request, response, jv);
	}
	/**
	 * 获取我的收货地址
	 */
	@RequestMapping("/user/getReceiptAddress.do")
	public void getReceiptAddress(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="isDefault",required=false)Integer isDefault){
		Integer userId=getUserId(request);
		JSONObject jv = new JSONObject();
		jv.put("info",receiptAddressService.getReceiptAddress(isDefault, userId));
		Utils.writeBack(request, response, jv);
	}
}
