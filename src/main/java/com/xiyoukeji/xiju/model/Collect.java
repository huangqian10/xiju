package com.xiyoukeji.xiju.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * 我的收藏
 *
 */
@Entity
@Table(name="collect")
public class Collect {
	@Id
	@GeneratedValue(generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "id", length = 11)
	private Integer id;
	
	@Column(name="goods_id",length=11)
	private Integer goodsId;
	
	@Column(name="user_id",length=11)
	private Integer userId;
	
	/**
	 * 收藏时间
	 */
	@Column(name="collect_time",length=11)
	private Integer CollectTime;

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

	public Integer getCollectTime() {
		return CollectTime;
	}

	public void setCollectTime(Integer collectTime) {
		CollectTime = collectTime;
	}
}
