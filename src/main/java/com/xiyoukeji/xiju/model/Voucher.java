package com.xiyoukeji.xiju.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * 优惠券
 *
 */
@Entity
@Table(name="voucher")
public class Voucher {
	@Id
	@GeneratedValue(generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "id", length = 11)
	private Integer id;
	
	/**
	 * 优惠券金额
	 */
	@Column(name="voucher_amount",length=11)
	private Integer voucherAmount;
	
	/**
	 * 红包金额限制
	 */
	@Column(name="voucher_limit",length=11)
	private Integer voucherLimit;
	
	/**
	 * 红包可用时间（起始时间）
	 */
	@Column(name="voucher_begin_time",length=11)
	private Integer voucherBeginTime;
	
	/**
	 * 红包可用时间(终止时间)
	 */
	@Column(name="voucher_end_time",length=11)
	private Integer voucherEndTime;
	
	/**
	 * 红包状态(可用，不可用，锁定)
	 */
	@Column(name="voucher_status",length=11)
	private Integer voucherStatus;
	
	/**
	 * 归属人
	 */
	@Column(name="user_id",length=11)
	private Integer userId;

	@Column(name="voucher_name",length=255)
	private String voucherName;
	
	@Column(name="voucher_code_id",length=11)
	private Integer voucherCodeId;
	
	
	
	public Integer getVoucherCodeId() {
		return voucherCodeId;
	}

	public void setVoucherCodeId(Integer voucherCodeId) {
		this.voucherCodeId = voucherCodeId;
	}

	public String getVoucherName() {
		return voucherName;
	}

	public void setVoucherName(String voucherName) {
		this.voucherName = voucherName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	

	public Integer getVoucherAmount() {
		return voucherAmount;
	}

	public void setVoucherAmount(Integer voucherAmount) {
		this.voucherAmount = voucherAmount;
	}

	public Integer getVoucherLimit() {
		return voucherLimit;
	}

	public void setVoucherLimit(Integer voucherLimit) {
		this.voucherLimit = voucherLimit;
	}

	public Integer getVoucherBeginTime() {
		return voucherBeginTime;
	}

	public void setVoucherBeginTime(Integer voucherBeginTime) {
		this.voucherBeginTime = voucherBeginTime;
	}

	public Integer getVoucherEndTime() {
		return voucherEndTime;
	}

	public void setVoucherEndTime(Integer voucherEndTime) {
		this.voucherEndTime = voucherEndTime;
	}

	public Integer getVoucherStatus() {
		return voucherStatus;
	}

	public void setVoucherStatus(Integer voucherStatus) {
		this.voucherStatus = voucherStatus;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}
