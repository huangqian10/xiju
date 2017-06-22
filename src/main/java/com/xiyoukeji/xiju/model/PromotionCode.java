/**
 * 
 */
package com.xiyoukeji.xiju.model;

import java.util.jar.Attributes.Name;

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
@Table(name="promotion_code")
public class PromotionCode {
	@Id
	@GeneratedValue(generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "id", length = 11)
	private Integer id;
	
	
	@Column(name="user_id" ,length = 11)
	private Integer userId;  //用户id
	
	@Column(name = "name", length = 255)
	private String name;              //客户姓名
	
	@Column(name = "cellphone", length = 30)
	private String cellphone;		//客服手机
	
	@Column(name="promotion_code",length=255)
	private String promotionCode;	//优惠码
	
	@Column(name="address",length=255)
	private String address;	//地址
	
	@Column(name="community",length=255)
	private String community;	//小区
	

	
	@Column(name="status",length=4)
	private Integer status;	//状态   1为未使用 2为已使用 3为过期(过期以结束时间判断)
	
	
	@Column(name="start_time",length=20)
	private long startTime;	//开始时间
	
	
	@Column(name="end_time",length=20)
	private long endTime;	//结束时间
	
	
	@Column(name="ctime",length=20)        
	private Long ctime=System.currentTimeMillis();   //记录生成的时间


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


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getCellphone() {
		return cellphone;
	}


	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}


	public String getPromotionCode() {
		return promotionCode;
	}


	public void setPromotionCode(String promotionCode) {
		this.promotionCode = promotionCode;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getCommunity() {
		return community;
	}


	public void setCommunity(String community) {
		this.community = community;
	}





	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	public long getStartTime() {
		return startTime;
	}


	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}


	public long getEndTime() {
		return endTime;
	}


	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}


	public Long getCtime() {
		return ctime;
	}


	public void setCtime(Long ctime) {
		this.ctime = ctime;
	}



	
	
}
