package com.xiyoukeji.xiju.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
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

	@Autowired
	UserService userService;

	@Transactional
	public void save(Feedback b) {
		dao.save(b);
	}

	public List<Feedback> getAll(Integer pageSize, Integer pageNo) {
		DetachedCriteria dc = DetachedCriteria.forClass(Feedback.class);
		dc.addOrder(Order.desc("id"));
		List<Feedback> list = this.dao.list(dc);
		List<Feedback> list2 = new ArrayList<Feedback>();
		if ((pageSize == null) || (pageNo == null)) {
			for (int i = 0; i < list.size(); i++) {
				Feedback f = (Feedback) list.get(i);
				f.setUsername(this.userService.getUserById(f.getUserId()).getUsername());
				list2.add(f);
			}
		} else {
			for (int i = pageSize.intValue() * pageNo.intValue(); (i < pageSize.intValue() * (pageNo.intValue() + 1))
					&& (i < list.size()); i++) {
				Feedback f = (Feedback) list.get(i);
				f.setUsername(this.userService.getUserById(f.getUserId()).getUsername());
				list2.add(f);
			}
		}
		return list2;
	}
}
