package com.xiyoukeji.xiju.domain;

import java.util.List;

import com.xiyoukeji.xiju.model.Content;
import com.xiyoukeji.xiju.model.FaxianActivity;

public class FaxianActivityShow {
	private FaxianActivity faxianActivity;

	private List<GoodsListShow> goodsListShow;

	private List<Content> content;

	public FaxianActivityShow(FaxianActivity faxianActivity,
			List<GoodsListShow> goodsListShow, List<Content> content) {
		this.faxianActivity = faxianActivity;
		this.goodsListShow = goodsListShow;
		this.content = content;
	}

	public List<Content> getContent() {
		return content;
	}

	public void setContent(List<Content> content) {
		this.content = content;
	}

	public FaxianActivity getFaxianActivity() {
		return faxianActivity;
	}

	public void setFaxianActivity(FaxianActivity faxianActivity) {
		this.faxianActivity = faxianActivity;
	}

	public List<GoodsListShow> getGoodsListShow() {
		return goodsListShow;
	}

	public void setGoodsListShow(List<GoodsListShow> goodsListShow) {
		this.goodsListShow = goodsListShow;
	}

}
