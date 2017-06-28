/**
 * 
 */
package com.xiyoukeji.xiju.service;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiyoukeji.xiju.core.dao.BaseHibernateDao;
import com.xiyoukeji.xiju.model.BankCard;



/**
 * @author hq
 *
 */
@Service
public class BankCardService {
	
	@Autowired
	@Qualifier("hibernateDao")
	BaseHibernateDao<BankCard, Integer> dao;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional
	public  void add(Integer userId,String owner,String openNumber,String openBank,String cellphone){
		BankCard bankCard=new BankCard();
		bankCard.setUserId(userId);
		bankCard.setOwner(owner);
		bankCard.setOpenNumber(openNumber);
		bankCard.setOpenBank(openBank);
		bankCard.setCellphone(cellphone);
		dao.save(bankCard);
	
	}
	
	
	@Transactional
	public  void change(Integer id,String owner,String openNumber,String openBank,String cellphone){
		BankCard bankCard=dao.get(id, BankCard.class);
		bankCard.setId(id);
		bankCard.setOwner(owner);
		bankCard.setOpenNumber(openNumber);
		bankCard.setOpenBank(openBank);
		bankCard.setCellphone(cellphone);
		dao.update(bankCard);
	
	}
	
	
	@Transactional
	public  void setDefault(Integer id,Integer isDefault){
		BankCard bankCard= dao.get(id, BankCard.class);
		
		if(isDefault==1){
			Session session = sessionFactory.getCurrentSession();
			String queryString="update BankCard set isDefault  =0 where userId="+bankCard.getUserId();
			session.createQuery(queryString);	
		}

		bankCard.setIsDefault(isDefault);

		dao.update(bankCard);
	
	}
	
	

	public  BankCard getById(Integer id){

		return  dao.get(id, BankCard.class);
	
	}
	
	public List<BankCard> getListByUserId(Integer userId){
		
	  return	dao.list(BankCard.class, "userId", userId);
	}
	
	@Transactional
	public void deleteById(Integer id){
		dao.delete(id, BankCard.class);
	}
	

}
