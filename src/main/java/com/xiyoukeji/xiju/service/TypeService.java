package com.xiyoukeji.xiju.service;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.xiyoukeji.xiju.core.dao.BaseHibernateDao;
import com.xiyoukeji.xiju.model.Type;

@Service
public class TypeService {
	@Autowired
	@Qualifier("hibernateDao")
	BaseHibernateDao<Type,Integer> dao ;
	
	@Transactional
	public void save(Type t){
		dao.save(t);
	}
	
	@Transactional
	public void update(Type t){
		dao.update(t);
	}
	
	public Type getById(Integer id){
		return dao.get(id, Type.class);
	}
	public List<Type> getAll(){
		DetachedCriteria dc = DetachedCriteria.forClass(Type.class);
		return dao.list(dc);
	}
	
	public boolean isExistTypeName(String typeName,Integer id){
		DetachedCriteria dc = DetachedCriteria.forClass(Type.class);
		dc.add(Restrictions.eq("typeName", typeName));
		List<Type> typeList=dao.list(dc);
		if(typeList!=null&&typeList.size()>0){
			if(id==null){
				return true;
			}
			Type type =typeList.get(0);
			if(!type.getId().equals(id)){
				return true;
			}
		}
		return false;
	}
}
