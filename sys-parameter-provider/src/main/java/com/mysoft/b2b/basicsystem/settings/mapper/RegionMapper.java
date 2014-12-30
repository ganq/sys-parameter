/**
 * Copyright mysoft Limited (c) 2014. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of mysoft Limited. Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from mysoft or an authorized sublicensor.
 */
package com.mysoft.b2b.basicsystem.settings.mapper;

import java.util.List;

import com.mysoft.b2b.basicsystem.settings.api.Region;

/**
 * chengp: Change to the actual description of this class
 * 
 * @version Revision History
 * 
 *          <pre>
 * Author     Version       Date        Changes
 * subh    1.0           2014年8月13日     Created
 * 
 * </pre>
 * @since b2b 2.0.0
 */

public interface RegionMapper {

	/**
	 * 批量插入区域数据
	 * 
	 * @param regionList
	 */
	void insertRegion(List<Region> regionList);

	/**
	 * 删除全部区域数据
	 * 
	 * @return
	 */
	int deleteAllRegion();

}
