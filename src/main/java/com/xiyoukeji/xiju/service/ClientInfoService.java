package com.xiyoukeji.xiju.service;

import javax.transaction.Transactional;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.xiyoukeji.xiju.core.dao.BaseHibernateDao;
import com.xiyoukeji.xiju.model.ClientInfo;

@Service
public class ClientInfoService {
	@Autowired
	@Qualifier("hibernateDao")
	BaseHibernateDao<ClientInfo, Integer> dao;
	
	@Transactional
	public void update(ClientInfo clientInfo){
		dao.update(clientInfo);
	}
	
	public ClientInfo getInfo(){
		DetachedCriteria dc = DetachedCriteria.forClass(ClientInfo.class);
		return (ClientInfo) dao.list(dc).get(0);
	}
	
}
