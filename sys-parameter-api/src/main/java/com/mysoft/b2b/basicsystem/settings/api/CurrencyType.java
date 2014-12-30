/**
 * 
 */
package com.mysoft.b2b.basicsystem.settings.api;

import java.io.Serializable;

/**
 * @author pengym
 *
 */
public class CurrencyType  implements Serializable {
	
	private static final long serialVersionUID = -944614807172809827L;
	/**
	 * 编码
	 */
	private String code;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 汇率
	 */
	private Double rate;
	/**
	 * 状态（停用:0、启用:1）
	 */
	private int status;
	// 排序
	private String displayOrder;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public String getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(String displayOrder) {
		this.displayOrder = displayOrder;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "CurrencyType{code="+code+", name="+name+",rate="+rate+",status="+status+"}";
	}
	
}
