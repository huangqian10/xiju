/**
 * 
 */
package com.xiyoukeji.xiju.service;

import java.util.List;
import java.util.UUID;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.xiyoukeji.xiju.core.dao.BaseHibernateDao;
import com.xiyoukeji.xiju.core.enums.Const;
import com.xiyoukeji.xiju.core.enums.PromotionCodeEnum;
import com.xiyoukeji.xiju.model.PromotionCode;
import com.xiyoukeji.xiju.model.Stylist;
import com.xiyoukeji.xiju.model.User;

/**
 * @author hq
 *
 */
@Service
public class PromotionCodeService {
	@Autowired
	@Qualifier("hibernateDao")
	BaseHibernateDao<PromotionCode, Integer> dao;
	
	@Autowired
	BaseHibernateDao<User, Integer> userDao;
	
	@Transactional
	public JSONObject addPromotionCode(Integer userId,String name,String cellphone,String address,
			String community){
		JSONObject jv= new JSONObject();
		long time=System.currentTimeMillis();
		DetachedCriteria dc=DetachedCriteria.forClass(User.class);
		dc.add(Restrictions.eq("mobile", cellphone));
		if(userDao.list(dc).isEmpty()){
			jv.put("code", Const.NO_SUCH_CELLPHONE);
			return jv;
		}
		
		
		 dc = DetachedCriteria.forClass(PromotionCode.class);
		dc.add(Restrictions.eq("status", PromotionCodeEnum.UNUSED.value())).add(Restrictions.gt("endTime", time));
		if(dao.list(dc).isEmpty()){
			PromotionCode promotionCode=new PromotionCode();
			promotionCode.setUserId(userId);
			promotionCode.setName(name);
			promotionCode.setCellphone(cellphone);
			promotionCode.setAddress(address);
			promotionCode.setCommunity(community);
			promotionCode.setStartTime(time);
			promotionCode.setStatus(PromotionCodeEnum.UNUSED.value());
		
			promotionCode.setPromotionCode(UUID.randomUUID().toString());
			promotionCode.setEndTime(time+1000*60*60*24*10);
			dao.save(promotionCode);
			jv.put("code", Const.SUCCESS);
			jv.put("info", promotionCode.getId());
			return jv;
		}else{
			jv.put("code", Const.PROMOTIONCODE_EXIST);
			return jv;
		}	
	}
	
	
		@Transactional
		public PromotionCode usecPromotionCode(String  promotionCode){
			long time=System.currentTimeMillis();
			DetachedCriteria dc=DetachedCriteria.forClass(PromotionCode.class);
			dc.add(Restrictions.eq("promotionCode", promotionCode))
			.add(Restrictions.gt("endTime", time))
			.add(Restrictions.eq("status", PromotionCodeEnum.UNUSED.value()));
			if(dao.list(dc).isEmpty()){
			return	 null;
			}else{
				PromotionCode  promotion=(PromotionCode)	dao.list(dc).get(0);	
						promotion.setStatus(2);;
						dao.update(promotion);
						return promotion;
			}
	
	}
		
	
		
		
		public	Integer valiPromotionCode(String  promotionCode){
			long time=System.currentTimeMillis();
			DetachedCriteria dc=DetachedCriteria.forClass(PromotionCode.class);
			dc.add(Restrictions.eq("promotionCode", promotionCode))
			.add(Restrictions.gt("endTime", time))
			.add(Restrictions.eq("status", PromotionCodeEnum.UNUSED.value()));
			if(dao.list(dc).isEmpty()){
				return	 Const.INVALID_CODE;
				}else{
					return Const.SUCCESS;
				}
		}
		
		@Transactional
		public List<PromotionCode> getPromotionCodeRecord(Integer userId){
			long time=System.currentTimeMillis();
			DetachedCriteria dc=DetachedCriteria.forClass(PromotionCode.class);
			dc.addOrder(Order.desc("ctime"));
			List<PromotionCode>  list=dao.list(dc);
			for(PromotionCode p:list){
				if(p.getStatus()==PromotionCodeEnum.UNUSED.value()){
					if(p.getEndTime()<time){
						p.setStatus(PromotionCodeEnum.OVERDUE.value());
						}
					}
			}
			
		
			return list;
		}
}
