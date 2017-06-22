package com.xiyoukeji.xiju.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 多品活动页
 *
 */
@Entity
@Table(name="faxian_activity")
public class FaxianActivity {
	@Id
	@GeneratedValue(generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "id", length = 11)
	private Integer id;
	
	
	@Column(name="faxian_name",length=255)
	private String faxianName;
	
	/**
	 * 海报链接
	 */
	@Column(name="poster_url",length=255)
	private String posterUrl;
	
	/**
	 * 视频或图片
	 */
	@Column(name="poster_type",length=11)
	private Integer posterType;
	
	
	
	/**
	 * 商品id列表
	 */
	@Column(name="goods_ids",length=255)
	private String goodsIds;

	/**
	 * detail
	 */
	@Column(name="detail",length=255)
	private String detail;
	
	@Column(name="title",length=255)
	private String title;
	
	@Column(name="price",length=11)
	private Integer price;
	
	@Column(name="content_ids",length=255)
	private String contentIds;
	
	

	public Integer getPrice() {
		return price;
	}


	public void setPrice(Integer price) {
		this.price = price;
	}
	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getPosterUrl() {
		return posterUrl;
	}


	public void setPosterUrl(String posterUrl) {
		this.posterUrl = posterUrl;
	}

	


	public Integer getPosterType() {
		return posterType;
	}


	public void setPosterType(Integer posterType) {
		this.posterType = posterType;
	}


	public String getGoodsIds() {
		return goodsIds;
	}


	public void setGoodsIds(String goodsIds) {
		this.goodsIds = goodsIds;
	}


	public String getFaxianName() {
		return faxianName;
	}


	public void setFaxianName(String faxianName) {
		this.faxianName = faxianName;
	}


	public String getDetail() {
		return detail;
	}


	public void setDetail(String detail) {
		this.detail = detail;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getContentIds() {
		return contentIds;
	}


	public void setContentIds(String contentIds) {
		this.contentIds = contentIds;
	}	
	
	
}
