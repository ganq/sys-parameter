package com.mysoft.b2b.basicsystem.settings.provider;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.ServerAddress;
import com.mysoft.b2b.basicsystem.settings.api.MongoDBService;

/**
 * MONGODB服务
 * 
 * @author liucz
 * 
 */
public class MongoDBServiceImpl implements MongoDBService {
	private static final Log log = LogFactory.getLog(MongoDBServiceImpl.class);

	private String adds;
	private String databaseName;
	private String userName;
	private String password;

	private static DB db;
	private String address;
	private int port;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setAdds(String adds) {
		this.adds = adds;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public DB getDb() {
		return db;
	}

	public void creatMongoDb() {
		List<ServerAddress> addr = new ArrayList<ServerAddress>();
		try {
			String [] _adds = adds.split(",");
			for(String _path : _adds){
				addr.add(new ServerAddress(_path));
			}			
			Mongo mongo = new Mongo(addr);
		//try {
			//Mongo mongo = new Mongo(address, port);
			db = mongo.getDB(databaseName);
			if (!db.authenticate(userName, password.toCharArray())) {
				log.info("MongoDb验证失败！ ");
			}
		} catch (Exception e) {
			log.error("creatMongoDb error ", e);
		}
	}
}
