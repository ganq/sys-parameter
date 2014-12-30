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
public class IdGenerationServiceTest extends BaseTestCase {
	
	private static final Logger logger = Logger
			.getLogger(IdGenerationServiceTest.class);

	@Autowired
	private IdGenerationService idGenerationService;

	@Test
	public void testGetNextId()  {
		logger.info("*****testGetNextId begin*****");
		long id = this.idGenerationService.getNextId("temp.lcz_testid");
		logger.info("*****testGetNextId id="+id+"   *****");
		logger.info("*****testGetNextId end*****");		 
	}
	
	@Test
	public void testGetMaxIdWithNum()  {
		logger.info("*****testGetMaxIdWithNum begin*****");
		long maxId = this.idGenerationService.getMaxIdWithNum("temp.lcz_testid", 5);
		logger.info("*****testGetMaxIdWithNum maxId="+maxId+"   *****");
		logger.info("*****testGetMaxIdWithNum end*****");		 
	}
}
/*
UPDATE `b2b_parameter`.`bsp_id_generation`
SET
`table_id` = table_id+1
WHERE `table_name` = 'temp.lcz_testid';

SELECT * FROM b2b_parameter.bsp_id_generation where table_name='temp.lcz_testid';

*/