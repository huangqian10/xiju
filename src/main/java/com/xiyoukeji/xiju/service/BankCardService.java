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
	
	/**
	 * 添加银行卡
	 * @Title: add 
	 * @param userId  用户id
	 * @param owner   开户人
	 * @param openNumber  开户号码
	 * @param openBank    开户银行
	 * @param cellphone   手机号
	 * @throws
	 */
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
	
	
	/**
	 * 修改银行卡
	 * @Title: change 
	 * @param id     银行卡id
	 * @param owner   开户人
	 * @param openNumber  开户号码
	 * @param openBank    开户银行
	 * @param cellphone   手机号
	 * @throws
	 */
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
	
	/**
	 * 设置银行卡是否默认
	 * @Title: setDefault 
	 * @param id        
	 * @param isDefault  0为不默认，1为默认  
	 * @throws
	 */
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
	
	
	/**
	 * 根据id获取银行卡信息
	 * @Title: getById 
	 * @param id
	 * @return   
	 * @throws
	 */
	public  BankCard getById(Integer id){

		return  dao.get(id, BankCard.class);
	
	}
	
	/**
	 * 根据用户id获取银行卡列表
	 * @Title: getListByUserId 
	 * @param userId
	 * @return   
	 * @throws
	 */
	public List<BankCard> getListByUserId(Integer userId){
		
	  return	dao.list(BankCard.class, "userId", userId);
	}
	
	
	/**
	 * 删除银行卡
	 * @Title: deleteById 
	 * @param id   
	 * @throws
	 */
	@Transactional
	public void deleteById(Integer id){
		dao.delete(id, BankCard.class);
	}
	

}
