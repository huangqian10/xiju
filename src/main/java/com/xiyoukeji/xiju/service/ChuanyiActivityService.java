package com.xiyoukeji.xiju.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.xiyoukeji.xiju.core.dao.BaseHibernateDao;
import com.xiyoukeji.xiju.model.ChuanyiActivity;

@Service
public class ChuanyiActivityService {
	@Autowired
	@Qualifier("hibernateDao")
	BaseHibernateDao<ChuanyiActivity, Integer> dao;

	@Transactional
	public void save(ChuanyiActivity c) {
		dao.save(c);
	}

	@Transactional
	public void update(ChuanyiActivity c) {
		dao.save(c);
	}

	public ChuanyiActivity getById(Integer id) {
		return dao.get(id, ChuanyiActivity.class);
	}

	public List<ChuanyiActivity> getAll() {
		DetachedCriteria dc = DetachedCriteria.forClass(ChuanyiActivity.class);
		return dao.list(dc);
	}

	public boolean isExistName(String name, Integer id) {
		DetachedCriteria dc = DetachedCriteria.forClass(ChuanyiActivity.class);
		dc.add(Restrictions.eq("chuanyiName", name));
		List<ChuanyiActivity> l = dao.list(dc);
		if (l != null && l.size() > 0) {
			if (id == null) {
				return true;
			}
			ChuanyiActivity c = l.get(0);
			if (!c.getId().equals(id)) {
				return true;
			}
		}
		return false;
	}
}
