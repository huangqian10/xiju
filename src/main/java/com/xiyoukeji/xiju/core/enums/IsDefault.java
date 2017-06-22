package com.xiyoukeji.xiju.core.enums;

public enum IsDefault {
	DEFAULT(1), NODEFAULT(0);
	private Integer value;

	IsDefault(Integer value) {
		this.value = value;
	}

	public Integer value() {
		return this.value;
	}
}
