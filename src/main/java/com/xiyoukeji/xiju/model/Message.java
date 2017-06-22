package com.xiyoukeji.xiju.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="message")
public class Message {
	
	public final static Integer TYPE_WORD=1;
	public final static Integer TYPE_GOODS=2;
	public final static Integer TYPE_CHUANYI=3;
	@Id
	@GeneratedValue(generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "id", length = 11)
	private Integer id;
	
	/**
	 * 详情
	 */
	@Column(name="content",length=255)
	private String content;
	
	/**
	 * 标题
	 */
	@Column(name="title",length=255)
	private String title;
	
	@Column(name="type",length=11)
	private Integer type;
	
	@Column(name="turn_id",length=11)
	private Integer turnId;
	
	@Column(name="goods_name",length=255)
	private String goodsName;
	
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getTurnId() {
		return turnId;
	}

	public void setTurnId(Integer turnId) {
		this.turnId = turnId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
}
