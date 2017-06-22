package com.xiyoukeji.xiju.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiyoukeji.xiju.core.dao.BaseHibernateDao;
import com.xiyoukeji.xiju.domain.GoodsColorStyleShow;
import com.xiyoukeji.xiju.model.Color;
import com.xiyoukeji.xiju.model.GoodsColorStyle;
import com.xiyoukeji.xiju.model.Style;

@Service
public class GoodsColorStyleService {
	@Autowired
	@Qualifier("hibernateDao")
	BaseHibernateDao<GoodsColorStyle, Integer> dao;

	@Autowired
	ColorService colorService;

	@Autowired
	StyleService styleService;

	@Transactional
	public void save(GoodsColorStyle g) {
		dao.save(g);
	}

	@Transactional
	public void update(GoodsColorStyle g) {
		dao.update(g);
	}

	public List<GoodsColorStyleShow> getByGoodsId(Integer goodsId) {
		List<GoodsColorStyleShow> res = new ArrayList<GoodsColorStyleShow>();
		DetachedCriteria dc = DetachedCriteria.forClass(GoodsColorStyle.class);
		dc.add(Restrictions.eq("goodsId", goodsId));
		List<GoodsColorStyle> dbRes = dao.list(dc);
		for (GoodsColorStyle goods : dbRes) {
			Color color = colorService.getColorById(goods.getColorId());
			String colorContent = "";
			String colorName = "";
			Integer colorId = null;
			if (color != null) {
				colorContent = color.getColorContent();
				colorName = color.getColorName();
				colorId = color.getId();
			}
			Style style = styleService.getById(goods.getStyleId());
			GoodsColorStyleShow show = new GoodsColorStyleShow(colorId,
					style.getId(), colorContent, style.getName(),
					goods.getPicUrl(), colorName, goods.getLeftCount(),
					goods.getId(), goods.getPrice(), goods.getSalePrice(),
					goods.getSid());
			res.add(show);
		}
		return res;
	}

	public List<GoodsColorStyle> getListByGoodsId(Integer goodsId) {
		DetachedCriteria dc = DetachedCriteria.forClass(GoodsColorStyle.class);
		dc.add(Restrictions.eq("goodsId", goodsId));
		List<GoodsColorStyle> dbRes = dao.list(dc);
		return dbRes;
	}

	public boolean isEnough(Integer id, Integer count) {
		GoodsColorStyle goods = dao.get(id, GoodsColorStyle.class);
		if (goods == null)
			return false;
		return goods.getLeftCount() >= count;
	}

	@Transactional
	public void devCount(Integer id, Integer count) {
		GoodsColorStyle goods = dao.get(id, GoodsColorStyle.class);
		goods.setLeftCount(goods.getLeftCount() - count);
		dao.update(goods);
	}

	@Transactional
	public void addCount(Integer id, Integer count) {
		GoodsColorStyle goods = dao.get(id, GoodsColorStyle.class);
		goods.setLeftCount(goods.getLeftCount() + count);
		dao.update(goods);
	}

	public List<Integer> getByColorId(Integer colorId, List<Integer> list1) {
		DetachedCriteria dc = DetachedCriteria.forClass(GoodsColorStyle.class);
		dc.add(Restrictions.eq("colorId", colorId));
		List<GoodsColorStyle> dbRes = dao.list(dc);
		List<Integer> list2 = new ArrayList<Integer>();
		for (GoodsColorStyle goodsColorStyle : dbRes) {
			list2.add(goodsColorStyle.getGoodsId());
		}
		if (list1 != null) {
			list1.retainAll(list2);
			return list1;
		}
		return list2;
	}

	public GoodsColorStyle getById(Integer id) {
		return dao.get(id, GoodsColorStyle.class);
	}

}
