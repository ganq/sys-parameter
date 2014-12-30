package com.mysoft.b2b.basicsystem.settings.api;

import java.io.Serializable;
/**
 * 开发商业务领域类型类
 * @author pengym
 *
 */
public class DeveloperBusinessArea implements Serializable{

	private static final long serialVersionUID = -4522390750859063888L;
	
	/**
	 * 编码
	 */
	private String code;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 状态
	 */
	private int status;
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
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "DeveloperBusinessArea{name="+name+",code="+code+",dispalyOrder="+displayOrder+""
				+ ",status"+status
				+ "}";
	}
	
}
