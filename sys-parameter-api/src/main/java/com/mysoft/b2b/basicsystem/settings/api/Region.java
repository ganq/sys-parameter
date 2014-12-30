package com.mysoft.b2b.basicsystem.settings.api;

import java.io.Serializable;

/**
 * （云平台）数据字典--区域
 * 
 * @author liucz
 * 
 */
public class Region implements Serializable {
	private static final long serialVersionUID = 5899590825071237631L;
	// 名称
	private String name;
	// 代码
	private String code;
	// 父级代码
	private String parentCode;
	// 是否末级
	private int isLeaf;
	// 级别
	private int hierarchyLevel;
	// 排序编号
	private String displayOrder;
	// 片区
	private String area;

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

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

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public int getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(int isLeaf) {
		this.isLeaf = isLeaf;
	}

	public int getHierarchyLevel() {
		return hierarchyLevel;
	}

	public void setHierarchyLevel(int hierarchyLevel) {
		this.hierarchyLevel = hierarchyLevel;
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
	
}
