package com.xiyoukeji.xiju.service;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.xiyoukeji.xiju.core.dao.BaseHibernateDao;
import com.xiyoukeji.xiju.model.Message;

@Service
public class MessageService {
	@Autowired
	@Qualifier("hibernateDao")
	BaseHibernateDao<Message, Integer> dao;
	
	
	@Transactional
	public void save(Message message){
		dao.save(message);
	}
	@Transactional
	public void update(Message m){
		dao.update(m);
	}
	
	public List<Message> getAll(){
		DetachedCriteria dc =DetachedCriteria.forClass(Message.class);
		return dao.list(dc);
	}
	
	public Message getById(Integer id){
		return dao.get(id, Message.class);
	}
}
