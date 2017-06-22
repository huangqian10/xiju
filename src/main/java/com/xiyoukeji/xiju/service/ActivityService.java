package com.xiyoukeji.xiju.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import com.xiyoukeji.xiju.core.dao.BaseHibernateDao;
import com.xiyoukeji.xiju.core.enums.ActivityType;
import com.xiyoukeji.xiju.core.enums.Const;
import com.xiyoukeji.xiju.model.Activity;

@Service
public class ActivityService extends BaseService {

	private static final Integer RESET = 1;
	private static final Integer NORESET = 2;

	@Autowired
	@Qualifier("hibernateDao")
	BaseHibernateDao<Activity, Integer> dao;

	@Autowired
	ColorService colorService;

	public List<Activity> getActivity(boolean isTiming, Integer type) {
		DetachedCriteria dc = DetachedCriteria.forClass(Activity.class);
		if (isTiming) {
			int nowTime = (int) (System.currentTimeMillis() / 1000);
			dc.add(Restrictions.lt("beginTime", nowTime));
			dc.add(Restrictions.gt("endTime", nowTime));
		}
		if (type != null) {
			dc.add(Restrictions.eq("activityType", type));
		}
		List<Activity> dbRes = dao.list(dc);
		List<Activity> resList = new ArrayList<Activity>();
		for (Activity activity : dbRes) {
			if (activity.getActivityType().equals(ActivityType.XUANGOU.value())
					&& !StringUtils.isEmpty(activity.getGoodsColor())) {
				activity.setGoodsColor(colorService.trans(activity
						.getGoodsColor()));
			}
			resList.add(activity);
		}
		return resList;
	}
	
	public Activity getById(Integer id){
		return dao.get(id, Activity.class);
	}

	@Transactional
	public void save(Activity a) {
		dao.save(a);
	}

	@Transactional
	public void update(Activity a) {
		dao.update(a);
	}

	@Transactional
	public void delete(Integer id) {
		dao.delete(id, Activity.class);
	}

	/**
	 * 当前优惠的活动
	 */
	@SuppressWarnings("unchecked")
	public List<Integer> getUserFulFaxian() {
		List<Integer> faxianIds = (List<Integer>) redisCache
				.getObject(Const.USEFUL_FAXIAN_ID);
		if (faxianIds == null) {
			faxianIds = new ArrayList<Integer>();
			DetachedCriteria dc = DetachedCriteria.forClass(Activity.class);
			dc.add(Restrictions.eq("activityType", ActivityType.FAXIAN.value()));
			int nowTime = (int) (System.currentTimeMillis() / 1000);
			dc.add(Restrictions.lt("beginTime", nowTime));
			dc.add(Restrictions.gt("endTime", nowTime));
			List<Activity> acLists = dao.list(dc);
			for (Activity a : acLists) {
				faxianIds.add(a.getId());
			}
			redisCache.setObject(Const.USEFUL_FAXIAN_ID, faxianIds, 60);
		}
		return faxianIds;
	}

	/**
	 * 获取需要置为原价的活动列表
	 */
	public List<Activity> getActivityForReset(Integer nowTime) {
		DetachedCriteria dc = DetachedCriteria.forClass(Activity.class);
		dc.add(Restrictions.lt("endTime", nowTime));
		dc.add(Restrictions.eq("isReset", RESET));
		return dao.list(dc);
	}
}
