/**
 * 
 */
package com.xiyoukeji.xiju.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.xiyoukeji.xiju.core.dao.BaseHibernateDao;
import com.xiyoukeji.xiju.core.enums.Const;
import com.xiyoukeji.xiju.model.Style;
import com.xiyoukeji.xiju.model.WithdrawalProportion;

/**
 * @author hq
 *
 */
@Service
@Transactional
public class WithdrawalProportionService {
	@Autowired
	@Qualifier("hibernateDao")
	BaseHibernateDao<WithdrawalProportion, Integer> dao;

	public Integer setProportion(Integer proportion){
		long time=System.currentTimeMillis();
		
		if(dao.countAll(WithdrawalProportion.class)==0){
			WithdrawalProportion withdrawalProportion=new WithdrawalProportion();
			withdrawalProportion.setCtime(time);
			withdrawalProportion.setUtime(time);
			withdrawalProportion.setProportion(proportion);
			dao.save(withdrawalProportion);
		}else{
			
			WithdrawalProportion withdrawalProportion=(WithdrawalProportion)dao.list(WithdrawalProportion.class).get(0);
			withdrawalProportion.setUtime(time);
			withdrawalProportion.setProportion(proportion);
			dao.update(withdrawalProportion);
		}
		
		
		return Const.SUCCESS;
		
	}
	
		public Integer getProportion(){
			List<WithdrawalProportion> list=	dao.list(WithdrawalProportion.class);
			if(list.isEmpty()){
		return 0;
			}else{
		return list.get(0).getProportion();
	}
	
	
	}
	
	
	
}
