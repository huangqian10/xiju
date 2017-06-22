package com.xiyoukeji.xiju.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.xiyoukeji.xiju.core.dao.BaseHibernateDao;
import com.xiyoukeji.xiju.core.enums.VoucherState;
import com.xiyoukeji.xiju.model.Voucher;

@Service
public class VoucherService {
	@Autowired
	@Qualifier("hibernateDao")
	BaseHibernateDao<Voucher, Integer> dao;
	
	@Transactional
	public void save(Voucher v){
		dao.save(v);
	}
	
	
	@Transactional
	public void update(Voucher v){
		dao.saveOrUpdate(v);
	}
	
	
	@Transactional
	public void uselessVoucher(Integer voucherId){
		Voucher voucher =dao.get(voucherId, Voucher.class);
		voucher.setVoucherStatus(VoucherState.USED.value());
		dao.update(voucher);
	}
	public Voucher getVoucherById(Integer voucherId){
		return dao.get(voucherId, Voucher.class);
	}
	
	
	public List<Voucher> getVoucherByUserId(Integer userId){
		DetachedCriteria dc =DetachedCriteria.forClass(Voucher.class);
		dc.add(Restrictions.eq("userId", userId));
		return dao.list(dc);
	}
	
	public boolean hasValidate(Integer userId,Integer voucherCodeId){
		DetachedCriteria dc = DetachedCriteria.forClass(Voucher.class);
		dc.add(Restrictions.eq("userId", userId));
		dc.add(Restrictions.eq("voucherCodeId", voucherCodeId));
		List<Voucher> voucherList=dao.list(dc);
		if(voucherList!=null&&voucherList.size()>0){
			return true;
		}
		return false;
	}
}
