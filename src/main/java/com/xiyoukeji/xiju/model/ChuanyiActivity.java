package com.xiyoukeji.xiju.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="chuanyi_activity")
public class ChuanyiActivity {
	@Id
	@GeneratedValue(generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "id", length = 11)
	private Integer id;
	
	@Column(name="chuanyi_name",length=255)
	private String chuanyiName;
	
	@Column(name="chuanyi_content",length=255)
	private String chuanyiContent;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getChuanyiName() {
		return chuanyiName;
	}

	public void setChuanyiName(String chuanyiName) {
		this.chuanyiName = chuanyiName;
	}

	public String getChuanyiContent() {
		return chuanyiContent;
	}

	public void setChuanyiContent(String chuanyiContent) {
		this.chuanyiContent = chuanyiContent;
	}
	
}
