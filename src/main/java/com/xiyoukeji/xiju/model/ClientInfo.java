package com.xiyoukeji.xiju.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="client_info")
public class ClientInfo {
	@Id
	@GeneratedValue(generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "id", length = 11)
	private Integer id;
	
	@Column(name="kf_mobile",length=63)
	private String kfMobile;
	
	@Column(name="user_agreement",length=255)
	private String userAgreement;
	
	@Column(name="weibo",length=255)
	private String weibo;
	
	@Column(name="xiju_intro",length=255)
	private String xijuIntro;
	
	@Column(name="wx_pub_pic",length=255)
	private String wxPubPic;
	
	@Column(name="wx_pub_url",length=255)
	private String wxPubUrl;
	
	@Column(name="spreadUrl",length=255)
	private String spreadUrl;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getKfMobile() {
		return kfMobile;
	}

	public void setKfMobile(String kfMobile) {
		this.kfMobile = kfMobile;
	}

	public String getUserAgreement() {
		return userAgreement;
	}

	public void setUserAgreement(String userAgreement) {
		this.userAgreement = userAgreement;
	}

	public String getWeibo() {
		return weibo;
	}

	public void setWeibo(String weibo) {
		this.weibo = weibo;
	}

	public String getXijuIntro() {
		return xijuIntro;
	}

	public void setXijuIntro(String xijuIntro) {
		this.xijuIntro = xijuIntro;
	}

	public String getWxPubPic() {
		return wxPubPic;
	}

	public void setWxPubPic(String wxPubPic) {
		this.wxPubPic = wxPubPic;
	}

	public String getWxPubUrl() {
		return wxPubUrl;
	}

	public void setWxPubUrl(String wxPubUrl) {
		this.wxPubUrl = wxPubUrl;
	}

	public String getSpreadUrl() {
		return spreadUrl;
	}

	public void setSpreadUrl(String spreadUrl) {
		this.spreadUrl = spreadUrl;
	}	
}
