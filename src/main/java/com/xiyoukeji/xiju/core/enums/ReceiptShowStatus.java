package com.xiyoukeji.xiju.core.enums;

public enum ReceiptShowStatus {
	NOTPAY(1), PAID(2), USELESS(3), NEED_COMMENT(4), FINISH(5);

	private Integer value;

	ReceiptShowStatus(Integer value) {
		this.value = value;
	}

	public Integer value() {
		return value;
	}
}
