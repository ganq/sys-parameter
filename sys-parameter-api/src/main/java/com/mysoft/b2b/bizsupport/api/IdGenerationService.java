/**
 * 
 */
package com.mysoft.b2b.bizsupport.api;

/**
 * ID生成服务
 * @author liucz
 *
 */
public interface IdGenerationService {
	/**
	 * 根据表名获取下一个可用的ID
	 * @param tableName
	 * @return
	 */
	long getNextId(String tableName);
	
	/**
	 * 根据表名和ID个数获取最大的可用ID
	 * @param tableName
	 * @param num
	 * @return
	 */
	long getMaxIdWithNum(String tableName, int num);

}
