/**
 * 
 */
package com.mysoft.b2b.basicsystem.settings.provider;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.bson.types.ObjectId;

import com.mysoft.b2b.basicsystem.settings.api.DictionaryService;
import com.mysoft.b2b.basicsystem.settings.util.SettingsUtil;

/**
 * @author liucz
 * 
 */
public class DataConvert {
	public static String FILE_REGION = "E:\\temp\\Region.txt";
	public static String FILE_SAVE_REGION = "E:\\temp\\Region.dat";
	public static String FILE_PROVIDER_TYPE = "E:\\temp\\ProviderType.txt";
	public static String FILE_SAVE_PROVIDER_TYPE = "E:\\temp\\ProviderType.dat";
	public static String FILE_COMPANYPROPERTY = "E:\\temp\\CompanyProperty.txt";
	public static String FILE_SAVE_COMPANYPROPERTY = "E:\\temp\\CompanyProperty.dat";
	
	public static void convertRegion() {
		System.out.println("************convertRegion begin**************");
		try {
			// String encoding = "GBK";
			String encoding = "UTF-8";
			File file = new File(FILE_REGION);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				Map<String, String> map = new HashMap<String, String>();
				String[] temp;
				String row, code, parentCode;
				StringBuilder sb=new StringBuilder();
				int i = 100001;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					temp = lineTxt.split(",");
					code = new ObjectId().toString();
					map.put(temp[2], code);
					parentCode = map.get(temp[3]);
					if (parentCode == null) {
						parentCode = "0";
					}
					row = "{ \"_id\" : { \"$oid\" : \""
							+ new ObjectId().toString()
							+ "\" }, \"item_type\" : \""+DictionaryService.TYPE_REGION+"\", \"item_name\" : \""
							+ temp[1].trim() + "\", \"item_code\" : \"" + code
							+ "\", \"parent_item_code\" : \"" + parentCode
							+ "\", \"last_modified_time\" : \""
							+ SettingsUtil.getNowDate()
							+ "\", \"display_order\" : \"" + i + "\""
							+ ", \"item_status\" : 1 }";
					System.out.println(row);
					sb.append(row).append("\r\n");
					i++;
				}
				read.close();
				stringToFile(sb.toString(), FILE_SAVE_REGION);
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		System.out.println("************convertRegion begin**************");
	}

	public static void convertProviderType() {
		System.out
				.println("************convertProviderType begin**************");
		try {
			/*
			 * //{ "_id" : ObjectId("52a1998124b798d426ee9264"), "item_type" :
			 * "system_parameter", "item_name" : "登录失败次数", "item_code" :
			 * "loginfailtimes", "last_modified_time" : "2013-12-06 17:31:42",
			 * "display_order" : "1", "item_status" : 1 }
			 */
			// String encoding = "GBK";
			String encoding = "UTF-8";
			File file = new File(FILE_PROVIDER_TYPE);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				StringBuilder sb=new StringBuilder();
				String row;
				int i = 100001;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					row = "{ \"_id\" : { \"$oid\" : \""
							+ new ObjectId().toString()
							+ "\" }, \"item_type\" : \""+DictionaryService.TYPE_PROVIDER_TYPE+"\", \"item_name\" : \""
							+ lineTxt.trim() + "\", \"item_code\" : \""
							+ new ObjectId().toString()
							+ "\", \"last_modified_time\" : \""
							+ SettingsUtil.getNowDate()
							+ "\", \"display_order\" : \"" + i + "\""
							+ ", \"item_status\" : 1 }";
					System.out.println(row);
					sb.append(row).append("\r\n");
					i++;
				}
				read.close();
				stringToFile(sb.toString(), FILE_SAVE_PROVIDER_TYPE);
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		System.out.println("************convertProviderType end**************");
	}

	public static void convertCompanyProperty() {
		System.out
				.println("************convertCompanyProperty begin**************");
		try {
			// String encoding = "GBK";
			String encoding = "UTF-8";
			File file = new File(FILE_COMPANYPROPERTY);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				StringBuilder sb=new StringBuilder();
				String row;
				int i = 100001;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					row = "{ \"_id\" : { \"$oid\" : \""
							+ new ObjectId().toString()
							+ "\" }, \"item_type\" : \""+DictionaryService.TYPE_COMPANY_PROPERTY+"\", \"item_name\" : \""
							+ lineTxt.trim() + "\", \"item_code\" : \""
							+ new ObjectId().toString()
							+ "\", \"last_modified_time\" : \""
							+ SettingsUtil.getNowDate()
							+ "\", \"display_order\" : \"" + i + "\""
							+ ", \"item_status\" : 1 }";
					System.out.println(row);
					sb.append(row).append("\r\n");
					i++;
				}
				read.close();
				stringToFile(sb.toString(), FILE_SAVE_COMPANYPROPERTY);
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		System.out
				.println("************convertCompanyProperty end**************");
	}

	public static void convertStandardCategory() {
		System.out
				.println("************convertStandardCategory begin**************");
		try {
			// String encoding = "GBK";
			String encoding = "UTF-8";
			File file = new File("E:\\temp\\StandardCategory.txt");
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					System.out.println(lineTxt);
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		System.out
				.println("************convertStandardCategory end**************");
	}

	public static void main(String argv[]) {
		// String filePath = "E:\\bsp_basic_forest.SQL";
		// "res/";
		// readTxtFile(filePath);
		//convertRegion();
		//convertProviderType();
		//convertCompanyProperty();
		//convertStandardCategory();
		createCurrencyType();
	}

	public static void stringToFile(String sourceString, String pathname) {
		PrintWriter pw = null;
		try {			
			String encoding = "UTF-8";
			File fileOfSave = new File(pathname);
			//String fileNameOfSave = fileOfSave.getName();
			fileOfSave.createNewFile();
			pw = new PrintWriter(fileOfSave, encoding);
			pw.print(sourceString);
			pw.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (pw != null)
				pw.close();
		}
	}
	
	public static void createCurrencyType(){
		String row;
			/*row = "{ \"_id\" : { \"$oid\" : \""
					+ new ObjectId().toString()
					+ "\" }, \"item_type\" : \""+DictionaryService.TYPE_CURRENCY_PROPERTY+"\", \"item_name\" : \"人民币\", \"item_code\" : \"RMB\", \"last_modified_time\" : \""
					+ SettingsUtil.getNowDate()
					+ "\", \"display_order\" : \"1\""
					+ ", \"item_status\" : 1 }";
			System.out.println(row);*/
			//
			/*row = "{ \"_id\" : { \"$oid\" : \""
					+ new ObjectId().toString()
					+ "\" }, \"item_type\" : \""+DictionaryService.TYPE_CURRENCY_PROPERTY+"\", \"item_name\" : \"美元\", \"item_code\" : \"USD\", \"last_modified_time\" : \""
					+ SettingsUtil.getNowDate()
					+ "\", \"display_order\" : \"2\""
					+ ", \"item_status\" : 1 }";*/
			
			row = "{ \"_id\" : { \"$oid\" : \""
					+ new ObjectId().toString()
					+ "\" }, \"item_type\" : \""+DictionaryService.TYPE_CURRENCY_PROPERTY+"\", \"item_name\" : \"港币\", \"item_code\" : \"HKD\", \"last_modified_time\" : \""
					+ SettingsUtil.getNowDate()
					+ "\", \"display_order\" : \"4\""
					+ ", \"item_status\" : 1 "
					+ ", \"rate\" : 0.8 }";
			//
			System.out.println(row);
			row = "{ \"_id\" : { \"$oid\" : \""
					+ new ObjectId().toString()
					+ "\" }, \"item_type\" : \""+DictionaryService.TYPE_CURRENCY_PROPERTY+"\", \"item_name\" : \"欧元\", \"item_code\" : \"EUR\", \"last_modified_time\" : \""
					+ SettingsUtil.getNowDate()
					+ "\", \"display_order\" : \"3\""
					+ ", \"item_status\" : 1"
					+ ", \"rate\" : 8.56 }";
			System.out.println(row);
			//
			row = "{ \"_id\" : { \"$oid\" : \""
					+ new ObjectId().toString()
					+ "\" }, \"item_type\" : \""+DictionaryService.TYPE_CURRENCY_PROPERTY+"\", \"item_name\" : \"美元\", \"item_code\" : \"USD\", \"last_modified_time\" : \""
					+ SettingsUtil.getNowDate()
					+ "\", \"display_order\" : \"2\""
					+ ", \"item_status\" : 1 "
					+ ", \"rate\" : 6.21 }";
			//
			System.out.println(row);
			
			row = "{ \"_id\" : { \"$oid\" : \""
					+ new ObjectId().toString()
					+ "\" }, \"item_type\" : \""+DictionaryService.TYPE_CURRENCY_PROPERTY+"\", \"item_name\" : \"人民币\", \"item_code\" : \"RMB\", \"last_modified_time\" : \""
					+ SettingsUtil.getNowDate()
					+ "\", \"display_order\" : \"1\""
					+ ", \"item_status\" : 1"
					+ ", \"rate\" : 1 }";
			//
			
			System.out.println(row);
			for(int i=0;i<4;i++)
				System.out.println(new ObjectId().toString());
			
	}

	/**
	 * @param args
	 */
	/*
	 * public static void main(String[] args) { // TODO Auto-generated method
	 * stub StringBuffer sb = new StringBuffer(""); }
	 */

}
