package com.xiyoukeji.xiju.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.xiyoukeji.xiju.core.dao.BaseHibernateDao;
import com.xiyoukeji.xiju.model.Activity;
import com.xiyoukeji.xiju.model.GoodsColorStyle;
import com.xiyoukeji.xiju.model.GoodsScene;

@Service
public class GoodsSceneService {
	@Autowired
	@Qualifier("hibernateDao")
	BaseHibernateDao<GoodsScene, Integer> dao;

	@Transactional
	public void save(GoodsScene g) {
		dao.save(g);
	}

	@Transactional
	public void update(GoodsScene g) {
		dao.update(g);
	}
	
	@Transactional
	public void del(GoodsScene g){
		dao.delete(g.getId(),GoodsScene.class);
	}

	public List<GoodsScene> getBySceneId(Integer sceneId) {
		DetachedCriteria dc = DetachedCriteria.forClass(GoodsScene.class);
		dc.add(Restrictions.eq("sceneId", sceneId));
		return dao.list(dc);
	}

	public List<Integer> getBySceneId(Integer sceneId, List<Integer> list1) {
		DetachedCriteria dc = DetachedCriteria.forClass(GoodsScene.class);
		dc.add(Restrictions.eq("sceneId", sceneId));
		List<GoodsScene> dbRes = dao.list(dc);
		List<Integer> list2 = new ArrayList<Integer>();
		for (GoodsScene goodsScene : dbRes) {
			list2.add(goodsScene.getGoodsId());
		}
		if (list1 != null) {
			list1.retainAll(list2);
			return list1;
		}
		return list2;
	}

	public List<GoodsScene> getByGoodsId(Integer goodsId) {
		DetachedCriteria dc = DetachedCriteria.forClass(GoodsScene.class);
		dc.add(Restrictions.eq("goodsId", goodsId));
		return dao.list(dc);
	}
}
