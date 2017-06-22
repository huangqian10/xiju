package com.xiyoukeji.xiju.core.enums;

/**
 * 
 * 活动类型：选购，创意，发现
 *
 */
public enum ActivityType {
	XUANGOU(1), CHUANYI(2), FAXIAN(3);

	Integer value;

	ActivityType(Integer value) {
		this.value = value;
	}

	public Integer value() {
		return this.value;
	}
}
