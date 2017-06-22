package com.xiyoukeji.xiju.service;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.xiyoukeji.xiju.core.dao.BaseHibernateDao;
import com.xiyoukeji.xiju.core.utils.Utils;
import com.xiyoukeji.xiju.model.Message;
import com.xiyoukeji.xiju.model.UserMsg;

@Service
public class UserMsgService {
	
	@Autowired
	@Qualifier("hibernateDao")
	BaseHibernateDao<UserMsg, Integer> dao;
	
	@Transactional
	public void save(Message message,Integer userId){
		UserMsg msg = new UserMsg();
		int nowTime=(int)(System.currentTimeMillis()/1000);
		msg.setContent(message.getContent());
		msg.setCreateTime(nowTime);
		msg.setGoodsName(message.getGoodsName());
		msg.setMsgId(message.getId());
		msg.setStatus(UserMsg.UNREAD);
		msg.setTitle(message.getTitle());
		msg.setTurnId(message.getTurnId());
		msg.setType(message.getType());
		msg.setUserId(userId);
		dao.save(msg);
	}
	
	@Transactional
	public void update(UserMsg u){
		dao.update(u);
	}
	
	public List<UserMsg> getUserMsgByUserId(Integer userId){
		DetachedCriteria dc = DetachedCriteria.forClass(UserMsg.class);
		dc.add(Restrictions.eq("userId", userId));
		return dao.list(dc);
	}
	
	public UserMsg getById(Integer id){
		return dao.get(id, UserMsg.class);
		
	}
}
