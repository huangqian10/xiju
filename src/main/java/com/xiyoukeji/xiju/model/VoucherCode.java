package com.xiyoukeji.xiju.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name="voucher_code")
public class VoucherCode {
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
	
	
	@Column(name="voucher_name",length=255)
	private String voucherName;
	
	/**
	 * 优惠码兑换开始时间
	 */
	@Column(name="code_begin_time",length=11)
	private Integer codeBeginTime;
	
	/**
	 * 优惠码兑换结束时间
	 */
	@Column(name="code_end_time",length=11)
	private Integer codeEndTime;
	
	@Column(name="code_content",length=255)
	private String codeContent;
	
	
	

	public String getCodeContent() {
		return codeContent;
	}

	public void setCodeContent(String codeContent) {
		this.codeContent = codeContent;
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

	public String getVoucherName() {
		return voucherName;
	}

	public void setVoucherName(String voucherName) {
		this.voucherName = voucherName;
	}

	public Integer getCodeBeginTime() {
		return codeBeginTime;
	}

	public void setCodeBeginTime(Integer codeBeginTime) {
		this.codeBeginTime = codeBeginTime;
	}

	public Integer getCodeEndTime() {
		return codeEndTime;
	}

	public void setCodeEndTime(Integer codeEndTime) {
		this.codeEndTime = codeEndTime;
	}
	
	
}
