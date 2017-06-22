package com.xiyoukeji.xiju.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 *  购物车
 *
 */
@Entity
@Table(name="cart")
public class Cart {
	@Id
	@GeneratedValue(generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "id", length = 11)
	private Integer id;
	
	/**
	 * 对应的商品id
	 */
	@Column(name="goods_id",length=11)
	private Integer goodsId;
	
	/**
	 * 
	 */
	@Column(name="user_id",length=11)
	private Integer userId;
	
	@Column(name="pay_json",length=65535)
	private String payJson;
	
	@Column(name="create_time",length=11)
	private Integer createTime;
	
	public Integer getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getPayJson() {
		return payJson;
	}

	public void setPayJson(String payJson) {
		this.payJson = payJson;
	}
	
	
	
	
	
}
