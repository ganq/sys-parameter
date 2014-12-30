/**
 * 
 */
package com.mysoft.b2b.basicsystem.settings.provider;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mysoft.b2b.basicsystem.settings.test.BaseTestCase;
import com.mysoft.b2b.bizsupport.api.IdGenerationService;

/**
 * @author liucz
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class IdGenerationServiceTest2 extends BaseTestCase {
	
	private static final Logger logger = Logger
			.getLogger(DictionaryServiceTest.class);

	@Autowired
	private IdGenerationService idGenerationService;

	@Test
	public void testGetNextId()  {
		logger.info("2*****testGetNextId begin*****");
		long id = this.idGenerationService.getNextId("temp.lcz_testid");
		logger.info("2*****testGetNextId id="+id+"   *****");
		logger.info("2*****testGetNextId end*****");		 
	}
	
	@Test
	public void testGetMaxIdWithNum()  {
		logger.info("2*****testGetMaxIdWithNum begin*****");
		long maxId = this.idGenerationService.getMaxIdWithNum("temp.lcz_testid", 5);
		logger.info("2*****testGetMaxIdWithNum maxId="+maxId+"   *****");
		logger.info("2*****testGetMaxIdWithNum end*****");		 
	}
}
