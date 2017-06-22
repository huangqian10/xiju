package com.xiyoukeji.xiju.core.enums;
/**
 * 
 * 订单状态
 *
 */
public enum ReceiptStatus {
	NOTPAY(1),PAID(2),USELESS(3);
	
	private Integer value;
	ReceiptStatus(Integer value){
		this.value=value;
	}
	
	public Integer value(){
		return this.value;
	}
	
}
