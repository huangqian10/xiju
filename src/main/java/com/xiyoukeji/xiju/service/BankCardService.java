/**
 * 
 */
package com.xiyoukeji.xiju.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

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

}
