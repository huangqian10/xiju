package com.xiyoukeji.xiju.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name="user_msg")
public class UserMsg {
	public static final Integer READ=1;
	public static final Integer UNREAD=2;
	
	@Id
	@GeneratedValue(generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "id", length = 11)
	private Integer id;
	
	@Column(name="user_id",length=11)
	private Integer userId;
	
	@Column(name="msg_id",length=11)
	private Integer msgId;
	
	@Column(name="status",length=11)
	private Integer status;
	/**
	 * 详情
	 */
	@Column(name="content",length=255)
	private String content;
	
	/**
	 * 创建时间
	 */
	@Column(name="create_time",length=11)
	private Integer createTime;
	
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
	
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getMsgId() {
		return msgId;
	}
	public void setMsgId(Integer msgId) {
		this.msgId = msgId;
	}
	
}
