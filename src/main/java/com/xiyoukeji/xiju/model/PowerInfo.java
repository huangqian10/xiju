package com.xiyoukeji.xiju.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="power_info")
public class PowerInfo {
	@Id
	@GeneratedValue(generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "id", length = 11)
	private Integer id;
	
	@Column(name="power_name",length=255)
	private String powerName;
	
	@Column(name="url_info_ids",length=65535)
	private String urlInfoIds;
	

	
	public String getUrlInfoIds() {
		return urlInfoIds;
	}

	public void setUrlInfoIds(String urlInfoIds) {
		this.urlInfoIds = urlInfoIds;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPowerName() {
		return powerName;
	}

	public void setPowerName(String powerName) {
		this.powerName = powerName;
	}
	
	
}
