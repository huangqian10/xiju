package com.xiyoukeji.xiju.service;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.xiyoukeji.xiju.core.dao.BaseHibernateDao;
import com.xiyoukeji.xiju.model.Spread;

@Service
public class SpreadService {
	@Autowired
	@Qualifier("hibernateDao")
	BaseHibernateDao<Spread, Integer> dao;

	@Transactional
	public void save(Spread s) {
		dao.save(s);
	}

	@Transactional
	public void update(Spread s) {
		dao.save(s);
	}

	public Spread getById(Integer id) {
		return dao.get(id, Spread.class);
	}

	public Spread getSpread() {
		DetachedCriteria dc = DetachedCriteria.forClass(Spread.class);
		List<Spread> list = dao.list(dc);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

}
