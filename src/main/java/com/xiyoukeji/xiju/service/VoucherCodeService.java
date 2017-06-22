package com.xiyoukeji.xiju.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiyoukeji.xiju.core.dao.BaseHibernateDao;
import com.xiyoukeji.xiju.model.VoucherCode;


@Service
public class VoucherCodeService {

	@Autowired
	@Qualifier("hibernateDao")
	BaseHibernateDao<VoucherCode, Integer> dao;
	
	@Transactional
	public void save(VoucherCode code){
		dao.save(code);
	}
	
	@Transactional
	public void update(VoucherCode code){
		dao.update(code);
	}
	
	public boolean isExistCodeContent(String codeContent){
		DetachedCriteria dc =DetachedCriteria.forClass(VoucherCode.class);
		dc.add(Restrictions.eq("codeContent", codeContent));
		List<VoucherCode> list =dao.list(dc);
		if(list!=null&&list.size()>0){
			return true;
		}
		return false;
	}
	
	public VoucherCode getVoucherCodeByCode(String codeContent){
		DetachedCriteria dc =DetachedCriteria.forClass(VoucherCode.class);
		dc.add(Restrictions.eq("codeContent", codeContent));
		List<VoucherCode> list =dao.list(dc);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	public List<VoucherCode> getAll(){
		DetachedCriteria dc = DetachedCriteria.forClass(VoucherCode.class);
		return dao.list(dc);
	}
	
}
