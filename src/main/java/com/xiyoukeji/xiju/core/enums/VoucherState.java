package com.xiyoukeji.xiju.core.enums;

/**
 * 
 * 红包状态
 *
 */
public enum VoucherState {
	USEFUL(1), USED(2), USELESS(3);

	private Integer value;

	private VoucherState(Integer value) {
		this.value = value;
	}

	public Integer value() {
		return this.value;
	}
}
