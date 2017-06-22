package com.xiyoukeji.xiju.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiyoukeji.xiju.core.dao.BaseHibernateDao;
import com.xiyoukeji.xiju.model.Feedback;

@Service
public class FeedBackService {
	@Autowired
	@Qualifier("hibernateDao")
	BaseHibernateDao<Feedback, Integer> dao;
	
	@Transactional
	public void save(Feedback b){
		dao.save(b);
	}
}
