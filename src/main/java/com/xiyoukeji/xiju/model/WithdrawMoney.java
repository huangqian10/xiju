/**
 * 
 */
package com.xiyoukeji.xiju.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.New;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
	

	
	@Column(name="withdraw_status" ,length = 2,nullable=false,columnDefinition="INT default 0")
	private Integer withdrawStatus;  //状态
	
	@Column(name="total_money" ,length = 11)
	private Integer totalMoney;  //提现金额
	
	@Column(name="withdraw_time" ,length = 20)
	private Long withdrawTime;  //提现的时间
	
	@OneToMany(targetEntity=Receipt.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY,mappedBy="withdrawMoney")
	private List<Receipt> receipt=new ArrayList<Receipt>();
	
	@OneToOne
	private BankCard bankCard;

	
	

	public void setReceipt(List<Receipt> receipt) {
		this.receipt = receipt;
	}


	@Column(name="ctime",length=20)        
	private Long ctime=System.currentTimeMillis();     //记录生成的时间
	
	public BankCard getBankCard() {
		return bankCard;
	}

	public void setBankCard(BankCard bankCard) {
		this.bankCard = bankCard;
	}
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}



	public Integer getWithdrawStatus() {
		return withdrawStatus;
	}

	public void setWithdrawStatus(Integer withdrawStatus) {
		this.withdrawStatus = withdrawStatus;
	}



	public Integer getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Integer totalMoney) {
		this.totalMoney = totalMoney;
	}



	public Long getWithdrawTime() {
		return withdrawTime;
	}

	public void setWithdrawTime(Long withdrawTime) {
		this.withdrawTime = withdrawTime;
	}




	
	public List<Receipt> getReceipt() {
		return receipt;
	}

	public void setReceipt(Receipt receipt) {
		this.receipt.add( receipt);
	}

	public Long getCtime() {
		return ctime;
	}

	public void setCtime(Long ctime) {
		this.ctime = ctime;
	}

	@Override
	public String toString() {
		return "WithdrawMoney [id=" + id + ", userId=" + userId + ", withdrawStatus=" + withdrawStatus + ", totalMoney="
				+ totalMoney + ", withdrawTime=" + withdrawTime + ", receipt=" + receipt + ", bankCard=" + bankCard
				+ ", ctime=" + ctime + "]";
	}
	
	
}
