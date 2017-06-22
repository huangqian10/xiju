/**
 * 
 */
package com.xiyoukeji.xiju.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author hq
 *
 */
@Entity
@Table(name="withdraw_money")
public class WithdrawMoney {
	@Id
	@GeneratedValue(generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "id", length = 11)
	private Integer id;
	
	
	@Column(name="user_id" ,length = 11)
	private Integer userId;  //用户id
	
	@Column(name="withdraw_status" ,length = 2)
	private Integer withdrawStatus;  //状态
	
	@Column(name="total_money" ,length = 20)
	private BigDecimal totalMoney;  //提现金额
	
	@Column(name="withdraw_time" ,length = 20)
	private Long withdrawTime;  //提现的时间
	
	
	@Column(name="owner" ,length = 255)
	private String owner;  //提卡人
	
	
	@Column(name="open_number" ,length = 255)
	private String openNumber;  //开户号
	
	@Column(name="open_bank" ,length = 255)
	private String openBank;  //开户号
	
	
	@Column(name="cellphone" ,length = 255)
	private String cellphone;  //手机号
	
	@Column(name="ctime",length=20)        
	private Long ctime=System.currentTimeMillis();     //记录生成的时间
}
