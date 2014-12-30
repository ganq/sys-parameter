package com.mysoft.b2b.basicsystem.settings.api;

import java.io.Serializable;
/**
 * 开发资质模型类
 * @author pengym
 *
 */
public class DevelopQualificationType implements Serializable{
	
	private static final long serialVersionUID = 7945744985636703263L;
	
	/**
	 * 编码
	 */
	private String code;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 排序
	 */
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
	public String getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(String displayOrder) {
		this.displayOrder = displayOrder;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "DevelopQualification{name="+name+",code="+code+"}";
	}
	
}
