/**
 * 
 */
package com.xiyoukeji.xiju.model;

import javax.enterprise.inject.New;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author hq
 *
 */
@Entity
@Table(name="stylist")
public class Stylist {
	@Id
	@GeneratedValue(generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "id", length = 11)
	private Integer id;
	
	@Column(name="user_id" ,length = 11)
	private Integer userId;  //用户id
	
	
	@Column(name="certification_status" ,length = 4)
	private int certificationStatus;  //认证状态,0为未认证,1为认证中,2为认证未通过,3为已通过
	
	
	@Column(name="stylist_card_url",length=255)
	private String stylistCardUrl;     //设计师名片
	
	
	@Column(name="stylist_certificate_url",length=255)
	private String stylistCertificateUrl;     //设计师证书
	
	
	@Column(name="business_license_url",length=255)
	private String businessLicenseUrl;             //营业执照
	
	@Column(name="ctime",length=20)				
	private Long ctime=System.currentTimeMillis();     //记录生成的时间

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getCertificationStatus() {
		return certificationStatus;
	}

	public void setCertificationStatus(Integer certificationStatus) {
		this.certificationStatus = certificationStatus;
	}

	public String getStylistCardUrl() {
		return stylistCardUrl;
	}

	public void setStylistCardUrl(String stylistCardUrl) {
		this.stylistCardUrl = stylistCardUrl;
	}

	public String getStylistCertificateUrl() {
		return stylistCertificateUrl;
	}

	public void setStylistCertificateUrl(String stylistCertificateUrl) {
		this.stylistCertificateUrl = stylistCertificateUrl;
	}

	public String getBusinessLicenseUrl() {
		return businessLicenseUrl;
	}

	public void setBusinessLicenseUrl(String businessLicenseUrl) {
		this.businessLicenseUrl = businessLicenseUrl;
	}

	public Long getCtime() {
		return ctime;
	}

	public void setCtime(Long ctime) {
		this.ctime = ctime;
	}
	
	
	

}
