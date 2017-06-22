package com.xiyoukeji.xiju.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.xiyoukeji.xiju.core.dao.BaseHibernateDao;
import com.xiyoukeji.xiju.core.enums.GoodsStatus;
import com.xiyoukeji.xiju.domain.GoodsListShow;
import com.xiyoukeji.xiju.model.Collect;
import com.xiyoukeji.xiju.model.Goods;

@Service
public class GoodsService extends BaseService {
	@Autowired
	@Qualifier("hibernateDao")
	BaseHibernateDao<Goods, Integer> dao;
	
	@Autowired
	CollectService collectService;
	public Goods getById(Integer id){
		return dao.get(id, Goods.class);
	}
	
	public List<Goods> getAllUseful(List<Integer> ids){
		DetachedCriteria dc = DetachedCriteria.forClass(Goods.class);
		dc.add(Restrictions.eq("status", GoodsStatus.ONLINE.value()));
		if(ids!=null&&ids.size()>0){
			dc.add(Restrictions.in("id", ids));
		}
		return dao.list(dc);

	}
	public List<Goods> getAllByIds(List<Integer> ids){
		DetachedCriteria dc = DetachedCriteria.forClass(Goods.class);
		if(ids!=null&&ids.size()>0){
			dc.add(Restrictions.in("id", ids));
		}
		return dao.list(dc);

	}
	
	
	public List<Goods> getAllByAdmin(Integer typeId,Integer status){
		DetachedCriteria dc = DetachedCriteria.forClass(Goods.class);
		if(typeId!=null){
			dc.add(Restrictions.eq("typeId", typeId));
		}
		if(status!=null){
			dc.add(Restrictions.eq("status", status));
		}
		return dao.list(dc);

	}
	public List<Goods> getByIdList(String id){
		Integer[] ids = StringtoInt(id);
		DetachedCriteria dc = DetachedCriteria.forClass(Goods.class);
		dc.add(Restrictions.in("id", ids));
		return dao.list(dc);
	}
	public List<GoodsListShow> getByIds(String ids,Integer userId){
		Integer[] id=StringtoInt(ids);
		List<GoodsListShow> showList= new ArrayList<GoodsListShow>();
		DetachedCriteria dc =DetachedCriteria.forClass(Goods.class);
		dc.add(Restrictions.in("id", id));
		List<Goods> dbRes=dao.list(dc);
		List<Collect> collects =collectService.getByUserId(userId);
		Map<Integer,Integer> collectMap = new HashMap<Integer, Integer>();
		for(Collect collect:collects){
			collectMap.put(collect.getGoodsId(), 1);
		}
		for(Goods good:dbRes){
			Integer isCollect= collectMap.get(good.getId())==null?0:1;
			showList.add(new GoodsListShow(good,isCollect));
		}
		collectMap=null;
		return showList;
	}
	
	public List<GoodsListShow> getByIds(String ids){
		Integer[] id=StringtoInt(ids);
		List<GoodsListShow> showList= new ArrayList<GoodsListShow>();
		DetachedCriteria dc =DetachedCriteria.forClass(Goods.class);
		dc.add(Restrictions.in("id", id));
		List<Goods> dbRes=dao.list(dc);
		for(Goods good:dbRes){
			showList.add(new GoodsListShow(good,0));
		}
		return showList;
	}
	public List<Integer> getByTypeId(Integer typeId){
		List<Integer> goodsId = new ArrayList<Integer>();
		DetachedCriteria dc = DetachedCriteria.forClass(Goods.class);
		dc.add(Restrictions.eq("typeId", typeId));
		List<Goods> goodsList=dao.list(dc);
		for(Goods goods:goodsList){
			goodsId.add(goods.getId());
		}
		return goodsId;
	}
	
	@Transactional
	public void save(Goods goods){
		dao.save(goods);
	}
	
	@Transactional
	public void update(Goods goods){
		dao.update(goods);
	}
}
