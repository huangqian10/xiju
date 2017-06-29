/**
 * 
 */
package com.xiyoukeji.xiju.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.xiyoukeji.xiju.core.dao.BaseHibernateDao;
import com.xiyoukeji.xiju.core.enums.Const;
import com.xiyoukeji.xiju.model.BankCard;
import com.xiyoukeji.xiju.model.Receipt;
import com.xiyoukeji.xiju.model.Stylist;
import com.xiyoukeji.xiju.model.WithdrawMoney;

/**
 * @author hq
 *
 */
@Service
public class WithdrawMoneyService {
	
	@Autowired
	@Qualifier("hibernateDao")
	BaseHibernateDao<Receipt, Integer> dao;
	
	
	@Autowired
	@Qualifier("hibernateDao")
	BaseHibernateDao<WithdrawMoney, Integer> withdrawDao;
	
	
	@Autowired
	@Qualifier("hibernateDao")
	BaseHibernateDao<BankCard, Integer> bankCardDao;
	
	/**
	 * 提现
	 * @Title: doWithdrawMoney 
	 * @param userId 用户id
	 * @param receiptIdList     订单id列表
	 * @param bankCardId        银行卡id
	 * @param totalMoney        提现总金额
	 * @return   
	 * @throws
	 */
	@Transactional
	public Integer doWithdrawMoney(Integer userId,String receiptIdList,Integer bankCardId,Integer totalMoney){
		WithdrawMoney withdrawMoney=new WithdrawMoney();
		String[]  idlist=receiptIdList.split(",");
		
		
		for(int i=0;i<idlist.length;i++){
			withdrawMoney.getReceipt().add((dao.get(Integer.valueOf(idlist[i]), Receipt.class)));
		}
		
		long time=System.currentTimeMillis();
		withdrawMoney.setBankCard(bankCardDao.get(bankCardId, BankCard.class));
		withdrawMoney.setCtime(time);
		withdrawMoney.setTotalMoney(totalMoney);
		withdrawMoney.setUserId(userId);
		withdrawMoney.setWithdrawTime(time);
		withdrawMoney.setWithdrawStatus(0);
		withdrawDao.save(withdrawMoney);
		return Const.SUCCESS;
	}
	
	
	/**
	 * 更改订单状态
	 * @Title: changeWithdrawMoneyStatusById 
	 * @param id          
	 * @param status
	 * @return   
	 * @throws
	 */
	@Transactional
	public Integer  changeWithdrawMoneyStatusById(Integer id,Integer status){
		
		WithdrawMoney withdrawMoney	=withdrawDao.get(id, WithdrawMoney.class);
		withdrawMoney.setWithdrawStatus(status);
		withdrawDao.update(withdrawMoney);
		return Const.SUCCESS;
	}
	
	/**
	 * 根据id获取提现信息
	 * @Title: getWithdrawMoneyById 
	 * @param id
	 * @return   
	 * @throws
	 */
	public WithdrawMoney getWithdrawMoneyById(Integer id){
		WithdrawMoney withdrawMoney	=withdrawDao.get(id, WithdrawMoney.class);
		return withdrawMoney;
	}
	
	
	/**
	 * 根据用户id获取提现列表
	 * @Title: getWithdrawMoneyById 
	 * @param id
	 * @return   
	 * @throws
	 */
	public List<WithdrawMoney> getWithdrawMoneyByUserId(Integer userId){
		DetachedCriteria dc = DetachedCriteria.forClass(WithdrawMoney.class);
		dc.add(Restrictions.eq("userId", userId));
		return withdrawDao.list(dc);
	}
	
	
	
		
}
