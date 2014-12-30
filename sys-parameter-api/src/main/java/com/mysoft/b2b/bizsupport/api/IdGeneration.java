/**
 * 
 */
package com.mysoft.b2b.bizsupport.api;

import java.io.Serializable;

/**
 * @author liucz
 *
 */
public class IdGeneration implements Serializable {
	private static final long serialVersionUID = 64502560045862509L;

	/**
	 * 表名 
	 */
	private String tableName;
	
	/**
	 * 当前ID
	 */
	private long tableId;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public long getTableId() {
		return tableId;
	}

	public void setTableId(long tableId) {
		this.tableId = tableId;
	}

	
}
