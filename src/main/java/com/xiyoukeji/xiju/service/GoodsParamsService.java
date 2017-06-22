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
import com.xiyoukeji.xiju.domain.GoodsParamsShow;
import com.xiyoukeji.xiju.model.Goods;
import com.xiyoukeji.xiju.model.GoodsParams;
import com.xiyoukeji.xiju.model.Params;

@Service
public class GoodsParamsService extends BaseService{
	@Autowired
	@Qualifier("hibernateDao")
	BaseHibernateDao<GoodsParams, Integer> dao;
	
	@Autowired
	ParamsService paramService;
	
	@Transactional
	public void save(GoodsParams p){
		dao.save(p);
	}
	@Transactional
	public void update(GoodsParams p){
		dao.update(p);
	}
	
	
	@Transactional
	public void del(GoodsParams g){
		dao.delete(g.getId(), GoodsParams.class);
	}
	public List<GoodsParamsShow> getByGoodsId(Integer goodsId){
		List<GoodsParamsShow> showList=new ArrayList<GoodsParamsShow>();
		DetachedCriteria dc =DetachedCriteria.forClass(GoodsParams.class);
		dc.add(Restrictions.eq("goodsId", goodsId));
		List<GoodsParams>  dbres=dao.list(dc);
		for(GoodsParams goodsParam:dbres){
			Params params=paramService.getParamsById(goodsParam.getParamsId());
			if(params!=null){
				GoodsParamsShow show=new GoodsParamsShow(params, goodsParam.getContent());
				showList.add(show);
			}
		}
		return showList;
	}
	
	public List<GoodsParams> getWithGoodsId(Integer goodsId){
		DetachedCriteria dc =DetachedCriteria.forClass(GoodsParams.class);
		dc.add(Restrictions.eq("goodsId", goodsId));
		List<GoodsParams>  dbres=dao.list(dc);
		return dbres;
	}
	
	
}
