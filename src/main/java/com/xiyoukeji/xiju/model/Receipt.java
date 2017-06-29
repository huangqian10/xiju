package com.xiyoukeji.xiju.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="receipt")
public class Receipt {
	
	
	public static final Integer ISRECEIPT=1;
	
	public static final Integer ISCOMMENTED=1;
	
	@Id
	@GeneratedValue(generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "id", length = 11)
	private Integer id;
	
	@Column(name="user_id",length=11)
	private Integer userId;
	
	
	
	@Column(name="pay_method",length=63)
	private String payMethod;
	
	@Column(name="amount",length=11)
	private Integer amount;
	
	@Column(name="net_amount",length=11)
	private Integer netAmount;
	
	@Column(name="status",length=11)
	private Integer status;
	
	@Column(name="create_time",length=11)
	private Integer createTime;
	
	@Column(name="pay_time",length=11)
	private Integer payTime;
	
	@Column(name="voucher_id",length=11)
	private Integer voucherId;
	
	@Column(name="pay_json",length=65535)
	private String payJson;

	@Column(name="address",length=255)
	private String address;
	
	@Column(name="mobile",length=63)
	private String mobile;
	
	@Column(name="name",length=255)
	private String name;
	
	@Column(name="receipt_address_id",length=11)
	private Integer receiptAddressId;
	
	@Column(name="fapiao",length=255)
	private String fapiao;
	
	@Column(name="memo",length=255)
	private String memo;
	

	
	@OneToOne
	private PromotionCode PromotionCode;
	
	@ManyToOne
	private WithdrawMoney withdrawMoney;
	


	public WithdrawMoney getWithdrawMoney() {
		return withdrawMoney;
	}

	public void setWithdrawMoney(WithdrawMoney withdrawMoney) {
		this.withdrawMoney = withdrawMoney;
	}

	public PromotionCode getPromotionCode() {
		return PromotionCode;
	}

	@Column(name="is_withdraw_money",length=4,nullable=false,columnDefinition="INT default 0")
	private Integer isWithdrawMoney;
	
	public Integer getIsWithdrawMoney() {
		return isWithdrawMoney;
	}

	public void setIsWithdrawMoney(Integer isWithdrawMoney) {
		this.isWithdrawMoney = isWithdrawMoney;
	}

	/**
	 * 是否已收货
	 */
	@Column(name="is_receipt",length=11)
	private Integer isReceipt;
	/**
	 * 是否已评价
	 */
	@Column(name="is_comment",length=11)
	private Integer isComment;
	
	
	/**
	 * 
	 * 
	 */
	private Integer showStatus;
	
	@Column(name="express_no",length=63)
	private String expressNo;
	
	
	
	public String getExpressNo() {
		return expressNo;
	}

	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFapiao() {
		return fapiao;
	}

	public void setFapiao(String fapiao) {
		this.fapiao = fapiao;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getReceiptAddressId() {
		return receiptAddressId;
	}

	public void setReceiptAddressId(Integer receiptAddressId) {
		this.receiptAddressId = receiptAddressId;
	}

	
	
	public Integer getShowStatus() {
		return showStatus;
	}

	public void setShowStatus(Integer showStatus) {
		this.showStatus = showStatus;
	}

	public Integer getIsComment() {
		return isComment;
	}

	public void setIsComment(Integer isComment) {
		this.isComment = isComment;
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

	public String getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(Integer netAmount) {
		this.netAmount = netAmount;
	}


	public void setPromotionCode(PromotionCode promotionCode) {
		PromotionCode = promotionCode;
	}

	public Integer getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}
	
	public Integer getPayTime() {
		return payTime;
	}

	public void setPayTime(Integer payTime) {
		this.payTime = payTime;
	}

	public Integer getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(Integer voucherId) {
		this.voucherId = voucherId;
	}

	public String getPayJson() {
		return payJson;
	}

	public void setPayJson(String payJson) {
		this.payJson = payJson;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getIsReceipt() {
		return isReceipt;
	}

	public void setIsReceipt(Integer isReceipt) {
		this.isReceipt = isReceipt;
	}

	@Override
	public String toString() {
		return "Receipt [id=" + id + ", userId=" + userId + ", payMethod=" + payMethod + ", amount=" + amount
				+ ", netAmount=" + netAmount + ", status=" + status + ", createTime=" + createTime + ", payTime="
				+ payTime + ", voucherId=" + voucherId + ", payJson=" + payJson + ", address=" + address + ", mobile="
				+ mobile + ", name=" + name + ", receiptAddressId=" + receiptAddressId + ", fapiao=" + fapiao
				+ ", memo=" + memo + ", PromotionCode=" + PromotionCode + ", isWithdrawMoney=" + isWithdrawMoney
				+ ", isReceipt=" + isReceipt + ", isComment=" + isComment + ", showStatus=" + showStatus
				+ ", expressNo=" + expressNo + "]";
	}
	
	
}
