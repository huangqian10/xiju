package com.xiyoukeji.xiju.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiyoukeji.xiju.core.dao.BaseHibernateDao;
import com.xiyoukeji.xiju.model.Style;

@Service
public class StyleService extends BaseService {
	@Autowired
	@Qualifier("hibernateDao")
	BaseHibernateDao<Style, Integer> dao;
	
	@Transactional
	public void save(Style style){
		dao.save(style);
	}
	
	@Transactional
	public void update(Style style){
		dao.update(style);
	}
	
	@Transactional
	public void del(Integer id){
		dao.delete(id, Style.class);
	}
	
	public List<Style> getAll(){
		DetachedCriteria dc =DetachedCriteria.forClass(Style.class);
		return dao.list(dc);
	}
	
	public List<Style> getStyleByIds(String ids){
		Integer id[]=StringtoInt(ids);
		DetachedCriteria dc = DetachedCriteria.forClass(Style.class);
		dc.add(Restrictions.in("id", id));
		return dao.list(dc);
	}
	
	public String getNameById(Integer id){
		Style style =dao.get(id, Style.class);
		if(style!=null){
			return style.getName();
		}
		return null;
	}
	
	public Style getById(Integer id){
		return dao.get(id, Style.class);
	}
	
	public boolean isExistName(String name,Integer id){
		DetachedCriteria dc =DetachedCriteria.forClass(Style.class);
		dc.add(Restrictions.eq("name", name));
		List<Style> l=dao.list(dc);
		if(l!=null&&l.size()>0){
			if(id==null){
				return true;
			}
			Style style = l.get(0);
			if(!style.getId().equals(id)){
				return true;
			}
		}
		return false;
	}
}