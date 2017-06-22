package com.xiyoukeji.xiju.core.enums;

public enum GoodsStatus {
	ONLINE(1), OFFLINE(2);

	private Integer value;

	GoodsStatus(Integer value) {
		this.value = value;
	}

	public Integer value() {
		return this.value;
	}
}
