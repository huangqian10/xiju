package com.xiyoukeji.xiju.domain;

import com.xiyoukeji.xiju.model.Cart;

public class CartShow {
	private Cart cart;
	private Integer price;
	private String leftEnough;
	
	
	public String getLeftEnough() {
		return leftEnough;
	}
	public void setLeftEnough(String leftEnough) {
		this.leftEnough = leftEnough;
	}
	public Cart getCart() {
		return cart;
	}
	public void setCart(Cart cart) {
		this.cart = cart;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
}
