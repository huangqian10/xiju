package com.xiyoukeji.xiju.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.transaction.annotation.Transactional;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.xiyoukeji.xiju.core.dao.BaseHibernateDao;
import com.xiyoukeji.xiju.core.enums.ReceiptStatus;
import com.xiyoukeji.xiju.model.Receipt;

@Service
public class ReceiptService {
	@Autowired
	@Qualifier("hibernateDao")
	BaseHibernateDao<Receipt, Integer> dao;
	
	
	
	@Transactional
	public void save(Receipt r){
		dao.save(r);
	}
	
	@Transactional
	public void update(Receipt r){
		dao.update(r);
	}
	
	@Transactional(readOnly = true)
	public Receipt getReceiptById(Integer id){
		return dao.get(id, Receipt.class);
	}
	
	@Transactional
	public void del(Integer id){
		dao.delete(id, Receipt.class);
	}
	
	public List<Receipt> getMyReceipt(Integer userId){
		DetachedCriteria dc = DetachedCriteria.forClass(Receipt.class);
		dc.add(Restrictions.eq("userId", userId));
		return dao.list(dc);
	}
	
	public List<Receipt> getAll(Integer payStatus){
		DetachedCriteria dc = DetachedCriteria.forClass(Receipt.class);
		if(payStatus!=null){
			dc.add(Restrictions.eq("status", payStatus));
		}
		return dao.list(dc);
	}
	@Transactional
	public List<Receipt> getNotPaidReceipt(){
		DetachedCriteria dc = DetachedCriteria.forClass(Receipt.class);
		dc.add(Restrictions.eq("status", ReceiptStatus.NOTPAY.value()));
		return dao.list(dc);
	}
}
