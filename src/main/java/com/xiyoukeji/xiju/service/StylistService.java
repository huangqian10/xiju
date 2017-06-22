/**
 * 
 */
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
import com.xiyoukeji.xiju.model.Stylist;
import com.xiyoukeji.xiju.model.UrlInfo;

/**
 * @author hq
 *
 */
@Service
public class StylistService {
	
	@Autowired
	@Qualifier("hibernateDao")
	BaseHibernateDao<Stylist, Integer> dao;
	
	
	@Transactional
	public void authentication(Integer userId,String stylistCardUrl,String stylistCertificateUrl,String businessLicenseUrl){
		Stylist stylist=new Stylist();
		stylist.setUserId(userId);
		stylist.setBusinessLicenseUrl(businessLicenseUrl);
		stylist.setStylistCardUrl(stylistCardUrl);
		stylist.setStylistCertificateUrl(stylistCertificateUrl);
		DetachedCriteria dc = DetachedCriteria.forClass(Stylist.class);
		dc.add(Restrictions.eq("userId", userId));
		if(dao.list(dc).isEmpty()){
			dao.save(stylist);
		}else{
			stylist=(Stylist)dao.list(dc).get(0);
			stylist.setBusinessLicenseUrl(businessLicenseUrl);
			stylist.setStylistCardUrl(stylistCardUrl);
			stylist.setStylistCertificateUrl(stylistCertificateUrl);
			dao.update(stylist);
		}
	}
	
	
	@Transactional
	public Stylist getStylistByUserId(Integer userId){
		DetachedCriteria dc = DetachedCriteria.forClass(Stylist.class);
		dc.add(Restrictions.eq("userId", userId));
		
		return (Stylist)dao.list(dc).get(0);
	}
	
	@Transactional
	public void changeStylistStatus(Integer userId,Integer status){
		DetachedCriteria dc = DetachedCriteria.forClass(Stylist.class);
		dc.add(Restrictions.eq("userId", userId));
		if(!dao.list(dc).isEmpty()){
		Stylist stylist=(Stylist)dao.list(dc).get(0);
		stylist.setCertificationStatus(status);
		dao.update(stylist);
		}
	}
	

}
