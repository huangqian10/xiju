package com.xiyoukeji.xiju.domain;

import com.xiyoukeji.xiju.model.Params;

public class GoodsParamsShow {

	private String icon;
	private String name;
	private String content;
	
	public GoodsParamsShow(Params params,String content){
		this.icon=params.getIcon();
		this.name=params.getName();
		this.content=content;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
