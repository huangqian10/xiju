package com.xiyoukeji.xiju.controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.xiyoukeji.xiju.core.enums.Const;
import com.xiyoukeji.xiju.core.enums.VoucherState;
import com.xiyoukeji.xiju.core.utils.Utils;
import com.xiyoukeji.xiju.model.Voucher;
import com.xiyoukeji.xiju.model.VoucherCode;

@Controller
public class VoucherCodeController extends BaseController {
	@RequestMapping("/user/validateCode.do")
	public void validateCode(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "code", required = true) String code) {
		JSONObject jv = new JSONObject();
		Integer userId = getUserId(request);
		VoucherCode voucherCode = voucherCodeService.getVoucherCodeByCode(code);
		if (voucherCode != null) {
			if (voucherService.hasValidate(userId, voucherCode.getId())) {// 已兑换
				jv.put("code", Const.HAS_VALIDATE);
			} else {
				Voucher voucher = new Voucher();
				voucher.setUserId(userId);
				voucher.setVoucherAmount(voucherCode.getVoucherAmount());
				voucher.setVoucherBeginTime(voucherCode.getVoucherBeginTime());
				voucher.setVoucherCodeId(voucherCode.getId());
				voucher.setVoucherEndTime(voucherCode.getVoucherEndTime());
				voucher.setVoucherLimit(voucherCode.getVoucherLimit());
				voucher.setVoucherName(voucherCode.getVoucherName());
				voucher.setVoucherStatus(VoucherState.USEFUL.value());
				voucherService.save(voucher);
				jv.put("code", Const.SUCCESS);
			}
		} else {
			jv.put("code", Const.NO_SUCH_VOUCHERCODE);
		}
		Utils.writeBack(request, response, jv);
	}

	@RequestMapping("/admin/getVoucherCodeList.do")
	public void getVoucherCodeList(HttpServletResponse response,
			HttpServletRequest request) {
		JSONObject jv = new JSONObject();
		jv.put("info", voucherCodeService.getAll());
		Utils.writeBack(request, response, jv);
	}

	@RequestMapping("/admin/saveOrUpdateVoucherCode.do")
	public void saveOrUpdateVoucherCode(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "voucherAmount", required = true) Integer voucherAmount,
			@RequestParam(value = "voucherLimit", required = true) Integer voucherLimit,
			@RequestParam(value = "voucherBeginTime", required = true) Integer voucherBeginTime,
			@RequestParam(value = "voucherEndTime", required = true) Integer voucherEndTime,
			@RequestParam(value = "voucherName", required = true) String voucherName,
			@RequestParam(value = "codeBeginTime", required = true) Integer codeBeginTime,
			@RequestParam(value = "codeEndTime", required = true) Integer codeEndTime,
			@RequestParam(value = "codeContent", required = true) String codeContent,
			@RequestParam(value = "id", required = false) Integer id) {
		JSONObject jv = new JSONObject();
		VoucherCode voucherCode = voucherCodeService
				.getVoucherCodeByCode(codeContent);
		if (voucherCode == null
				|| (id != null && voucherCode.getId().equals(id))) {
			if (id != null) {
				voucherCode.setCodeBeginTime(codeBeginTime);
				voucherCode.setCodeContent(codeContent);
				voucherCode.setCodeEndTime(codeEndTime);
				voucherCode.setVoucherAmount(voucherAmount);
				voucherCode.setVoucherBeginTime(voucherBeginTime);
				voucherCode.setVoucherEndTime(voucherEndTime);
				voucherCode.setVoucherLimit(voucherLimit);
				voucherCode.setVoucherName(voucherName);
				voucherCode.setId(id);
				voucherCodeService.update(voucherCode);
			} else {
				voucherCode = new VoucherCode();
				voucherCode.setCodeBeginTime(codeBeginTime);
				voucherCode.setCodeContent(codeContent);
				voucherCode.setCodeEndTime(codeEndTime);
				voucherCode.setVoucherAmount(voucherAmount);
				voucherCode.setVoucherBeginTime(voucherBeginTime);
				voucherCode.setVoucherEndTime(voucherEndTime);
				voucherCode.setVoucherLimit(voucherLimit);
				voucherCode.setVoucherName(voucherName);
				voucherCodeService.save(voucherCode);
				jv.put("id", voucherCode.getId());
			}
			jv.put("code", Const.SUCCESS);

		} else {
			jv.put("code", Const.EXIST_CODE);
		}
		Utils.writeBack(request, response, jv);
	}
}
