package com.xiyoukeji.xiju.domain;

import com.xiyoukeji.xiju.model.Goods;

public class GoodsListShow {
	
	private Integer id;
	private String title;
	private String detail;
	private Integer price;
	private String smallUrl;
	private Integer isCollect;
	public GoodsListShow(Goods goods,Integer isCollect){
		this.id=goods.getId();
		this.title=goods.getTitle();
		this.detail=goods.getDetail();
		this.price=goods.getGoodsSalePrice();
		this.smallUrl=goods.getSmallUrl();
		this.isCollect=isCollect;
	}
	
	public GoodsListShow(){
		
	}
	
	public Integer getIsCollect() {
		return isCollect;
	}

	public void setIsCollect(Integer isCollect) {
		this.isCollect = isCollect;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getSmallUrl() {
		return smallUrl;
	}

	public void setSmallUrl(String smallUrl) {
		this.smallUrl = smallUrl;
	}
	
}
