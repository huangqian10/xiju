package com.xiyoukeji.xiju.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiyoukeji.xiju.core.dao.BaseHibernateDao;
import com.xiyoukeji.xiju.model.PowerInfo;
import com.xiyoukeji.xiju.model.UrlInfo;


@Service
public class PowerInfoService extends BaseService{
	
	@Autowired
	UrlInfoService urlInfoService;
	@Autowired
	@Qualifier("hibernateDao")
	BaseHibernateDao<PowerInfo, Integer> dao;
	
	@Transactional
	public void save(PowerInfo p){
		dao.save(p);
	}
	
	@Transactional
	public void update(PowerInfo p){
		dao.update(p);
	}
	
	public boolean isAuthUrl(String uri,Integer powerId){
		UrlInfo urlInfo =urlInfoService.getUrlInfoByUri(uri);

		PowerInfo powerInfo =dao.get(powerId, PowerInfo.class);
		
		if(urlInfo!=null&&powerInfo!=null){
			System.out.println(urlInfo.getId());
			System.out.println(powerInfo.getUrlInfoIds());
			Integer[] authId=StringtoInt(powerInfo.getUrlInfoIds());
			for(Integer id:authId){
				if(id.equals(urlInfo.getId())){
					return true;
				}
			}
		}
		return false;
	}
	
	public List<PowerInfo> getAll(){
		DetachedCriteria dc = DetachedCriteria.forClass(PowerInfo.class);
		return dao.list(dc);
	}
	
	public PowerInfo getById(Integer id){
		return dao.get(id, PowerInfo.class);
	}
}
