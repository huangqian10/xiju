package com.xiyoukeji.xiju.core.enums;

public enum CommentType {
	CHUNAYI(1), GOOD(2);

	private Integer value;

	CommentType(Integer value) {
		this.value = value;
	}

	public Integer value() {
		return this.value;
	}
}
