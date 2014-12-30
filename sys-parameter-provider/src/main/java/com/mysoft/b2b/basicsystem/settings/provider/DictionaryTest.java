package com.mysoft.b2b.basicsystem.settings.provider;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.util.JSON;

public class DictionaryTest {

	private static final String COLLECTION_NAME = "infr_dictionary";
	private static final String ITEM_ID = "_id";
	private static final String ITEM_TYPE = "item_type";
	private static final String ITEM_CODE = "item_code";
	private static final String ITEM_NAME = "item_name";
	private static final String ITEM_VALUE = "item_value";
	private static final String ITEM_STATUS = "item_status";
	private static final String PARENT_ITEM_CODE = "parent_item_code";
	private static final String LAST_MODIFIED_TIME = "last_modified_time";
	private static final String DISPLAY_ORDER = "display_order";
	
	private static java.text.DateFormat format1 = new java.text.SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	public static void main(String[] args) throws UnknownHostException,
			MongoException {
		// TODO Auto-generated method stub
		init();
		findSystemParameters();
		// updateSystemParameters();
		// findAll();
		// findByWhere();
		// updateByWhere();

	}

	public static void print(Object o) {
		System.out.println(o);
	}

	private static void updateSystemParameters() throws UnknownHostException {
		BasicDBObject systemParameter = new BasicDBObject();
		// systemParameter.put("_id", new ObjectId().toString());
		systemParameter.put("item_type", "system_parameter");
		systemParameter.put("item_name", "超时80");
		systemParameter.put("item_code", "timeout80");
		systemParameter.put("item_value", "50");
		// systemParameter.put("parent_item_code", "liucz");
		systemParameter.put("last_modified_time", format1.format(new Date()));
		systemParameter.put("display_order", "3");
		systemParameter.put("ITEM_STATUS, 1", "1");
		
		DBCollection dics = getDics();

		BasicDBObject o;
		DBCursor cur;
		print("**********find item_code=\"timeouts\"**************");
		o = new BasicDBObject();
		o.put("item_type", "system_parameter");
		o.put("item_code", "timeout80");
		cur = dics.find(o);
		BasicDBObject row;
		if (cur.count() > 0) {
			while (cur.hasNext()) {
				row = (BasicDBObject) cur.next();
				print(row);
				row.put("item_value", "80");
				dics.save(row);
			}
		} else {
			dics.insert(systemParameter);
		}
		
		print("**********update item_code=\"timeout80\"**************");
		o = new BasicDBObject();
		o.put("item_type", "system_parameter");
		o.put("item_code", "timeout80");
		cur = dics.find(o);
		while (cur.hasNext()) {
			row = (BasicDBObject) cur.next();
			print(row);
		}
		print("**********delete item_code=\"timeout80\"**************");
		o = new BasicDBObject();
		o.put("item_type", "system_parameter");
		o.put("item_code", "timeout80");
		dics.remove(o);
		cur = dics.find(o);
		while (cur.hasNext()) {
			row = (BasicDBObject) cur.next();
			print(row);
		}
		
	}

	private static void findSystemParameters() throws UnknownHostException {		
		DBCollection dics = getDics();

		// 查询数据
		print("**********findAllSystemParameters**************");
		BasicDBObject o = new BasicDBObject();
		o.put("item_type", "system_parameter");
		DBCursor cur = dics.find(o);
		while (cur.hasNext()) {
			System.out.println(cur.next());
		}

		print("**********find item_code=\"timeout\"**************");
		o = new BasicDBObject();
		o.put("item_type", "system_parameter");
		o.put("item_code", "timeout");
		cur = dics.find(o);
		while (cur.hasNext()) {
			print(cur.next());
		}
	}

	private static void updateByWhere() throws UnknownHostException {
		Mongo mg = new Mongo();
		// 查询所有的Database
		for (String name : mg.getDatabaseNames()) {
			System.out.println("dbName: " + name);
		}

		DB db = mg.getDB("b2b_infrastructure");

		DBCollection dics = db.getCollection("infr_dictionary");
		dics.drop();

		// 查询数据
		BasicDBObject o = new BasicDBObject();
		o.put("name", "liucz");
		o.put("age", 28);
		dics.update(o, new BasicDBObject("name", "update liucz"), true,// 如果数据库不存在，是否添加
				false// false只修改第一天，true如果有多条就不修改
		);
		DBCursor cur = dics.find(o);
		while (cur.hasNext()) {
			System.out.println(cur.next());
		}
		System.out.println(cur.count());
		System.out.println(cur.getCursorId());
		System.out.println(JSON.serialize(cur));
	}

	private static void findAll() throws UnknownHostException {
		Mongo mg = new Mongo();
		// 查询所有的Database
		for (String name : mg.getDatabaseNames()) {
			System.out.println("dbName: " + name);
		}

		DB db = mg.getDB("b2b_infrastructure");
		// 查询所有的聚集集合
		for (String name : db.getCollectionNames()) {
			System.out.println("collectionName: " + name);
		}

		DBCollection dics = db.getCollection("infr_dictionary");

		// 查询所有的数据
		DBCursor cur = dics.find();
		while (cur.hasNext()) {
			System.out.println(cur.next());
		}
		System.out.println(cur.count());
		System.out.println(cur.getCursorId());
		System.out.println(JSON.serialize(cur));
	}

	private static void init() throws UnknownHostException {
		DBCollection dics = getDics();
		dics.drop();

		List<DBObject> list = new ArrayList<DBObject>();		
		BasicDBObject systemParameter = new BasicDBObject();
		// systemParameter.put("_id", new ObjectId().toString());
		systemParameter.put("item_type", "system_parameter");
		systemParameter.put("item_name", "登录失败次数");
		systemParameter.put("item_code", "loginfailtimes");
		systemParameter.put("item_value", "3");
		// systemParameter.put("parent_item_code", "liucz");
		systemParameter.put("last_modified_time", format1.format(new Date()));
		systemParameter.put("display_order", "1");
		systemParameter.put(ITEM_STATUS, 1);
		list.add(systemParameter);
		systemParameter = new BasicDBObject();
		// systemParameter.put("_id", new ObjectId().toString());
		systemParameter.put("item_type", "system_parameter");
		systemParameter.put("item_name", "超时");
		systemParameter.put("item_code", "timeout");
		systemParameter.put("item_value", "30");
		// systemParameter.put("parent_item_code", "liucz");
		systemParameter.put("last_modified_time", format1.format(new Date()));
		systemParameter.put("display_order", "2");
		systemParameter.put(ITEM_STATUS, 1);
		list.add(systemParameter);
		

		BasicDBObject region = new BasicDBObject();
		// region.put("_id", new ObjectId().toString());
		region.put("item_type", "region");
		region.put("item_name", "华东");
		region.put("item_code", "HUADONG");
		// region.put("item_value", "liucz");
		region.put("parent_item_code", "");
		region.put("last_modified_time", format1.format(new Date()));
		region.put("display_order", "2");
		region.put(ITEM_STATUS, 1);
		list.add(region);
		
		region = new BasicDBObject();
		// region.put("_id", new ObjectId().toString());
		region.put("item_type", "region");
		region.put("item_name", "上海");
		region.put("item_code", "SH");
		// region.put("item_value", "liucz");
		region.put("parent_item_code", "HUADONG");
		region.put("last_modified_time", format1.format(new Date()));
		region.put("display_order", "2");
		region.put(ITEM_STATUS, 1);
		list.add(region);
		
		region = new BasicDBObject();
		// region.put("_id", new ObjectId().toString());
		region.put("item_type", "region");
		region.put("item_name", "浙江");
		region.put("item_code", "ZHEJIANG");
		// region.put("item_value", "liucz");
		region.put("parent_item_code", "HUADONG");
		region.put("last_modified_time", format1.format(new Date()));
		region.put("display_order", "2");
		region.put(ITEM_STATUS, 1);
		list.add(region);
		
		region = new BasicDBObject();
		// region.put("_id", new ObjectId().toString());
		region.put("item_type", "region");
		region.put("item_name", "上海2");
		region.put("item_code", "SH2");
		// region.put("item_value", "liucz");
		region.put("parent_item_code", "HUADONG");
		region.put("last_modified_time", format1.format(new Date()));
		region.put("display_order", "1");
		region.put(ITEM_STATUS, 0);
		list.add(region);
		
		region = new BasicDBObject();
		// region.put("_id", new ObjectId().toString());
		region.put("item_type", "region");
		region.put("item_name", "浦东");
		region.put("item_code", "PUDONG");
		// region.put("item_value", "liucz");
		region.put("parent_item_code", "SH");
		region.put("last_modified_time", format1.format(new Date()));
		region.put("display_order", "2");
		region.put(ITEM_STATUS, 1);
		list.add(region);
		region = new BasicDBObject();
		// region.put("_id", new ObjectId().toString());
		region.put("item_type", "region");
		region.put("item_name", "浦西");
		region.put("item_code", "PUXI");
		// region.put("item_value", "liucz");
		region.put("parent_item_code", "SH");
		region.put("last_modified_time", format1.format(new Date()));
		region.put("display_order", "1");
		region.put(ITEM_STATUS, 1);
		list.add(region);

		region = new BasicDBObject();
		// region.put("_id", new ObjectId().toString());
		region.put("item_type", "region");
		region.put("item_name", "华南");
		region.put("item_code", "HUANAN");
		// region.put("item_value", "liucz");
		region.put("parent_item_code", "");
		region.put("last_modified_time", format1.format(new Date()));
		region.put("display_order", "1");
		region.put(ITEM_STATUS, 1);
		list.add(region);
		

		region = new BasicDBObject();
		// region.put("_id", new ObjectId().toString());
		region.put("item_type", "region");
		region.put("item_name", "广东");
		region.put("item_code", "GUANGDONG");
		// region.put("item_value", "liucz");
		region.put("parent_item_code", "HUANAN");
		region.put("last_modified_time", format1.format(new Date()));
		region.put("display_order", "2");
		region.put(ITEM_STATUS, 1);
		list.add(region);

		region = new BasicDBObject();
		// region.put("_id", new ObjectId().toString());
		region.put("item_type", "region");
		region.put("item_name", "深圳");
		region.put("item_code", "SHENZHEN");
		// region.put("item_value", "liucz");
		region.put("parent_item_code", "GUANGDONG");
		region.put("last_modified_time", format1.format(new Date()));
		region.put("display_order", "2");
		region.put(ITEM_STATUS, 1);
		list.add(region);
		
		region = new BasicDBObject();
		// region.put("_id", new ObjectId().toString());
		region.put("item_type", "region");
		region.put("item_name", "广州");
		region.put("item_code", "GZ");
		// region.put("item_value", "liucz");
		region.put("parent_item_code", "GUANGDONG");
		region.put("last_modified_time", format1.format(new Date()));
		region.put("display_order", "1");
		region.put(ITEM_STATUS, 1);
		list.add(region);
		
		region = new BasicDBObject();
		// region.put("_id", new ObjectId().toString());
		region.put("item_type", "region");
		region.put("item_name", "中山");
		region.put("item_code", "ZHONGSHAN");
		// region.put("item_value", "liucz");
		region.put("parent_item_code", "GUANGDONG");
		region.put("last_modified_time", format1.format(new Date()));
		region.put("display_order", "3");
		region.put(ITEM_STATUS, 1);
		list.add(region);

		region = new BasicDBObject();
		// region.put("_id", new ObjectId().toString());
		region.put("item_type", "region");
		region.put("item_name", "广西");
		region.put("item_code", "GUANGXI");
		// region.put("item_value", "liucz");
		region.put("parent_item_code", "HUANAN");
		region.put("last_modified_time", format1.format(new Date()));
		region.put("display_order", "1");
		region.put(ITEM_STATUS, 1);
		list.add(region);

		// 添加List集合
		print(list.get(0).get("_id")+"-"+list.get(0).get("item_code")+"-"+list.get(0).get("item_name"));
		print(dics.insert(list).getN());
		print(list.get(0).get("_id")+"-"+list.get(0).get("item_code")+"-"+list.get(0).get("item_name"));
	}

	private static DBCollection getDics() throws UnknownHostException {
		Mongo mg = new Mongo();
		DB db = mg.getDB("b2b_infrastructure");
		db.authenticate("admin", "admin".toCharArray());
		DBCollection dics = db.getCollection("infr_dictionary");
		return dics;
	}
	
}

