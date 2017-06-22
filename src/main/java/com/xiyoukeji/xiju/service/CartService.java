package com.xiyoukeji.xiju.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiyoukeji.xiju.core.dao.BaseHibernateDao;
import com.xiyoukeji.xiju.model.Cart;

@Service
public class CartService {
	@Autowired
	@Qualifier("hibernateDao")
	BaseHibernateDao<Cart, Integer> dao;
	
	@Transactional
	public void save(Cart c){
		dao.save(c);
	}
	
	@Transactional
	public void delete(Integer id){
		dao.delete(id, Cart.class);
	}
	
	@Transactional
	public void update(Cart t){
		dao.update(t);
	}
	
	public List<Cart> getByUserId(Integer userId){
		DetachedCriteria dc =DetachedCriteria.forClass(Cart.class);
		dc.add(Restrictions.eq("userId", userId));
		dc.addOrder(Order.desc("id"));
		return dao.list(dc);
	}
	
	public boolean isCart(Integer userId,Integer id){
		Cart cart =dao.get(id, Cart.class);
		if(cart!=null){
			if(cart.getUserId().equals(userId)){
				return true;
			}
			return false;
		}
		return false;
	}
	
	
}
