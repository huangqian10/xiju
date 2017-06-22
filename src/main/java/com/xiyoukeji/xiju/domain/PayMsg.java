package com.xiyoukeji.xiju.domain;

public class PayMsg {
	private Integer goodsId;
	private Integer goodsAmount;
	private String goodsColor;
	private String goodsStyle;
	private Integer goodsColorStyleId;
	private String goodsName;
	private String goodsSmallUrl;
	private String colorName;
	private Integer price;
	private Integer commentId;
	public PayMsg(Integer goodsId,Integer goodsAmount,String goodsColor,String goodsStyle, Integer goodsColorStyleId,String goodsName,
			String goodsSmallUrl,String colorName){
		this.goodsId=goodsId;
		this.goodsAmount=goodsAmount;
		this.goodsColor=goodsColor;
		this.goodsStyle=goodsStyle;
		this.goodsColorStyleId=goodsColorStyleId;
		this.goodsName=goodsName;
		this.goodsSmallUrl=goodsSmallUrl;
		this.colorName=colorName;
	}

	
	public Integer getCommentId() {
		return commentId;
	}


	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}


	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public PayMsg(){}
	
	
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsSmallUrl() {
		return goodsSmallUrl;
	}

	public void setGoodsSmallUrl(String goodsSmallUrl) {
		this.goodsSmallUrl = goodsSmallUrl;
	}

	public String getColorName() {
		return colorName;
	}

	public void setColorName(String colorName) {
		this.colorName = colorName;
	}

	public Integer getGoodsColorStyleId() {
		return goodsColorStyleId;
	}

	public void setGoodsColorStyleId(Integer goodsColorStyleId) {
		this.goodsColorStyleId = goodsColorStyleId;
	}

	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	public Integer getGoodsAmount() {
		return goodsAmount;
	}

	public void setGoodsAmount(Integer goodsAmount) {
		this.goodsAmount = goodsAmount;
	}

	public String getGoodsColor() {
		return goodsColor;
	}

	public void setGoodsColor(String goodsColor) {
		this.goodsColor = goodsColor;
	}

	public String getGoodsStyle() {
		return goodsStyle;
	}

	public void setGoodsStyle(String goodsStyle) {
		this.goodsStyle = goodsStyle;
	}


	@Override
	public String toString() {
		return "PayMsg [goodsId=" + goodsId + ", goodsAmount=" + goodsAmount
				+ ", goodsColor=" + goodsColor + ", goodsStyle=" + goodsStyle
				+ ", goodsColorStyleId=" + goodsColorStyleId + ", goodsName="
				+ goodsName + ", goodsSmallUrl=" + goodsSmallUrl
				+ ", colorName=" + colorName + ", price=" + price
				+ ", commentId=" + commentId + "]";
	}


	
}	
