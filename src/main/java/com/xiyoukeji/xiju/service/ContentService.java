package com.xiyoukeji.xiju.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiyoukeji.xiju.core.dao.BaseHibernateDao;
import com.xiyoukeji.xiju.model.Content;

@Service
public class ContentService extends BaseService{
	@Autowired
	@Qualifier("hibernateDao")
	BaseHibernateDao<Content, Integer> dao;
	
	@Transactional
	public void save(Content c){
		dao.save(c);
	}
	
	@Transactional
	public void update(Content c){
		dao.update(c);
	}
	
	@Transactional
	public void del(Integer id){
		dao.delete(id, Content.class);
	}
	
	public Content getById(Integer id){
		return dao.get(id, Content.class);
	}
	public List<Content> getByIds(String ids){
		Integer[] id=StringtoInt(ids);
		DetachedCriteria dc =DetachedCriteria.forClass(Content.class);
		dc.add(Restrictions.in("id", id));
		return dao.list(dc);
	}
}
