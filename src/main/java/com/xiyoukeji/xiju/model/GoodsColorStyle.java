package com.xiyoukeji.xiju.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.alibaba.fastjson.JSONObject;

@Entity
@Table(name="goods_color_style")
public class GoodsColorStyle {
	@Id
	@GeneratedValue(generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "id", length = 11)
	private Integer id;
	
	@Column(name="goods_id",length=11)
	private Integer goodsId;
	
	@Column(name="color_id",length=11)
	private Integer colorId;
	
	@Column(name="style_id",length=11)
	private Integer styleId;
	
	@Column(name="pic_url",length=255)
	private String picUrl;
	/**
	 * 剩余库存
	 */
	
	@Column(name="left_count",length=11)
	private Integer leftCount;
	
	@Column(name="price",length=11)
	private Integer price;
	
	@Column(name="sale_price",length=11)
	private Integer salePrice;
	
	@Column(name="sid",length=255)
	private String sid;
	
	
	
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

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public Integer getLeftCount() {
		return leftCount;
	}

	public void setLeftCount(Integer leftCount) {
		this.leftCount = leftCount;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public Integer getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}
	public static void main(String[] args) {
		JSONObject jv = new JSONObject();
		List<GoodsColorStyle> list = new ArrayList<GoodsColorStyle>();
		GoodsColorStyle goodsColorStyle = new GoodsColorStyle();
		goodsColorStyle.setColorId(1);
		goodsColorStyle.setLeftCount(10);
		goodsColorStyle.setPicUrl("picurl");
		goodsColorStyle.setPrice(10000);
		goodsColorStyle.setSalePrice(999);
		goodsColorStyle.setSid("sid111");
		goodsColorStyle.setStyleId(1);
		list.add(goodsColorStyle);
		
		GoodsColorStyle goodsColorStyle2 = new GoodsColorStyle();
		goodsColorStyle2.setColorId(1);
		goodsColorStyle2.setLeftCount(10);
		goodsColorStyle2.setPicUrl("picurl");
		goodsColorStyle2.setPrice(10000);
		goodsColorStyle2.setSalePrice(999);
		goodsColorStyle2.setSid("sid111");
		goodsColorStyle2.setStyleId(1);
		list.add(goodsColorStyle2);
		jv.put("goodsStyleJson", list);
		System.out.println(jv.toJSONString());
	}
	
}
