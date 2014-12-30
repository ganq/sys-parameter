package com.mysoft.b2b.basicsystem.settings.api;

import java.io.Serializable;

/**
 * （云平台）数据字典--系统参数
 * 
 * @author liucz
 * Comparable<SystemParameter>,
		
 */
public class SystemParameter implements Serializable {
	private static final long serialVersionUID = -5265408197127250220L;
	// 名称
	private String name;
	// 代码
	private String code;
	// 参数值
	private String value;
	// 排序
	private String displayOrder;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(String displayOrder) {
		this.displayOrder = displayOrder;
	}

	public String toString() {
		return code + "-" + name;
	}

	/*public int compareTo(SystemParameter o) {
		return (this.displayOrder.compareTo(o.displayOrder));
	}*/
}
