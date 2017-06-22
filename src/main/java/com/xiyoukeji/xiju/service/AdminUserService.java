package com.xiyoukeji.xiju.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiyoukeji.xiju.core.dao.BaseHibernateDao;
import com.xiyoukeji.xiju.model.AdminUser;

@Service
public class AdminUserService {

	@Autowired
	@Qualifier("hibernateDao")
	BaseHibernateDao<AdminUser, Integer> dao;
	
	@Transactional
	public void save(AdminUser user){
		dao.save(user);
	}
	
	@Transactional
	public void update(AdminUser user){
		dao.update(user);
	}
	
	public AdminUser judgeLogin(String userName,String password){
		DetachedCriteria dc =DetachedCriteria.forClass(AdminUser.class);
		System.out.println(userName+"||"+password);
		dc.add(Restrictions.eq("userName", userName));
		dc.add(Restrictions.eq("password", password));
		List<AdminUser> user = dao.list(dc);

		if(user!=null&&user.size()>0){
			return user.get(0);
		}
		return null;
	}
	public AdminUser getAdminUserById(Integer adminId){
		return dao.get(adminId, AdminUser.class);
	}
	
	public List<AdminUser> getAll(){
		DetachedCriteria dc = DetachedCriteria.forClass(AdminUser.class);
		return dao.list(dc);
	}
}
