package com.mysoft.b2b.basicsystem.settings.mapper;

import java.io.Serializable;

/**
 * （云平台）数据字典--通用数据字典对象
 * 
 * @author liucz
 * 
 */
public class ItemDO implements Serializable {
	private static final long serialVersionUID = 1286477958934791876L;
	// ID
	private String itemId;
	// 类型名称
	private String itemType;
	// 名称
	private String itemName;
	// 代码
	private String itemCode;
	// 参数值
	private String itemValue;
	// 代码
	private String parentItemCode;
	// 排序
	private String displayOrder;
	// 最后修改时间
	private String lastModifiedTime;
	// 状态
	private int itemStatus;

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemValue() {
		return itemValue;
	}

	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}

	public String getParentItemCode() {
		return parentItemCode;
	}

	public void setParentItemCode(String parentItemCode) {
		this.parentItemCode = parentItemCode;
	}

	public String getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(String lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	public int getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(int itemStatus) {
		this.itemStatus = itemStatus;
	}

	public String getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(String displayOrder) {
		this.displayOrder = displayOrder;
	}

	public String toString() {
		return itemCode + "-" + itemName;
	}
}
