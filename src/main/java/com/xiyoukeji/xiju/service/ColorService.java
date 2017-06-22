package com.xiyoukeji.xiju.service;

import java.util.List;
import java.util.StringTokenizer;

import org.springframework.transaction.annotation.Transactional;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.xiyoukeji.xiju.core.dao.BaseHibernateDao;
import com.xiyoukeji.xiju.model.Color;

@Service
public class ColorService extends BaseService{

	@Autowired
	@Qualifier("hibernateDao")
	BaseHibernateDao<Color, Integer> dao;

	@Transactional
	public void save(Color c) {
		dao.save(c);
	}

	@Transactional
	public void update(Color c) {
		dao.update(c);
	}

	public String getColorContent(Integer id) {
		Color color = dao.get(id, Color.class);
		if (color == null) {
			return null;
		} else {
			return color.getColorContent();
		}
	}
	
	public boolean isExistColorName(String colorName,Integer id){
		DetachedCriteria dc =DetachedCriteria.forClass(Color.class);
		dc.add(Restrictions.eq("colorName", colorName));
		List<Color> colorList=dao.list(dc);
		if(colorList!=null&&colorList.size()>0){
			if(id==null){
				return true;
			}
			Color color =colorList.get(0);
			if(!color.getId().equals(id)){
				return true;
			}
		}
		return false;
	}

	public Color getColorById(Integer id){
		return dao.get(id, Color.class);
	}
	public Color getById(Integer id) {
		return dao.get(id, Color.class);
	}

	public String trans(String ids) {
		DetachedCriteria dc = DetachedCriteria.forClass(Color.class);
		Integer[] id = StringtoInt(ids);
		dc.add(Restrictions.in("id", id));
		List<Color> res = dao.list(dc);
		StringBuffer resStr = new StringBuffer();
		for (int i = 0; i < res.size(); i++) {
			Color color = res.get(i);
			if (i == 0) {
				resStr.append(color.getColorContent());
			} else {
				resStr.append(",").append(color.getColorContent());
			}
		}
		return resStr.toString();
	}

	public List<Color> getALl(){
		DetachedCriteria dc = DetachedCriteria.forClass(Color.class);
		return dao.list(dc);
	}

}
