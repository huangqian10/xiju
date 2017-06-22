/**
 * 
 */
package com.xiyoukeji.xiju.core.enums;

/**
 * @author hq
 *
 */
public enum PromotionCodeEnum {
	UNUSED(1),USED(2),OVERDUE(3);
	
	private Integer value;

	PromotionCodeEnum(Integer value) {
		this.value = value;
	}

	public Integer value() {
		return this.value;
	}
}
