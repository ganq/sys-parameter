/**
 * 
 */
package com.mysoft.b2b.bizsupport.provider;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.mysoft.b2b.bizsupport.api.IdGeneration;
import com.mysoft.b2b.bizsupport.api.IdGenerationService;
import com.mysoft.b2b.bizsupport.mapper.IdGenerationMapper;

/**
 * @author liucz
 *
 */
@Service("idGenerationService")
public class IdGenerationServiceImpl implements IdGenerationService {
	private static final Logger log = Logger.getLogger(IdGenerationServiceImpl.class);
	
	@Autowired
	private IdGenerationMapper idGenerationMapper;
	/**
	 * 根据表名获取下一个可用的ID
	 * @param tableName
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED) 
	public long getNextId(String tableName) {
		//判断表示是否为空
		if(tableName ==null || tableName.equals("")){
			log.info("空表名数据", new RuntimeException("表名不能为空"));
			return 0L;
		}
		IdGeneration idGeneration = idGenerationMapper.getIdGenerationByName(tableName);		
		boolean isNew = false;
		if(idGeneration==null){
			idGeneration = new IdGeneration();
			idGeneration.setTableName(tableName);
			isNew = true;
		}
		idGeneration.setTableId(idGeneration.getTableId()+1);
		if(!isNew){
			idGenerationMapper.updateIdGeneration(idGeneration);
		} else {
			idGenerationMapper.saveIdGeneration(idGeneration);
		}
		long tableId =idGeneration.getTableId();
		log.info("************** "+tableName +" tableId="+ tableId +" ****************************** ");
		return tableId;
	}

	/**
	 * 根据表名和ID个数获取最大的可用ID
	 * @param tableName
	 * @param num
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED) 
	public long getMaxIdWithNum(String tableName, int num) {
		IdGeneration idGeneration = idGenerationMapper.getIdGenerationByName(tableName);
		boolean isNew = false;
		if(idGeneration==null){
			idGeneration = new IdGeneration();
			idGeneration.setTableName(tableName);
			isNew = true;
		}
		idGeneration.setTableId(idGeneration.getTableId()+num);
		if(!isNew){
			idGenerationMapper.updateIdGeneration(idGeneration);
		} else {
			idGenerationMapper.saveIdGeneration(idGeneration);
		}
		return idGeneration.getTableId();
	}

}
