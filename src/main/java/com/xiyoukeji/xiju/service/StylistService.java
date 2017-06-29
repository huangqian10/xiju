/**
 * 
 */
package com.xiyoukeji.xiju.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xiyoukeji.xiju.core.dao.BaseHibernateDao;
import com.xiyoukeji.xiju.core.enums.ReceiptStatus;
import com.xiyoukeji.xiju.core.enums.StylistReceiptStatus;
import com.xiyoukeji.xiju.model.PowerInfo;
import com.xiyoukeji.xiju.model.Receipt;
import com.xiyoukeji.xiju.model.Stylist;
import com.xiyoukeji.xiju.model.UrlInfo;

/**
 * @author hq
 *
 */
@Service
public class StylistService {
	
	@Autowired
	@Qualifier("hibernateDao")
	BaseHibernateDao<Stylist, Integer> dao;
	
	@Autowired
	@Qualifier("hibernateDao")
	BaseHibernateDao<Receipt, Integer> receiptDao;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional
	public void authentication(Integer userId,String stylistCardUrl,String stylistCertificateUrl,String businessLicenseUrl){
		Stylist stylist=new Stylist();
		stylist.setUserId(userId);
		stylist.setBusinessLicenseUrl(businessLicenseUrl);
		stylist.setStylistCardUrl(stylistCardUrl);
		stylist.setStylistCertificateUrl(stylistCertificateUrl);
		DetachedCriteria dc = DetachedCriteria.forClass(Stylist.class);
		dc.add(Restrictions.eq("userId", userId));
		if(dao.list(dc).isEmpty()){
			dao.save(stylist);
		}else{
			stylist=(Stylist)dao.list(dc).get(0);
			stylist.setBusinessLicenseUrl(businessLicenseUrl);
			stylist.setStylistCardUrl(stylistCardUrl);
			stylist.setStylistCertificateUrl(stylistCertificateUrl);
			dao.update(stylist);
		}
	}
	
	
	@Transactional
	public Stylist getStylistByUserId(Integer userId){
		DetachedCriteria dc = DetachedCriteria.forClass(Stylist.class);
		dc.add(Restrictions.eq("userId", userId));
		
		return (Stylist)dao.list(dc).get(0);
	}
	
	
	@Transactional
	public List<Map<String, Object>> getReceiptListByUserId(Integer userId){
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		Session session = this.sessionFactory.getCurrentSession();
		String queryString="from Receipt as r where r.PromotionCode.userId="+userId;
		List<Receipt> receiptList=session.createQuery(queryString).list();
	
		int totalMoney=0;
		for(Receipt r:receiptList){
			Map<String, Object> map=new HashMap<String, Object>();
			if(r.getStatus()==ReceiptStatus.NOTPAY.value()||r.getStatus()==ReceiptStatus.PAID.value()){
				JSONObject jsonObject=JSONObject.parseObject(r.getPayJson());
				JSONArray jsonArray=jsonObject.getJSONArray("payMsg");
				totalMoney+=getPrice(jsonArray);
				map.put("totalMoney", totalMoney);
				map.put("createTime", r.getCreateTime());
				map.put("id", r.getId());
				if(r.getStatus()==ReceiptStatus.NOTPAY.value()){
					map.put("status", StylistReceiptStatus.RECEIVING.value());
				}else if(r.getStatus()==ReceiptStatus.PAID.value()){
					map.put("status", StylistReceiptStatus.COMPLETE.value());
				}
				list.add(map);
			}
		}
		return list;
	}
	
	
	

	
	@Transactional
	public Map<String, Object> getStylistInfoByUserId(Integer userId){
		Map<String, Object> map=new HashMap<String, Object>();
		Session session = this.sessionFactory.getCurrentSession();
	
		String queryString="from Receipt as r where r.PromotionCode.userId="+userId;
		List<Receipt> list=session.createQuery(queryString).list();

		int totalMoney=0;
		int withdrawMoney=0;
		for(Receipt r:list){

			JSONObject jsonObject=JSONObject.parseObject(r.getPayJson());
			JSONArray jsonArray=jsonObject.getJSONArray("payMsg");
			
			if(r.getStatus()==ReceiptStatus.NOTPAY.value()){
				withdrawMoney+=	getPrice(jsonArray);
				totalMoney+=getPrice(jsonArray);
			}else if(r.getStatus()==ReceiptStatus.PAID.value()){
				totalMoney+=getPrice(jsonArray);
			}
			     
		}
		map.put("totalMoney", totalMoney);
		map.put("withdrawMoney", withdrawMoney);
		map.put("Stylist", getStylistByUserId(userId));

		return map;
	}
	
	
	public int getPrice(JSONArray jsonArray){
		Iterator<?> it=jsonArray.iterator();
		int money=0;
		while(it.hasNext()){
			money +=		JSONObject.parseObject(it.next()+"").getInteger("price");
		}
	
		return money;
	}
	
	
	@Transactional
	public void changeStylistStatus(Integer userId,Integer status){
		DetachedCriteria dc = DetachedCriteria.forClass(Stylist.class);
		dc.add(Restrictions.eq("userId", userId));
		if(!dao.list(dc).isEmpty()){
		Stylist stylist=(Stylist)dao.list(dc).get(0);
		stylist.setCertificationStatus(status);
		dao.update(stylist);
		}
	}
	
	
	


}
