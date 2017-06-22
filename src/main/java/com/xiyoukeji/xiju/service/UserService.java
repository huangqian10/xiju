package com.xiyoukeji.xiju.service;

import java.awt.image.RescaleOp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.transaction.annotation.Transactional;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiyoukeji.xiju.core.dao.BaseHibernateDao;
import com.xiyoukeji.xiju.model.User;

@Service
public class UserService {

	@Autowired
	BaseHibernateDao<User, Integer> dao;

	public boolean isExist(String mobile) {
		DetachedCriteria dc = DetachedCriteria.forClass(User.class);
		dc.add(Restrictions.eq("mobile", mobile));
		List<User> resList = dao.list(dc);
		return resList.size() > 0;
	}
	
	@Transactional
	public void save(User u ){
		dao.save(u);
	}
	
	public User judgeLogin(String mobile,String password){
		DetachedCriteria dc = DetachedCriteria.forClass(User.class);
		dc.add(Restrictions.eq("mobile", mobile));
		dc.add(Restrictions.eq("password", password));
		List<User> resList = dao.list(dc);
		return resList.size() > 0?resList.get(0):null;
	}
	
	public User getUserById(Integer id){
		return dao.get(id, User.class);
	}
	
	public User getByMobile(String mobile){
		DetachedCriteria dc = DetachedCriteria.forClass(User.class);
		dc.add(Restrictions.eq("mobile", mobile));
		List<User> resList =dao.list(dc);
		if(resList!=null&&resList.size()>0){
			return resList.get(0);
		}
		return null;
	}
	
	
	public User getByUid(String uid){
		DetachedCriteria dc =DetachedCriteria.forClass(User.class);
		dc.add(Restrictions.eq("uid", uid));
		List<User> list = dao.list(dc);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
	public List<User> getAll(){
		DetachedCriteria dc = DetachedCriteria.forClass(User.class);
		return dao.list(dc);
	}
	
	@Transactional
	public void update(User u){
		dao.update(u);
	}
}
