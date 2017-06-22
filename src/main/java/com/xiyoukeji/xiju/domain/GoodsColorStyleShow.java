package com.xiyoukeji.xiju.domain;


public class GoodsColorStyleShow {

	private String colorContent;
	
	private String styleName;
	
	private String url;
	
	private String colorName;
	
	private Integer leftCount;
	
	private Integer id;
	
	private Integer price;
	
	private Integer salePrice;
	
	private Integer colorId;
	
	private Integer styleId;
	
	private String sid;
	
	
	

	public GoodsColorStyleShow(String colorContent,String styleName,String url,String colorName,Integer leftCount,Integer id,Integer price,Integer salePrice){
		this.colorContent=colorContent;
		this.styleName=styleName;
		this.url=url;
		this.colorName=colorName;
		this.leftCount=leftCount;
		this.id=id;
		this.salePrice=salePrice;
		this.price=price;
	}
	
	public GoodsColorStyleShow(Integer colorId,Integer styleId,String colorContent,String styleName,String url,String colorName,Integer leftCount,Integer id,Integer price,Integer salePrice,String sid){
		this.colorContent=colorContent;
		this.styleName=styleName;
		this.url=url;
		this.colorName=colorName;
		this.leftCount=leftCount;
		this.id=id;
		this.salePrice=salePrice;
		this.sid=sid;
		this.price=price;
		this.colorId=colorId;
		this.styleId=styleId;
	}
	
	
	public Integer getColorId() {
		return colorId;
	}



	public void setColorId(Integer colorId) {
		this.colorId = colorId;
	}



	public Integer getStyleId() {
		return styleId;
	}



	public void setStyleId(Integer styleId) {
		this.styleId = styleId;
	}



	public Integer getPrice() {
		return price;
	}


	public void setPrice(Integer price) {
		this.price = price;
	}


	public Integer getSalePrice() {
		return salePrice;
	}


	public void setSalePrice(Integer salePrice) {
		this.salePrice = salePrice;
	}


	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public Integer getLeftCount() {
		return leftCount;
	}



	public void setLeftCount(Integer leftCount) {
		this.leftCount = leftCount;
	}



	public String getColorName() {
		return colorName;
	}

	public void setColorName(String colorName) {
		this.colorName = colorName;
	}

	public String getColorContent() {
		return colorContent;
	}

	public void setColorContent(String colorContent) {
		this.colorContent = colorContent;
	}

	public String getStyleName() {
		return styleName;
	}

	public void setStyleName(String styleName) {
		this.styleName = styleName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}
}
