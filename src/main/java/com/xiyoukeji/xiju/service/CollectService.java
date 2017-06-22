package com.xiyoukeji.xiju.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.xiyoukeji.xiju.core.dao.BaseHibernateDao;
import com.xiyoukeji.xiju.model.Collect;


@Service
public class CollectService {
	@Autowired
	@Qualifier("hibernateDao")
	BaseHibernateDao<Collect, Integer> dao;
	
	@Transactional
	public void save(Collect c){
		dao.save(c);
	}
	
	
	@Transactional
	public void update(Collect c){
		dao.update(c);
	}
	
	@Transactional
	public void del(Integer id){
		dao.delete(id, Collect.class);
	}
	
	public List<Collect> getByUserId(Integer userId){
		DetachedCriteria dc = DetachedCriteria.forClass(Collect.class);
		dc.add(Restrictions.eq("userId", userId));
		return dao.list(dc);
	}
	
	public Collect getByUserIdAndGoodsId(Integer userId,Integer goodsId){
		DetachedCriteria dc =DetachedCriteria.forClass(Collect.class);
		dc.add(Restrictions.eq("userId", userId));
		dc.add(Restrictions.eq("goodsId", goodsId));
		List<Collect> list =dao.list(dc);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
}
