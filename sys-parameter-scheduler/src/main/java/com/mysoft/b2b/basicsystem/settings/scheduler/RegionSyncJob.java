package com.mysoft.b2b.basicsystem.settings.scheduler;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mysoft.b2b.basicsystem.settings.api.DictionaryService;
import com.mysoft.b2b.commons.scheduler.MysoftJob;

public class RegionSyncJob extends MysoftJob {
	protected Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private DictionaryService dictionaryService;

	@Override
	public void run() {
		logger.info("同步区域数据到mysql开始");
		dictionaryService.syncRegionDataToMysqlDB();
		logger.info("同步区域数据到mysql结束");
	}

}
