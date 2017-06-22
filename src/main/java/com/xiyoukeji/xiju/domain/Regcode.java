package com.xiyoukeji.xiju.domain;

import java.io.Serializable;

public class Regcode implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -679104858126153931L;

	private String registCode;
	
	private Integer sendTime;
	public Regcode(String registCode,Integer sendTime){
		this.registCode=registCode;
		this.sendTime=sendTime;
	}
	public String getRegistCode() {
		return registCode;
	}
	public void setRegistCode(String registCode) {
		this.registCode = registCode;
	}
	public Integer getSendTime() {
		return sendTime;
	}
	public void setSendTime(Integer sendTime) {
		this.sendTime = sendTime;
	}
	@Override
	public String toString() {
		return "Regcode [registCode=" + registCode + ", sendTime=" + sendTime
				+ "]";
	}

	
}