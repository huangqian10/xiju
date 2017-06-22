package com.xiyoukeji.xiju.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xiyoukeji.xiju.core.enums.Const;
import com.xiyoukeji.xiju.domain.PayMsg;
import com.xiyoukeji.xiju.service.ActivityService;
import com.xiyoukeji.xiju.service.AdminUserService;
import com.xiyoukeji.xiju.service.CartService;
import com.xiyoukeji.xiju.service.ChuanyiActivityService;
import com.xiyoukeji.xiju.service.ClientInfoService;
import com.xiyoukeji.xiju.service.CollectService;
import com.xiyoukeji.xiju.service.ColorService;
import com.xiyoukeji.xiju.service.CommentService;
import com.xiyoukeji.xiju.service.ContentService;
import com.xiyoukeji.xiju.service.FeedBackService;
import com.xiyoukeji.xiju.service.FileService;
import com.xiyoukeji.xiju.service.FaxianActivityService;
import com.xiyoukeji.xiju.service.GoodsColorStyleService;
import com.xiyoukeji.xiju.service.GoodsParamsService;
import com.xiyoukeji.xiju.service.GoodsSceneService;
import com.xiyoukeji.xiju.service.GoodsService;
import com.xiyoukeji.xiju.service.MessageService;
import com.xiyoukeji.xiju.service.ParamsService;
import com.xiyoukeji.xiju.service.PingppService;
import com.xiyoukeji.xiju.service.ReceiptAddressService;
import com.xiyoukeji.xiju.service.ReceiptJobService;
import com.xiyoukeji.xiju.service.ReceiptService;
import com.xiyoukeji.xiju.service.RedisCache;
import com.xiyoukeji.xiju.service.SceneService;
import com.xiyoukeji.xiju.service.SmsService;
import com.xiyoukeji.xiju.service.SpreadService;
import com.xiyoukeji.xiju.service.StyleService;
import com.xiyoukeji.xiju.service.StylistService;
import com.xiyoukeji.xiju.service.TypeService;
import com.xiyoukeji.xiju.service.UserMsgService;
import com.xiyoukeji.xiju.service.UserService;
import com.xiyoukeji.xiju.service.VoucherCodeService;
import com.xiyoukeji.xiju.service.VoucherService;
import com.xiyoukeji.xiju.service.WithdrawMoneyService;
import com.xiyoukeji.xiju.service.WithdrawalProportionService;
import com.xiyoukeji.xiju.service.UrlInfoService;
import com.xiyoukeji.xiju.service.PowerInfoService;
import com.xiyoukeji.xiju.service.PromotionCodeService;

public class BaseController {

	static Logger logger = Logger.getLogger(BaseController.class);
	
	@Autowired
	WithdrawalProportionService withdrawalProportionService;
	
	@Autowired
	ReceiptAddressService receiptAddressService;
	
	@Autowired
	PromotionCodeService promotionCodeService;
	
	@Autowired 
	WithdrawMoneyService withdrawMoneyService;
	
	@Autowired 
	StylistService stylistService;
	
	@Autowired
	RedisCache redisCache;

	@Autowired
	SmsService smsService;

	@Autowired
	UserService userService;

	@Autowired
	@Qualifier("fileService")
	FileService fileService;

	@Autowired 
	ActivityService activityService;

	@Autowired
	ReceiptService receiptService;

	@Autowired
	GoodsService goodsService;

	@Autowired
	FaxianActivityService faxianActivityService;

	@Autowired
	ChuanyiActivityService chuanyiActivityService;

	@Autowired
	CommentService commentService;

	@Autowired
	VoucherService voucherService;

	@Autowired
	ColorService colorService;

	@Autowired
	PingppService pingppService;

	@Autowired
	CollectService collectService;

	@Autowired
	CartService cartService;

	@Autowired
	ContentService contentService;

	@Autowired
	GoodsColorStyleService goodsColorStyleService;

	@Autowired
	GoodsParamsService goodsParamsService;

	@Autowired
	ReceiptJobService jobService;
	
	@Autowired
	GoodsSceneService goodsSceneService;
	
	@Autowired
	SceneService sceneService;
	
	@Autowired
	TypeService typeService;
	
	@Autowired
	FeedBackService feedBackService;
	
	@Autowired
	ParamsService paramsService;
	
	@Autowired
	StyleService styleService;
	
	@Autowired
	VoucherCodeService voucherCodeService;
	
	@Autowired
	AdminUserService adminUserService;
	
	@Autowired
	SpreadService spreadService;
	
	@Autowired
	MessageService messageService;
	
	@Autowired
	UserMsgService userMsgService;
	
	
	@Autowired
	UrlInfoService urlInfoService;
	
	@Autowired
	PowerInfoService powerInfoService;
	
	@Autowired
	ClientInfoService clientInfoService;

	public Integer getUserId(HttpServletRequest request) {
		Integer userId= (Integer)request.getSession().getAttribute(Const.LOGIN_USER_ID); 
		return userId;
	}

	public List<PayMsg> format(String jsonStr) {
		List<PayMsg> payMsgList = new ArrayList<PayMsg>();

		try {
			JSONObject json = JSONObject.parseObject(jsonStr);
			String resPayList = json.getString("payMsg");
			payMsgList=JSONArray.parseArray(resPayList, PayMsg.class);
			for(PayMsg payMsg:payMsgList){
				logger.info(payMsg.toString());
			}
		} catch (Exception e) {
			logger.info("error json="+jsonStr);
			return null;
		}
		return payMsgList;
	}
	public Integer[] StringtoInt(String str) {
		Integer ret[] = new Integer[str.split(";").length];
		StringTokenizer toKenizer = new StringTokenizer(str, ";");
		int i = 0;
		while (toKenizer.hasMoreElements()) {
			ret[i++] = Integer.valueOf(toKenizer.nextToken());
		}
		return ret;
	}
	
/*	public static void main(String[] args) {
		String json="{\"payMsg\":[{\"colorName\":\"white\",\"goodsStyle\":\"big\",\"goodsColor\":\"#FFFFFF\",\"goodsSmallUrl\":\"/xijufile/goods1-3.png\",\"goodsName\":\"title\",\"goodsId\":1,\"goodsColorStyleId\":2,\"goodsAmount\":1}]}";
		List<PayMsg> list =format(json);
		for(PayMsg payMsg:list){
			System.out.println(payMsg.toString());
		}
		
	}*/
}
