package com.xiyoukeji.xiju.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="receipt_address")
public class ReceiptAddress implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -582837561438872495L;

	@Id
	@GeneratedValue(generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "id", length = 11)
	private Integer id;
	
	@Column(name="user_id",length=11)
	private Integer userId;
	
	/**
	 * 详细收货地址
	 */
	@Column(name="address",length=255)
	private String address;
	
	/**
	 * 收货人手机号码
	 */
	@Column(name="mobile",length=255)
	private String mobile;
	/**
	 * 收货人姓名
	 */
	@Column(name="name",length=63)
	private String name;
	
	/**
	 * 是否为默认地址
	 */
	@Column(name="is_default",length=11)
	private Integer isDefault;
	
	/**
	 * 省市县
	 */
	@Column(name="local",length=255)
	private String local;

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public Integer getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}
}
