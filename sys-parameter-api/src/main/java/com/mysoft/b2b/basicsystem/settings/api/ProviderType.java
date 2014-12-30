package com.mysoft.b2b.basicsystem.settings.api;

import java.io.Serializable;

/**
 * （云平台）数据字典--公司类别
 * 
 * @author liucz
 * Comparable<ProviderType>, 
 */
public class ProviderType implements Serializable {
	private static final long serialVersionUID = -1654068870896196561L;
	// 名称
	private String name;
	// 代码
	private String code;
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

	public String getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(String displayOrder) {
		this.displayOrder = displayOrder;
	}

	public String toString() {
		return code + "-" + name;
	}

	/*public int compareTo(ProviderType o) {
		return (this.displayOrder.compareTo(o.displayOrder));
	}*/
}
