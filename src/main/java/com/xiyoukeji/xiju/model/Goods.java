package com.xiyoukeji.xiju.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/**
 * 
 *  商品表
 *
 */

@Entity
@Table(name="goods")
public class Goods {
	@Id
	@GeneratedValue(generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "id", length = 11)
	private Integer id;
	
	@Column(name="poster",length=255)
	private String poster;
	
	@Column(name="poster_type",length=11)
	private Integer posterType;
	
	@Column(name="title",length=255)
	private String title;
	
	@Column(name="detail",length=255)
	private String detail;
	
	@Column(name="goods_price",length=11)
	private Integer goodsPrice;
	
	@Column(name="goods_sale_price",length=11)
	private Integer goodsSalePrice;
	
	@Column(name="small_url",length=255)
	private String smallUrl;
	
	@Column(name="tuijian_goods_id",length=255)
	private String tuijianGoodsId;
	
	@Column(name="content_ids",length=255)
	private String contentIds;
	
	@Column(name="type_id",length=11)
	private Integer typeId;
	
	/**
	 * 商品状态
	 */
	@Column(name="status",length=11)
	private Integer status;
	
	
	@Column(name="size_pic",length=255)
	private String sizePic;
	
	private String typeName;
	
	
	
	
	public String getSizePic() {
		return sizePic;
	}

	public void setSizePic(String sizePic) {
		this.sizePic = sizePic;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public void setContentIds(String contentIds) {
		this.contentIds = contentIds;
	}
	
	public String getContentIds() {
		return contentIds;
	}
	public String getTuijianGoodsId() {
		return tuijianGoodsId;
	}
	public void setTuijianGoodsId(String tuijianGoodsId) {
		this.tuijianGoodsId = tuijianGoodsId;
	}
	public String getSmallUrl() {
		return smallUrl;
	}

	public void setSmallUrl(String smallUrl) {
		this.smallUrl = smallUrl;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public Integer getPosterType() {
		return posterType;
	}

	public void setPosterType(Integer posterType) {
		this.posterType = posterType;
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

	public Integer getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(Integer goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public Integer getGoodsSalePrice() {
		return goodsSalePrice;
	}

	public void setGoodsSalePrice(Integer goodsSalePrice) {
		this.goodsSalePrice = goodsSalePrice;
	}

	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
