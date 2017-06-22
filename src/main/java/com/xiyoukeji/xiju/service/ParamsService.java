package com.xiyoukeji.xiju.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiyoukeji.xiju.core.dao.BaseHibernateDao;
import com.xiyoukeji.xiju.model.Params;

@Service
public class ParamsService extends BaseService{
	@Autowired
	@Qualifier("hibernateDao")
	BaseHibernateDao<Params, Integer> dao;
	
	@Transactional
	public void save(Params params){
		dao.save(params);
	}
	
	@Transactional
	public void update(Params params){
		dao.saveOrUpdate(params);
	}
	
	@Transactional
	public void del(Integer id){
		dao.delete(id, Params.class);
	}
	public List<Params> getParamsByIds(String ids){
		Integer [] id =StringtoInt(ids);
		DetachedCriteria dc =DetachedCriteria.forClass(Params.class);
		dc.add(Restrictions.in("id", id));
		return dao.list(dc);
	}
	public Params getParamsById(Integer id){
		return dao.get(id, Params.class);
	}
	
	public boolean IsExistParamsName(String name,Integer id){
		DetachedCriteria dc =DetachedCriteria.forClass(Params.class);
		dc.add(Restrictions.eq("name", name));
		List<Params> parms =dao.list(dc);
		if(parms!=null&&parms.size()>0){
			if(id==null){
				return true;
			}
			Params params =parms.get(0);
			if(!params.getId().equals(id)){
				return true;
			}
		}
		return false;
	}
	
	public List<Params> getAll(){
		DetachedCriteria dc = DetachedCriteria.forClass(Params.class);
		return dao.list(dc);
	}
	
}
