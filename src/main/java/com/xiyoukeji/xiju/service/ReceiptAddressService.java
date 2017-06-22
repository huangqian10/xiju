package com.xiyoukeji.xiju.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.xiyoukeji.xiju.core.dao.BaseHibernateDao;
import com.xiyoukeji.xiju.core.enums.IsDefault;
import com.xiyoukeji.xiju.model.ReceiptAddress;

@Service
public class ReceiptAddressService {
	
	Logger logger =Logger.getLogger(ReceiptAddressService.class);
	
	@Autowired
	@Qualifier("hibernateDao")
	BaseHibernateDao<ReceiptAddress, Integer> dao;
	
	@Transactional
	public void save(ReceiptAddress r){
		dao.save(r);
	}
	@Transactional
	public void update(ReceiptAddress r){
		dao.update(r);
	}
	@Transactional
	public void delete(Integer id){
		dao.delete(id, ReceiptAddress.class);
	}
	@Transactional
	public void cancelDefault(Integer userId){
		DetachedCriteria dc = DetachedCriteria.forClass(ReceiptAddress.class);
		dc.add(Restrictions.eq("userId", userId));
		dc.add(Restrictions.eq("isDefault", IsDefault.DEFAULT.value()));
		
		List<ReceiptAddress> res=dao.list(dc);
		logger.info(res.size());
		if(res!=null&&res.size()>0){
			ReceiptAddress receiptAddress=res.get(0);
			receiptAddress.setIsDefault(IsDefault.NODEFAULT.value());
			dao.update(receiptAddress);
		}
	}
	
	public ReceiptAddress getReceiptAddressById(Integer id,Integer userId){
		ReceiptAddress address=dao.get(id, ReceiptAddress.class);
		if(address!=null){
			if(address.getUserId().equals(userId)){
				return address;
			}
		}
		return null;
	}
	public List<ReceiptAddress> getReceiptAddress(Integer isDefault,Integer userId){
		DetachedCriteria dc = DetachedCriteria.forClass(ReceiptAddress.class);
		dc.add(Restrictions.eq("userId", userId));
		if(isDefault!=null&&isDefault==IsDefault.DEFAULT.value()){
			dc.add(Restrictions.eq("isDefault", isDefault));
		}
		return dao.list(dc);
	}
	@Transactional
	public void changeDefault(Integer userId){
		DetachedCriteria dc = DetachedCriteria.forClass(ReceiptAddress.class);
		dc.add(Restrictions.eq("userId", userId));
		List<ReceiptAddress> list =dao.list(dc);
		if(list!=null&&list.size()>0){
			ReceiptAddress r=list.get(0);
			r.setIsDefault(IsDefault.DEFAULT.value());
			dao.update(r);
		}
	}
}
