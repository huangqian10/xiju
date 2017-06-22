package com.xiyoukeji.xiju.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiyoukeji.xiju.core.dao.BaseHibernateDao;
import com.xiyoukeji.xiju.model.UrlInfo;

@Service
public class UrlInfoService extends BaseService{

	@Autowired
	@Qualifier("hibernateDao")
	BaseHibernateDao<UrlInfo, Integer> dao;
	
	@Transactional
	public void save(UrlInfo u){
		dao.save(u);
	}
	
	@Transactional
	public void update(UrlInfo u){
		dao.update(u);
	}
	
	public List<UrlInfo> getAll(){
		DetachedCriteria dc = DetachedCriteria.forClass(UrlInfo.class);
		return dao.list(dc);
	}
	
	public UrlInfo getUrlInfoByUri(String uri){
		DetachedCriteria dc =DetachedCriteria.forClass(UrlInfo.class);
		dc.add(Restrictions.eq("url", uri));
		List<UrlInfo> list =dao.list(dc);
		if(list!=null&list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	public UrlInfo getById(Integer id){
		return dao.get(id, UrlInfo.class);
	}
}
