/**
 * 
 */
package com.xiyoukeji.xiju.model;

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
@Table(name="bank_card")
public class BankCard {
	
	@Id
	@GeneratedValue(generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "id", length = 11)
	private Integer id;
	
	
	@Column(name="user_id" ,length = 11)
	private Integer userId;  //用户id
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name="owner" ,length = 255)
	private String owner;  //提卡人
	
	
	@Column(name="open_number" ,length = 255)
	private String openNumber;  //开户号
	
	@Column(name="open_bank" ,length = 255)
	private String openBank;  //开户银行
	
	
	@Column(name="cellphone" ,length = 255)
	private String cellphone;  //手机号
	
	@Column(name="is_default" ,length = 4,nullable=false,columnDefinition="INT default 0")
	private int isDefault;  //是否默认
	
	@Column(name="ctime",length=20)
	private long ctime=System.currentTimeMillis();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getOpenNumber() {
		return openNumber;
	}

	public void setOpenNumber(String openNumber) {
		this.openNumber = openNumber;
	}

	public String getOpenBank() {
		return openBank;
	}

	public void setOpenBank(String openBank) {
		this.openBank = openBank;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}



	public int getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(int isDefault) {
		this.isDefault = isDefault;
	}

	public long getCtime() {
		return ctime;
	}

	public void setCtime(long ctime) {
		this.ctime = ctime;
	}
	
	
	

}
