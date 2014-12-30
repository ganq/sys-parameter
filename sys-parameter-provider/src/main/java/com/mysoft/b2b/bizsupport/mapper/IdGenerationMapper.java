package com.mysoft.b2b.bizsupport.mapper;

import org.springframework.stereotype.Component;

import com.mysoft.b2b.bizsupport.api.IdGeneration;

@Component("idGenerationMapper")
public interface IdGenerationMapper {	
	IdGeneration getIdGenerationByName(String tableName);
	
	void saveIdGeneration(IdGeneration idGeneration);

	void updateIdGeneration(IdGeneration idGeneration);	
}
