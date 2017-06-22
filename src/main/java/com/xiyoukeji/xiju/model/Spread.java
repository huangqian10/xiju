package com.xiyoukeji.xiju.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="spread")
public class Spread {
	@Id
	@GeneratedValue(generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "id", length = 11)
	private Integer id;
	
	@Column(name="spread_url",length=255)
	private String spreadUrl;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSpreadUrl() {
		return spreadUrl;
	}

	public void setSpreadUrl(String spreadUrl) {
		this.spreadUrl = spreadUrl;
	}
	
	
}
