/**
 * 
 */
package com.xiyoukeji.xiju.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.xiyoukeji.xiju.core.dao.BaseHibernateDao;
import com.xiyoukeji.xiju.model.Style;

/**
 * @author hq
 *
 */
@Service
@Transactional
public class TestService {
	@Autowired
	@Qualifier("hibernateDao")
	BaseHibernateDao<Style, Integer> dao;
	
	
	
	public void test(){
		
		Style style=new Style();
		style.setName("ere");
		dao.save(style);
	}
}
