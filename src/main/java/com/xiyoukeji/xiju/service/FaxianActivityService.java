package com.xiyoukeji.xiju.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.xiyoukeji.xiju.core.dao.BaseHibernateDao;
import com.xiyoukeji.xiju.model.FaxianActivity;

@Service
public class FaxianActivityService extends BaseService {
	@Autowired
	@Qualifier("hibernateDao")
	BaseHibernateDao<FaxianActivity, Integer> dao;
	
	@Transactional
	public void save(FaxianActivity m){
		dao.save(m);
	}
	
	@Transactional
	public void update(FaxianActivity m){
		dao.update(m);
	}
	
	public List<FaxianActivity> getAll(){
		DetachedCriteria dc =DetachedCriteria.forClass(FaxianActivity.class);
		return dao.list(dc);
	}
	
	public FaxianActivity getById(Integer id){
		return dao.get(id, FaxianActivity.class);
	}
	
}
