package com.xiyoukeji.xiju.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/**
 * 
 * 底部栏的三个：选购，发现，创意
 *
 */
@Entity
@Table(name="activity")
public class Activity {
	@Id
	@GeneratedValue(generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "id", length = 11)
	private Integer id;
	
	
	/**
	 * 活动类型
	 */
	@Column(name="activity_type",length=11)
	private Integer activityType;
	
	
	/**
	 * 活动显示时间
	 */
	@Column(name="begin_time",length=11)
	private Integer beginTime;
	
	/**
	 * 活动结束显示时间
	 */
	@Column(name="end_time",length=11)
	private Integer endTime;
	
	/**
	 * 选购必填：对应的单品id
	 */
	@Column(name="xuangou_goods_id",length=11)
	private Integer xuangouGoodsId;
	
	/**
	 * 选购必填:对应的选购的海报
	 */
	@Column(name="xuangou_pic",length=255)
	private String xuangouPic;
	
	/**
	 * 选购必填：对应的介绍缩略图图片
	 */
	@Column(name="xuangou_pic_url",length=65535)
	private String xuangouSmallUrl;
	
	/**
	 * 发现必填:对应发现（活动）对应的id
	 */
	@Column(name="faxian_id",length=11)
	private Integer faxianId;
	
	/**
	 * 发现必填：发现（活动）对应的海报
	 */
	@Column(name="faxian_pic",length=255)
	private String faxianPic;
	
	/**
	 * 创意必填 创意 id
	 */
	@Column(name="chuanyi_id",length=11)
	private Integer chuanyiId;
	
	/**
	 * 创意必填 创意对应的海报
	 */
	@Column(name="chuanyi_pic",length=255)
	private String chuanyiPic;
	
	
	/**
	 * 商品颜色
	 */
	@Column(name="goods_color",length=255)
	private String goodsColor;
	
	@Column(name="price",length=11)
	private Integer price;
	
	
	/**
	 * title
	 */
	@Column(name="title",length=255)
	private String title;
	
	/**
	 * detail
	 */
	@Column(name="detail",length=255)
	private String detail;
	
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getActivityType() {
		return activityType;
	}

	public void setActivityType(Integer activityType) {
		this.activityType = activityType;
	}

	public Integer getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Integer beginTime) {
		this.beginTime = beginTime;
	}

	public Integer getEndTime() {
		return endTime;
	}

	public void setEndTime(Integer endTime) {
		this.endTime = endTime;
	}

	public Integer getXuangouGoodsId() {
		return xuangouGoodsId;
	}

	public void setXuangouGoodsId(Integer xuangouGoodsId) {
		this.xuangouGoodsId = xuangouGoodsId;
	}

	public String getXuangouPic() {
		return xuangouPic;
	}

	public void setXuangouPic(String xuangouPic) {
		this.xuangouPic = xuangouPic;
	}

	public String getXuangouSmallUrl() {
		return xuangouSmallUrl;
	}
	public void setXuangouSmallUrl(String xuangouSmallUrl) {
		this.xuangouSmallUrl = xuangouSmallUrl;
	}

	public Integer getFaxianId() {
		return faxianId;
	}

	public void setFaxianId(Integer faxianId) {
		this.faxianId = faxianId;
	}

	public String getFaxianPic() {
		return faxianPic;
	}

	public void setFaxianPic(String faxianPic) {
		this.faxianPic = faxianPic;
	}

	public Integer getChuanyiId() {
		return chuanyiId;
	}

	public void setChuanyiId(Integer chuanyiId) {
		this.chuanyiId = chuanyiId;
	}

	public String getChuanyiPic() {
		return chuanyiPic;
	}

	public void setChuanyiPic(String chuanyiPic) {
		this.chuanyiPic = chuanyiPic;
	}

	public String getGoodsColor() {
		return goodsColor;
	}

	public void setGoodsColor(String goodsColor) {
		this.goodsColor = goodsColor;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	
}
