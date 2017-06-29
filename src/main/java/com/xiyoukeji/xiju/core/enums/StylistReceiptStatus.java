/**
 * 
 */
package com.xiyoukeji.xiju.core.enums;

/**
 * @author hq
 *
 */
public enum StylistReceiptStatus {
	RECEIVING(1),COMPLETE(2);
	
	private Integer value;
	StylistReceiptStatus(Integer value){
		this.value=value;
	}
	
	public Integer value(){
		return this.value;
	}
}
