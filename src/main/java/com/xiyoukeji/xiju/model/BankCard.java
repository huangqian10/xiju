/**
 * 
 */
package com.xiyoukeji.xiju.model;

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
@Table(name="bank_card")
public class BankCard {
	
	@Id
	@GeneratedValue(generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "id", length = 11)
	private Integer id;
	
	@Column(name="owner" ,length = 255)
	private String owner;  //提卡人
	
	
	@Column(name="open_number" ,length = 255)
	private String openNumber;  //开户号
	
	@Column(name="open_bank" ,length = 255)
	private String openBank;  //开户号
	
	
	@Column(name="cellphone" ,length = 255)
	private String cellphone;  //手机号
	
	@Column(name="isDefault" ,length = 4)
	private Integer is_default;  //手机号
	
	@Column(name="ctime",length=20)
	private long ctime=System.currentTimeMillis();
	
	
	

}
