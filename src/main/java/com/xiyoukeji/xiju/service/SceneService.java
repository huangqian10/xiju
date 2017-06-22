package com.xiyoukeji.xiju.service;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.maven.artifact.versioning.Restriction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.xiyoukeji.xiju.core.dao.BaseHibernateDao;
import com.xiyoukeji.xiju.model.Scene;


@Service
public class SceneService extends BaseService{
	@Autowired
	@Qualifier("hibernateDao")
	BaseHibernateDao<Scene, Integer> dao;
	
	@Transactional
	public void save(Scene s){
		dao.save(s);
	}
	
	@Transactional
	public void update(Scene s){
		dao.update(s);
	}
	
	public List<Scene> getAll(){
		DetachedCriteria dc = DetachedCriteria.forClass(Scene.class);
		return dao.list(dc);
	}
	public Scene getById(Integer id){
		return dao.get(id, Scene.class);
	}
	
	public boolean isExistName(String scene){
		DetachedCriteria dc =DetachedCriteria.forClass(Scene.class);
		dc.add(Restrictions.eq("scene", scene));
		List<Scene> sceneList = dao.list(dc);
		if(sceneList!=null&&sceneList.size()>0){
			return true;
		}
		return false;
	}
}
