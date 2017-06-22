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
@Table(name="withdrawal_proportion")
public class WithdrawalProportion {
	
	@Id
	@GeneratedValue(generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "id", length = 11)
	private Integer id;
	
	
	@Column(name = "proportion", length = 4,nullable=false,columnDefinition="INT default 0")
	private Integer proportion;  //提现比例
	
	@Column(name="ctime",length=20)
	private long  ctime;  //创建时间
	
	@Column(name="utime",length=20)
	private long utime;   //修改时间

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProportion() {
		return proportion;
	}

	public void setProportion(Integer proportion) {
		this.proportion = proportion;
	}

	public long getCtime() {
		return ctime;
	}

	public void setCtime(long ctime) {
		this.ctime = ctime;
	}

	public long getUtime() {
		return utime;
	}

	public void setUtime(long utime) {
		this.utime = utime;
	}
	
	
	
	
	
	
	
	
	

}
