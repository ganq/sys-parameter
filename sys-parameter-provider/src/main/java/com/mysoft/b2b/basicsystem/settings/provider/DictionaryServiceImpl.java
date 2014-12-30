package com.mysoft.b2b.basicsystem.settings.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.mysoft.b2b.basicsystem.settings.api.*;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mysoft.b2b.basicsystem.settings.mapper.RegionMapper;
import com.mysoft.b2b.basicsystem.settings.util.SettingsUtil;

/**
 * DictionaryService接口的实现类,提供基础参数(数据字典)相关服务
 * 
 * @author liucz
 * 
 */
@Service("dictionaryService")
public class DictionaryServiceImpl implements DictionaryService {
	private static final Logger log = Logger.getLogger(DictionaryServiceImpl.class);

	@Resource(name = "mongoDBService")
	private MongoDBService mongoDBService;
	@Autowired
	private CacheManager cacheManager;
	
	private DBCollection getDictionaryCollection() {
		DB db = mongoDBService.getDb();
		db.getCollection("infr_dictionary");
		DBCollection dics = db.getCollection(SettingsUtil.COLLECTION_NAME);
		return dics;
	}

	private Object getEhCache(String cacheName, String cachekey) {
		Cache ehCache = cacheManager.getCache(cacheName);
		Element element = ehCache.get(cachekey);
		if (element == null) {
			if (cacheName.equals(SettingsUtil.CACHE_REGION)) {
				loadRegionNodeCache();
			} else if (cacheName.equals(SettingsUtil.CACHE_PROVIDER_TYPE)) {
				loadProviderTypeCache();
			} else if (cacheName.equals(SettingsUtil.CACHE_COMPANY_PROPERTY)) {
				loadCompanyPropertyCache();
			} else if (cacheName.equals(SettingsUtil.CACHE_SYSTEM_PARAMETER)) {
				this.loadSystemParameterCache();
			} else if (cacheName.equals(SettingsUtil.CACHE_DICTIONRY)) {
				loadDictionryCache();
			}else if(cacheName.equals(SettingsUtil.CACHE_CURRENCY_TYPE)){
				loadCurrencyTypeCache();
			}else if(cacheName.equals(SettingsUtil.CACHE_DEVELOP_Q_TYPE)){
				loadDevelopQualificationTypeCache();
			}else if(cacheName.equals(SettingsUtil.CACHE_DEVELOP_BUSINESS_AREA_TYPE)){
				loadDeveloperBusinessAreaCache();
			}else if(cacheName.equals(SettingsUtil.CACHE_SUPPLIER_TAG_TYPE)){
                loadSupplierTagTypeCache();
            }
		}
		element = ehCache.get(cachekey);
		if (element == null) {
			return null;
		}
		return element.getObjectValue();
	}

	private void loadRegionNodeCache() {
		log.info("加载区域缓存数据 loadRegionNodeCache()！");
		List<RegionNode> listRegionNode = new ArrayList<RegionNode>();
		Map<String, RegionNode> mapRegionNode = new HashMap<String, RegionNode>();

		BasicDBObject o = new BasicDBObject();
		o.put(SettingsUtil.FIELD_ITEM_TYPE, TYPE_REGION);
		o.put(SettingsUtil.FIELD_ITEM_STATUS, 1);
		// o.put("item_code", "region");

		BasicDBObject orderBy = new BasicDBObject();
		orderBy.put(SettingsUtil.FIELD_PARENT_ITEM_CODE, 1);
		orderBy.put(SettingsUtil.FIELD_DISPLAY_ORDER, 1);
		DBCursor cur = getDictionaryCollection().find(o).sort(orderBy);
		DBObject dbObject;
		RegionNode rn, parentrn;
		while (cur.hasNext()) {
			dbObject = cur.next();
			rn = new RegionNode();
			rn.setCode((String) dbObject.get(SettingsUtil.FIELD_ITEM_CODE));
			rn.setName((String) dbObject.get(SettingsUtil.FIELD_ITEM_NAME));
			rn.setParentCode((String) dbObject.get(SettingsUtil.FIELD_PARENT_ITEM_CODE));
			rn.setDisplayOrder((String) dbObject.get(SettingsUtil.FIELD_DISPLAY_ORDER));
			rn.setArea((String) dbObject.get(SettingsUtil.FIELD_AREA));
			mapRegionNode.put(rn.getCode(), rn);
			listRegionNode.add(rn);
		}

		String parentCode;
		for (int i = 0; i < listRegionNode.size(); i++) {
			rn = listRegionNode.get(i);
			parentCode = rn.getParentCode();
			if (parentCode != null && !"".equals(parentCode)) {
				parentrn = mapRegionNode.get(parentCode);
				if (parentrn != null) {
					parentrn.addChildRegionNode(rn);
				}
			}
		}

		Cache ehCache = cacheManager.getCache(SettingsUtil.CACHE_REGION);
		Element element = new Element(SettingsUtil.CACHE_KEY_MAP_REGION_NODE, mapRegionNode);
		ehCache.put(element);
	}

	/**
	 * 查询区域树接口(返回启用状态的整个区域树)
	 * 
	 * @return RegionNode
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<RegionNode> getRegionRootHierarchy() {
		List<RegionNode> result = new ArrayList<RegionNode>();
		Map<String, RegionNode> mapRegionNode = (Map<String, RegionNode>) getEhCache(SettingsUtil.CACHE_REGION,
				SettingsUtil.CACHE_KEY_MAP_REGION_NODE);
		if (mapRegionNode == null) {
			return result;
		}
		for (RegionNode regionNode : mapRegionNode.values()) {
			if (regionNode.getParentCode() == null || "0".equals(regionNode.getParentCode())
					|| "".equals(regionNode.getParentCode())) {
				result.add(regionNode);
			}
		}
		SettingsUtil.sortListByPropertiesInAsc(result, "parentCode,displayOrder");
		return result;		
	}

	/**
	 * 查询上级区域树接口(返回启用状态的当前节点至根节点树)
	 * 
	 * @param code
	 *            区域代码
	 * @return RegionNode
	 * 
	 */
	@SuppressWarnings("unchecked")
	public RegionNode getRegionSuperHierarchy(String code) {
		Map<String, RegionNode> mapRegionNode = (Map<String, RegionNode>) getEhCache(SettingsUtil.CACHE_REGION,
				SettingsUtil.CACHE_KEY_MAP_REGION_NODE);
		if (mapRegionNode == null) {
			return null;
		}

		RegionNode regionNode = mapRegionNode.get(code);
		RegionNode parentRegionNode;
		if (regionNode == null) {
			return null;
		}
		regionNode = SettingsUtil.cloneRegionNodeWhitoutChildren(regionNode);
		String parentCode = regionNode.getParentCode();
		while (parentCode != null && !"".equals(parentCode)) {
			parentRegionNode = mapRegionNode.get(parentCode);
			if (parentRegionNode == null) {
				return regionNode;
			}
			parentRegionNode = SettingsUtil.cloneRegionNodeWhitoutChildren(parentRegionNode);
			parentRegionNode.addChildRegionNode(regionNode);
			parentCode = parentRegionNode.getParentCode();
			regionNode = parentRegionNode;
		}

		return regionNode;
	}

	/**
	 * 查询子级区域树接口(返回启用状态的当前节点至末级节点树)
	 * 
	 * @param code
	 *            区域代码
	 * @return RegionNode
	 * 
	 */
	@SuppressWarnings("unchecked")
	public RegionNode getRegionHierarchyByCode(String code) {
		Map<String, RegionNode> mapRegionNode = (Map<String, RegionNode>) getEhCache(SettingsUtil.CACHE_REGION,
				SettingsUtil.CACHE_KEY_MAP_REGION_NODE);
		if (mapRegionNode == null) {
			return null;
		}

		RegionNode regionNode = mapRegionNode.get(code);
		if (regionNode == null) {
			return null;
		}
		return regionNode;
	}

	/**
	 * 查询子区域列表接口(返回启用状态的直接下级区域记录列表)
	 * 
	 * @param code
	 *            区域代码
	 * @return List<Region>
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<Region> getChildrenRegions(String code) {
		List<Region> result = new ArrayList<Region>();
		Map<String, RegionNode> mapRegionNode = (Map<String, RegionNode>) getEhCache(SettingsUtil.CACHE_REGION,
				SettingsUtil.CACHE_KEY_MAP_REGION_NODE);
		if (mapRegionNode == null) {
			return result;
		}

		RegionNode regionNode = mapRegionNode.get(code);
		if (regionNode == null) {
			return result;
		}
		RegionNode childRegionNode;
		Region region;
		for (int i = 0; i < regionNode.getChildRegionNodes().size(); i++) {
			childRegionNode = (RegionNode) regionNode.getChildRegionNodes().get(i);
			region = SettingsUtil.convertRegionNode2Region(childRegionNode);
			result.add(region);
		}

		return result;
	}
	
	/**
	 * 根据片区查询区域列表接口(返回启用状态的区域记录列表)
	 * 
	 * @param area 区域代码
	 * @return List<Region>
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<Region> getRegionsByArea(String area){
		List<Region> result = new ArrayList<Region>();
		Map<String, RegionNode> mapRegionNode = (Map<String, RegionNode>) getEhCache(SettingsUtil.CACHE_REGION,
				SettingsUtil.CACHE_KEY_MAP_REGION_NODE);
		if (mapRegionNode == null) {
			return result;
		}
		for (RegionNode regionNode : mapRegionNode.values()) {
			if (area.equals(regionNode.getArea())) {
				result.add(SettingsUtil.convertRegionNode2Region(regionNode));
			}
		}
		SettingsUtil.sortListByPropertiesInAsc(result, "parentCode,displayOrder");
		return result;
	}

	/**
	 * 查询单个区域接口(返回指定的区域记录[不区分状态])
	 * 
	 * @param code
	 *            区域代码
	 * @return Region
	 * 
	 */
	@SuppressWarnings("unchecked")
	public Region getRegionByCode(String code) {
		Map<String, RegionNode> mapRegionNode = (Map<String, RegionNode>) getEhCache(SettingsUtil.CACHE_REGION,
				SettingsUtil.CACHE_KEY_MAP_REGION_NODE);
		if (mapRegionNode != null) {
			RegionNode regionNode = mapRegionNode.get(code);
			if (regionNode != null) {
				return SettingsUtil.convertRegionNode2Region(regionNode);
			}
		}

		Item item = this.getItemByCode(TYPE_REGION, code);
		if (item == null) {
			return null;
		}
		return SettingsUtil.convertItem2Region(item);
	}

	/**
	 * 查询指定级数的区域列表接口(返回启用状态的指定级数的区域记录列表)
	 * 
	 * @param level
	 *            级数
	 * @return List<Region>
	 * 
	 */
	public List<Region> getChildrenRegionsWithLevel(int level) {
		List<Region> result = new ArrayList<Region>();
		List<RegionNode> listRegionNode = getRegionRootHierarchy();
		if (listRegionNode == null) {
			return result;
		}
		RegionNode childRegionNode;
		for (int i = 0; i < listRegionNode.size(); i++) {
			childRegionNode = listRegionNode.get(i);
			result.addAll(getChildrenRegionsWithLevel(level, 1, childRegionNode));
		}

		return result;
	}

	/**
	 * 遍历获取指定级次的区域节点。
	 * 
	 * @param level
	 *            目标级次
	 * @param currentLevel
	 *            当前级次
	 * @param regionNode
	 *            当前区域节点
	 * @return List<Region>
	 * 
	 */
	private List<Region> getChildrenRegionsWithLevel(int level, int currentLevel, RegionNode regionNode) {
		List<Region> result = new ArrayList<Region>();
		if (level == currentLevel) {
			result.add(SettingsUtil.convertRegionNode2Region(regionNode));
			return result;
		}
		List<RegionNode> listRegionNode = regionNode.getChildRegionNodes();
		if (listRegionNode == null) {
			return result;
		}
		RegionNode childRegionNode;
		for (int i = 0; i < listRegionNode.size(); i++) {
			childRegionNode = listRegionNode.get(i);
			result.addAll(getChildrenRegionsWithLevel(level, currentLevel + 1, childRegionNode));
		}
		return result;
	}

	/**
	 * 加载公司类别缓存数据
	 */
	private void loadProviderTypeCache() {
		log.info("******加载公司类别缓存数据 loadProviderTypeCache()！ begin******");
		Map<String, ProviderType> mapProviderType = new HashMap<String, ProviderType>();

		BasicDBObject o = new BasicDBObject();
		o.put(SettingsUtil.FIELD_ITEM_TYPE, TYPE_PROVIDER_TYPE);
		o.put(SettingsUtil.FIELD_ITEM_STATUS, 1);

		BasicDBObject orderBy = new BasicDBObject();
		orderBy.put(SettingsUtil.FIELD_DISPLAY_ORDER, 1);
		DBCursor cur = getDictionaryCollection().find(o).sort(orderBy);
		DBObject dbObject;
		ProviderType providerType;
		while (cur.hasNext()) {
			dbObject = cur.next();
			providerType = new ProviderType();
			providerType.setCode((String) dbObject.get(SettingsUtil.FIELD_ITEM_CODE));
			providerType.setName((String) dbObject.get(SettingsUtil.FIELD_ITEM_NAME));
			providerType.setDisplayOrder((String) dbObject.get(SettingsUtil.FIELD_DISPLAY_ORDER));
			mapProviderType.put(providerType.getCode(), providerType);
			log.info(providerType.getCode() + "-" + providerType.getName() + "-" + providerType.getDisplayOrder());
		}

		Cache ehCache = cacheManager.getCache(SettingsUtil.CACHE_PROVIDER_TYPE);
		Element element = new Element(SettingsUtil.CACHE_KEY_MAP_PROVIDER_TYPE, mapProviderType);
		ehCache.put(element);
		log.info("******加载公司类别缓存数据 loadProviderTypeCache()！ end******");
	}

	/**
	 * 查询全部公司类别接口(返回启用状态的全部公司类别列表)
	 * 
	 * @return List<ProviderType>
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<ProviderType> getProviderTypes() {
		List<ProviderType> result = new ArrayList<ProviderType>();
		Map<String, ProviderType> mapProviderType = (Map<String, ProviderType>) getEhCache(
				SettingsUtil.CACHE_PROVIDER_TYPE, SettingsUtil.CACHE_KEY_MAP_PROVIDER_TYPE);
		if (mapProviderType == null) {
			return result;
		}
		for (ProviderType providerType : mapProviderType.values()) {
			// log.info("providerType = " + providerType);
			result.add(providerType);
		}
		SettingsUtil.sortListByPropertyInAsc(result, "displayOrder", SettingsUtil.SORT_VALUE_TYPE_STRING);
		return result;
	}

	/**
	 * 查询指定公司类别接口(返回指定的公司类别记录[不区分状态])
	 * 
	 * @param code
	 *            公司类别代码
	 * @return ProviderType
	 * 
	 */
	@SuppressWarnings("unchecked")
	public ProviderType getProviderTypeByCode(String code) {
		Map<String, ProviderType> mapProviderType = (Map<String, ProviderType>) getEhCache(
				SettingsUtil.CACHE_PROVIDER_TYPE, SettingsUtil.CACHE_KEY_MAP_PROVIDER_TYPE);
		if (mapProviderType != null) {
			ProviderType providerType = mapProviderType.get(code);
			if (providerType != null) {
				return providerType;
			}
		}

		Item item = this.getItemByCode(TYPE_PROVIDER_TYPE, code);
		if (item == null) {
			return null;
		}
		return SettingsUtil.convertItem2ProviderType(item);
	}

	/**
	 * 加载企业性质缓存数据
	 */
	private void loadCompanyPropertyCache() {
		log.info("******加载企业性质缓存数据 loadCompanyPropertyCache()！ begin******");
		Map<String, CompanyProperty> mapCompanyProperty = new HashMap<String, CompanyProperty>();

		BasicDBObject o = new BasicDBObject();
		o.put(SettingsUtil.FIELD_ITEM_TYPE, TYPE_COMPANY_PROPERTY);
		o.put(SettingsUtil.FIELD_ITEM_STATUS, 1);

		BasicDBObject orderBy = new BasicDBObject();
		orderBy.put(SettingsUtil.FIELD_DISPLAY_ORDER, 1);
		DBCursor cur = getDictionaryCollection().find(o).sort(orderBy);
		DBObject dbObject;
		CompanyProperty companyProperty;
		while (cur.hasNext()) {
			dbObject = cur.next();
			companyProperty = new CompanyProperty();
			companyProperty.setCode((String) dbObject.get(SettingsUtil.FIELD_ITEM_CODE));
			companyProperty.setName((String) dbObject.get(SettingsUtil.FIELD_ITEM_NAME));
			companyProperty.setDisplayOrder((String) dbObject.get(SettingsUtil.FIELD_DISPLAY_ORDER));
			mapCompanyProperty.put(companyProperty.getCode(), companyProperty);
			log.info(companyProperty.getCode() + "-" + companyProperty.getName() + "-"
					+ companyProperty.getDisplayOrder());
		}

		Cache ehCache = cacheManager.getCache(SettingsUtil.CACHE_COMPANY_PROPERTY);
		Element element = new Element(SettingsUtil.CACHE_KEY_MAP_COMPANY_PROPERTY, mapCompanyProperty);
		ehCache.put(element);
		log.info("******加载企业性质缓存数据 loadCompanyProperty()！ end******");
	}

	private void loadCurrencyTypeCache() {
		log.info("******加载货币缓存数据 loadCurrencyTypeCache()！ begin******");
		Map<String, CurrencyType> mapCurrencyType = new HashMap<String, CurrencyType>();

		BasicDBObject o = new BasicDBObject();
		o.put(SettingsUtil.FIELD_ITEM_TYPE, TYPE_CURRENCY_PROPERTY);
		o.put(SettingsUtil.FIELD_ITEM_STATUS, 1);

		BasicDBObject orderBy = new BasicDBObject();
		orderBy.put(SettingsUtil.FIELD_DISPLAY_ORDER, 1);
		DBCursor cur = getDictionaryCollection().find(o).sort(orderBy);
		DBObject dbObject;
		CurrencyType currencyType;
		while (cur.hasNext()) {
			dbObject = cur.next();
			currencyType = new CurrencyType();
			currencyType.setCode((String) dbObject.get(SettingsUtil.FIELD_ITEM_CODE));
			currencyType.setName((String) dbObject.get(SettingsUtil.FIELD_ITEM_NAME));
			currencyType.setDisplayOrder((String) dbObject.get(SettingsUtil.FIELD_DISPLAY_ORDER));
			currencyType.setRate(((Double) dbObject.get(SettingsUtil.FIELD_RATE)).doubleValue());
			currencyType.setStatus(((Integer) dbObject.get(SettingsUtil.FIELD_ITEM_STATUS)).intValue());
			mapCurrencyType.put(currencyType.getCode(), currencyType);
			log.info(currencyType.getCode() + "-" + currencyType.getName() + "-"
					+ currencyType.getDisplayOrder());
		}
		Cache ehCache = cacheManager.getCache(SettingsUtil.CACHE_CURRENCY_TYPE);
		Element element = new Element(SettingsUtil.CACHE_KEY_MAP_CURRENCY_TYPE, mapCurrencyType);
		ehCache.put(element);
		log.info("******加载货币类型缓存数据 loadCurrencyTypeCache()！ end******");
	}
	
	private void loadDevelopQualificationTypeCache() {
		log.info("******加载开发资质loadDevelopQualificationTypeCache()！ begin******");
		Map<String, DevelopQualificationType> mapDevelopQualificationType = new HashMap<String, DevelopQualificationType>();

		BasicDBObject o = new BasicDBObject();
		o.put(SettingsUtil.FIELD_ITEM_TYPE, TYPE_DEVELOP_Q_TYPE);
		o.put(SettingsUtil.FIELD_ITEM_STATUS, 1);

		BasicDBObject orderBy = new BasicDBObject();
		orderBy.put(SettingsUtil.FIELD_DISPLAY_ORDER, 1);
		DBCursor cur = getDictionaryCollection().find(o).sort(orderBy);
		DBObject dbObject;
		DevelopQualificationType developQualificationType;
		while (cur.hasNext()) {
			dbObject = cur.next();
			developQualificationType = new DevelopQualificationType();
			developQualificationType.setCode((String) dbObject.get(SettingsUtil.FIELD_ITEM_CODE));
			developQualificationType.setName((String) dbObject.get(SettingsUtil.FIELD_ITEM_NAME));
			developQualificationType.setDisplayOrder((String) dbObject.get(SettingsUtil.FIELD_DISPLAY_ORDER));
			
			mapDevelopQualificationType.put(developQualificationType.getCode(), developQualificationType);
			log.info(developQualificationType.toString());
		}
		Cache ehCache = cacheManager.getCache(SettingsUtil.CACHE_DEVELOP_Q_TYPE);
		Element element = new Element(SettingsUtil.CACHE_KEY_MAP_DEVELOP_Q_TYPE, mapDevelopQualificationType);
		ehCache.put(element);
		log.info("******加载开发资质loadDevelopQualificationTypeCache()！ end******");
	}
	
	
	private void loadDeveloperBusinessAreaCache() {
		log.info("******加载开发商业务领域loadDeveloperBusinessAreaCache()！ begin******");
		Map<String, DeveloperBusinessArea> mapDeveloperBusinessArea = new HashMap<String, DeveloperBusinessArea>();
		BasicDBObject o = new BasicDBObject();
		o.put(SettingsUtil.FIELD_ITEM_TYPE, TYPE_DEVELOP_BUSINESS_AREA_TYPE);
		o.put(SettingsUtil.FIELD_ITEM_STATUS, 1);

		BasicDBObject orderBy = new BasicDBObject();
		orderBy.put(SettingsUtil.FIELD_DISPLAY_ORDER, 1);
		DBCursor cur = getDictionaryCollection().find(o).sort(orderBy);
		DBObject dbObject;
		DeveloperBusinessArea developerBusinessArea;
		while (cur.hasNext()) {
			dbObject = cur.next();
			developerBusinessArea = new DeveloperBusinessArea();
			developerBusinessArea.setCode((String) dbObject.get(SettingsUtil.FIELD_ITEM_CODE));
			developerBusinessArea.setName((String) dbObject.get(SettingsUtil.FIELD_ITEM_NAME));
			developerBusinessArea.setDisplayOrder((String) dbObject.get(SettingsUtil.FIELD_DISPLAY_ORDER));
			developerBusinessArea.setStatus(((Integer) dbObject.get(SettingsUtil.FIELD_ITEM_STATUS)).intValue());
			mapDeveloperBusinessArea.put(developerBusinessArea.getCode(), developerBusinessArea);
			log.info(developerBusinessArea.toString());
		}
		Cache ehCache = cacheManager.getCache(SettingsUtil.CACHE_DEVELOP_BUSINESS_AREA_TYPE);
		Element element = new Element(SettingsUtil.CACHE_KEY_MAP_DEVELOP_BUSINESS_AREA_TYPE, mapDeveloperBusinessArea);
		ehCache.put(element);
		log.info("******加载开发商业务领域loadDeveloperBusinessAreaCache()！ end******");
	}
	
	/**
	 * 查询全部企业性质接口(返回启用状态的全部公司性质列表)
	 * 
	 * @return List<CompanyProperty>
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyProperty> getCompanyPropertys() {
		List<CompanyProperty> result = new ArrayList<CompanyProperty>();
		Map<String, CompanyProperty> mapCompanyProperty = (Map<String, CompanyProperty>) getEhCache(
				SettingsUtil.CACHE_COMPANY_PROPERTY, SettingsUtil.CACHE_KEY_MAP_COMPANY_PROPERTY);
		if (mapCompanyProperty == null) {
			return result;
		}
		for (CompanyProperty companyProperty : mapCompanyProperty.values()) {
			// log.info("providerType = " + companyProperty);
			result.add(companyProperty);
		}
		SettingsUtil.sortListByPropertyInAsc(result, "displayOrder", SettingsUtil.SORT_VALUE_TYPE_STRING);
		return result;
	}

	/**
	 * 查询指定企业性质接口(返回指定的企业性质记录[不区分状态])
	 * 
	 * @param code
	 *            企业性质代码
	 * @return CompanyProperty
	 * 
	 */
	@SuppressWarnings("unchecked")
	public CompanyProperty getCompanyPropertyByCode(String code) {
		Map<String, CompanyProperty> mapCompanyProperty = (Map<String, CompanyProperty>) getEhCache(
				SettingsUtil.CACHE_COMPANY_PROPERTY, SettingsUtil.CACHE_KEY_MAP_COMPANY_PROPERTY);
		if (mapCompanyProperty != null) {
			CompanyProperty companyProperty = mapCompanyProperty.get(code);
			if (companyProperty != null) {
				return companyProperty;
			}
		}

		Item item = getItemByCode(TYPE_COMPANY_PROPERTY, code);
		if (item == null) {
			return null;
		}
		return SettingsUtil.convertItem2CompanyProperty(item);
	}

	/**
	 * 加载系统参数缓存数据
	 */
	private void loadSystemParameterCache() {
		log.info("******加载系统参数缓存数据 loadCompanyPropertyCache()！ begin******");
		Map<String, SystemParameter> mapSystemParameter = new HashMap<String, SystemParameter>();

		BasicDBObject o = new BasicDBObject();
		o.put(SettingsUtil.FIELD_ITEM_TYPE, TYPE_SYSTEM_PARAMETER);
		o.put(SettingsUtil.FIELD_ITEM_STATUS, 1);

		BasicDBObject orderBy = new BasicDBObject();
		orderBy.put(SettingsUtil.FIELD_DISPLAY_ORDER, 1);
		DBCursor cur = getDictionaryCollection().find(o).sort(orderBy);
		DBObject dbObject;
		SystemParameter systemParameter;
		while (cur.hasNext()) {
			dbObject = cur.next();
			systemParameter = new SystemParameter();
			systemParameter.setCode((String) dbObject.get(SettingsUtil.FIELD_ITEM_CODE));
			systemParameter.setName((String) dbObject.get(SettingsUtil.FIELD_ITEM_NAME));
			systemParameter.setValue((String) dbObject.get(SettingsUtil.FIELD_ITEM_VALUE));
			systemParameter.setDisplayOrder((String) dbObject.get(SettingsUtil.FIELD_DISPLAY_ORDER));
			mapSystemParameter.put(systemParameter.getCode(), systemParameter);
			log.info(systemParameter.getCode() + "-" + systemParameter.getName() + "-"
					+ systemParameter.getDisplayOrder());
		}

		Cache ehCache = cacheManager.getCache(SettingsUtil.CACHE_SYSTEM_PARAMETER);
		Element element = new Element(SettingsUtil.CACHE_KEY_MAP_SYSTEM_PARAMETER, mapSystemParameter);
		ehCache.put(element);
		log.info("******加载系统参数缓存数据 loadCompanyProperty()！ end******");
	}

	/**
	 * 查询全部系统参数接口(返回启用状态的全部系统参数列表)
	 * 
	 * @return List<SystemParameter>
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<SystemParameter> getSystemParameters() {
		List<SystemParameter> result = new ArrayList<SystemParameter>();
		Map<String, SystemParameter> mapSystemParameter = (Map<String, SystemParameter>) getEhCache(
				SettingsUtil.CACHE_SYSTEM_PARAMETER, SettingsUtil.CACHE_KEY_MAP_SYSTEM_PARAMETER);
		if (mapSystemParameter == null) {
			return result;
		}
		for (SystemParameter systemParameter : mapSystemParameter.values()) {
			// log.info("providerType = " + companyProperty);
			result.add(systemParameter);
		}
		SettingsUtil.sortListByPropertyInAsc(result, "displayOrder", SettingsUtil.SORT_VALUE_TYPE_STRING);
		return result;
	}

	/**
	 * 查询指定系统参数接口(返回指定的系统参数记录[不区分状态])
	 * 
	 * @param code
	 *            系统参数代码
	 * @return SystemParameter
	 * 
	 */
	@SuppressWarnings("unchecked")
	public SystemParameter getSystemParameterByCode(String code) {
		Map<String, SystemParameter> mapSystemParameter = (Map<String, SystemParameter>) getEhCache(
				SettingsUtil.CACHE_SYSTEM_PARAMETER, SettingsUtil.CACHE_KEY_MAP_SYSTEM_PARAMETER);
		if (mapSystemParameter != null) {
			SystemParameter systemParameter = mapSystemParameter.get(code);
			if (systemParameter != null) {
				return systemParameter;
			}
		}

		Item item = getItemByCode(TYPE_SYSTEM_PARAMETER, code);
		if (item == null) {
			return null;
		}
		return SettingsUtil.convertItem2SystemParameter(item);
	}

	/**
	 * 加载全部基础参数设置
	 */
	private void loadDictionryCache() {
		log.info("******加载全部基础参数设置  loadDictionryCache()！ begin******");
		Map<String, Item> mapItem = new HashMap<String, Item>();

		BasicDBObject orderBy = new BasicDBObject();
		orderBy.put(SettingsUtil.FIELD_ITEM_TYPE, 1);
		orderBy.put(SettingsUtil.FIELD_DISPLAY_ORDER, 1);
		DBCursor cur = getDictionaryCollection().find().sort(orderBy);
		DBObject dbObject;
		Item item;
		while (cur.hasNext()) {
			dbObject = cur.next();
			item = new Item();
			item.setItemId(((ObjectId) dbObject.get(SettingsUtil.FIELD_ITEM_ID)).toString());
			item.setItemType((String) dbObject.get(SettingsUtil.FIELD_ITEM_TYPE));
			item.setItemCode((String) dbObject.get(SettingsUtil.FIELD_ITEM_CODE));
			item.setItemName((String) dbObject.get(SettingsUtil.FIELD_ITEM_NAME));
			item.setItemValue((String) dbObject.get(SettingsUtil.FIELD_ITEM_VALUE));
			item.setParentItemCode((String) dbObject.get(SettingsUtil.FIELD_PARENT_ITEM_CODE));
			item.setDisplayOrder((String) dbObject.get(SettingsUtil.FIELD_DISPLAY_ORDER));
			item.setLastModifiedTime((String) dbObject.get(SettingsUtil.FIELD_LAST_MODIFIED_TIME));
			item.setItemStatus(((Integer) dbObject.get(SettingsUtil.FIELD_ITEM_STATUS)).intValue());
			mapItem.put(item.getItemType() + "-" + item.getItemCode(), item);
			log.info(item.getItemType() + "-" + item.getItemCode() + "-" + item.getItemName() + "-" + item.getDisplayOrder());
		}

		Cache ehCache = cacheManager.getCache(SettingsUtil.CACHE_DICTIONRY);
		Element element = new Element(SettingsUtil.CACHE_KEY_MAP_DICTIONRY, mapItem);
		ehCache.put(element);
		log.info("******加载全部基础参数设置 loadDictionryCache()！ end******");
	}

	/**
	 * 查询数据字典记录列表接口(返回启用状态的指定数据类型的全部列表)
	 * 
	 * @param itemType
	 *            数据类型代码
	 * @return List<Item>
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<Item> getItems(String itemType) {
		List<Item> result = new ArrayList<Item>();
		Map<String, Item> mapItem = (Map<String, Item>) getEhCache(SettingsUtil.CACHE_DICTIONRY,
				SettingsUtil.CACHE_KEY_MAP_DICTIONRY);
		if (mapItem == null) {
			return result;
		}
		for (Item item : mapItem.values()) {
			// log.info("providerType = " + companyProperty);
			if (item.getItemType().equals(itemType) && item.getItemStatus() == 1) {
				result.add(item);
			}
		}
		SettingsUtil.sortListByPropertyInAsc(result, "displayOrder", SettingsUtil.SORT_VALUE_TYPE_STRING);
		return result;
	}

	/**
	 * 查询指定数据字典记录接口（返回指定的数据字典类型和代码的记录[不区分状态]）。
	 * 
	 * @param itemType
	 *            数据类型代码
	 * @param itemCode
	 *            数据代码
	 * @return Item
	 * 
	 */
	@SuppressWarnings("unchecked")
	public Item getItemByCode(String itemType, String itemCode) {
		Map<String, Item> mapItem = (Map<String, Item>) getEhCache(SettingsUtil.CACHE_DICTIONRY,
				SettingsUtil.CACHE_KEY_MAP_DICTIONRY);
		if (mapItem != null) {
			Item item = mapItem.get(itemType + "-" + itemCode);
			if (mapItem != null) {
				return item;
			}
		}
		return null;
	}

	/**
	 * 查询整个数据字典记录列表接口(返回全部数据字典记录)
	 * 
	 * @return List<Item>
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<Item> getDitionary() {
		List<Item> result = new ArrayList<Item>();
		Map<String, Item> mapItem = (Map<String, Item>) getEhCache(SettingsUtil.CACHE_DICTIONRY,
				SettingsUtil.CACHE_KEY_MAP_DICTIONRY);
		if (mapItem == null) {
			return result;
		}
		for (Item item : mapItem.values()) {
			result.add(item);
		}
		SettingsUtil.sortListByPropertiesInAsc(result, "itemType,parentItemCode,displayOrder");
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<CurrencyType> getCurrencyTypes(){
		List<CurrencyType> result = new ArrayList<CurrencyType>();
		Map<String, CurrencyType> mapCurrencyType = (Map<String, CurrencyType>) getEhCache(
				SettingsUtil.CACHE_CURRENCY_TYPE, SettingsUtil.CACHE_KEY_MAP_CURRENCY_TYPE);
		if (mapCurrencyType == null) {
			return result;
		}
		for (CurrencyType currencyType : mapCurrencyType.values()) {
			// log.info("providerType = " + companyProperty);
			result.add(currencyType);
		}
		SettingsUtil.sortListByPropertyInAsc(result, "displayOrder", SettingsUtil.SORT_VALUE_TYPE_STRING);
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public CurrencyType getCurrencyTypeByCode(String code){
		Map<String, CurrencyType> mapCurrencyType = (Map<String, CurrencyType>) getEhCache(
				SettingsUtil.CACHE_CURRENCY_TYPE, SettingsUtil.CACHE_KEY_MAP_CURRENCY_TYPE);
		if (mapCurrencyType != null) {
			CurrencyType currencyType = mapCurrencyType.get(code);
			if (currencyType != null) {
				return currencyType;
			}
		}

		Item item = getItemByCode(TYPE_CURRENCY_PROPERTY, code);
		if (item == null) {
			return null;
		}
		return SettingsUtil.convertItem2CurrencyType(item);
	}
	
	public Double getRMBRate(String code, float num) {
		// TODO Auto-generated method stub
		CurrencyType cType = getCurrencyTypeByCode(code);
		if(cType == null) return 0.0;
		Double rate = cType.getRate();
		return rate*num;
	}

	@SuppressWarnings("unchecked")
	public List<DevelopQualificationType> getDevelopQualifications() {
		// TODO Auto-generated method stub
		List<DevelopQualificationType> result = new ArrayList<DevelopQualificationType>();
		Map<String, DevelopQualificationType> mapDevelopQualificationType = (Map<String, DevelopQualificationType>) getEhCache(
				SettingsUtil.CACHE_DEVELOP_Q_TYPE, SettingsUtil.CACHE_KEY_MAP_DEVELOP_Q_TYPE);
		if (mapDevelopQualificationType == null) {
			return result;
		}
		for (DevelopQualificationType developQualificationType : mapDevelopQualificationType.values()) {
			// log.info("providerType = " + companyProperty);
			result.add(developQualificationType);
		}
		SettingsUtil.sortListByPropertyInAsc(result, "displayOrder", SettingsUtil.SORT_VALUE_TYPE_STRING);
		return result;
	}

	
	@SuppressWarnings("unchecked")
	public DevelopQualificationType getDevelopQualificationByCode(String code) {
		// TODO Auto-generated method stub
		Map<String, DevelopQualificationType> mapDevelopQualificationType = (Map<String, DevelopQualificationType>) getEhCache(
				SettingsUtil.CACHE_DEVELOP_Q_TYPE, SettingsUtil.CACHE_KEY_MAP_DEVELOP_Q_TYPE);
		if (mapDevelopQualificationType != null) {
			DevelopQualificationType developQualificationType = mapDevelopQualificationType.get(code);
			if (developQualificationType != null) {
				return developQualificationType;
			}
		}

		Item item = getItemByCode(TYPE_DEVELOP_Q_TYPE, code);
		if (item == null) {
			return null;
		}
		return SettingsUtil.convertItem2DevelopQualificationType(item);
	}

	@SuppressWarnings("unchecked")
	public List<DeveloperBusinessArea> getDeveloperBusinessAreas() {
		// TODO Auto-generated method stub
		List<DeveloperBusinessArea> result = new ArrayList<DeveloperBusinessArea>();
		Map<String, DeveloperBusinessArea> mapDevelopQualificationType = (Map<String, DeveloperBusinessArea>) getEhCache(
				SettingsUtil.CACHE_DEVELOP_BUSINESS_AREA_TYPE, SettingsUtil.CACHE_KEY_MAP_DEVELOP_BUSINESS_AREA_TYPE);
		if (mapDevelopQualificationType == null) {
			return result;
		}
		for (DeveloperBusinessArea developerBusinessArea : mapDevelopQualificationType.values()) {
			result.add(developerBusinessArea);
		}
		SettingsUtil.sortListByPropertyInAsc(result, "displayOrder", SettingsUtil.SORT_VALUE_TYPE_STRING);
		return result;
	}

	@SuppressWarnings("unchecked")
	public DeveloperBusinessArea getDeveloperBusinessAreaByCode(String code) {
		// TODO Auto-generated method stub
		Map<String, DeveloperBusinessArea> mapDevelopQualificationType = (Map<String, DeveloperBusinessArea>) getEhCache(
				SettingsUtil.CACHE_DEVELOP_BUSINESS_AREA_TYPE, SettingsUtil.CACHE_KEY_MAP_DEVELOP_BUSINESS_AREA_TYPE);
		if (mapDevelopQualificationType != null) {
			DeveloperBusinessArea developerBusinessArea = mapDevelopQualificationType.get(code);
			if (developerBusinessArea != null) {
				return developerBusinessArea;
			}
		}
		//
		Item item = getItemByCode(TYPE_DEVELOP_BUSINESS_AREA_TYPE, code);
		if (item == null) {
			return null;
		}
		return SettingsUtil.convertItem2DeveloperBusinessArea(item);
	}

	public boolean updateCurrencyTypeByCode(String code, Double rate, int status) {
		// TODO Auto-generated method stub
		DBCollection dics = getDictionaryCollection();
		// 查询数据
		BasicDBObject old = new BasicDBObject();
		old.put("item_type", TYPE_CURRENCY_PROPERTY);
		old.put("item_code", code);
		BasicDBObject newDBO = new BasicDBObject();
		newDBO.put("item_code", code);
		newDBO.put("rate", rate);
		newDBO.put("item_status", status);
		dics.update(old, newDBO, false, true);
		return true;
	}
    /**
     * 加载公司类别缓存数据
     */
    private void loadSupplierTagTypeCache() {
        log.info("******加载公司标签缓存数据 loadProviderTypeCache()！ begin******");
        Map<String, SupplierTag> supplierTagHashMap = new HashMap<String, SupplierTag>();

        BasicDBObject o = new BasicDBObject();
        o.put(SettingsUtil.FIELD_ITEM_TYPE, TYPE_SUPPLIER_TAG_TYPE);
        o.put(SettingsUtil.FIELD_ITEM_STATUS, new Integer(1));

        BasicDBObject orderBy = new BasicDBObject();
        orderBy.put(SettingsUtil.FIELD_DISPLAY_ORDER, 1);
        DBCursor cur = getDictionaryCollection().find(o).sort(orderBy);
        DBObject dbObject;
        SupplierTag supplierTag;
        while (cur.hasNext()) {
            dbObject = cur.next();
            supplierTag = new SupplierTag();
            supplierTag.setCode((String) dbObject.get(SettingsUtil.FIELD_ITEM_CODE));
            supplierTag.setName((String) dbObject.get(SettingsUtil.FIELD_ITEM_NAME));
            supplierTag.setDisplayOrder((String) dbObject.get(SettingsUtil.FIELD_DISPLAY_ORDER));
            supplierTagHashMap.put(supplierTag.getCode(), supplierTag);
            log.info(supplierTag.getCode() + "-" + supplierTag.getName() + "-" + supplierTag.getDisplayOrder());
        }

        Cache ehCache = cacheManager.getCache(SettingsUtil.CACHE_SUPPLIER_TAG_TYPE);
        Element element = new Element(SettingsUtil.CACHE_KEY_MAP_SUPPLIER_TAG_TYPE, supplierTagHashMap);
        ehCache.put(element);
        log.info("******加载公司标签缓存数据 l标签SupplierTagTypeCache()！ end******");
    }

    @SuppressWarnings("unchecked")
    public List<SupplierTag> getSupplierTagTypes() {
        List<SupplierTag> result = new ArrayList<SupplierTag>();
        Map<String, SupplierTag> supplierTagMap = (Map<String, SupplierTag>) getEhCache(
                SettingsUtil.CACHE_SUPPLIER_TAG_TYPE, SettingsUtil.CACHE_KEY_MAP_SUPPLIER_TAG_TYPE);
        if (supplierTagMap == null) {
            return result;
        }
        for (SupplierTag supplierTag : supplierTagMap.values()) {
            result.add(supplierTag);
        }
        SettingsUtil.sortListByPropertyInAsc(result, "displayOrder", SettingsUtil.SORT_VALUE_TYPE_STRING);
        return result;
    }

    @SuppressWarnings("unchecked")
    public SupplierTag getSupplierTagByKey(String code) {
        Map<String, SupplierTag> stringSupplierTagMap = (Map<String, SupplierTag>) getEhCache(
                SettingsUtil.CACHE_SUPPLIER_TAG_TYPE, SettingsUtil.CACHE_KEY_MAP_SUPPLIER_TAG_TYPE);
        if (stringSupplierTagMap != null) {
            SupplierTag supplierTag = stringSupplierTagMap.get(code);
            if (supplierTag != null) {
                return supplierTag;
            }
        }

        Item item = this.getItemByCode(TYPE_PROVIDER_TYPE, code);
        if (item == null) {
            return null;
        }
        return SettingsUtil.convertItem2SupplierTag(item);
    }


	@Autowired
    private RegionMapper regionMapper;

	@Override
	public void syncRegionDataToMysqlDB() {
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    	log.info("load region data from mongodb start...");
		List<Region> regionList = new ArrayList<Region>();

		BasicDBObject o = new BasicDBObject();
		o.put(SettingsUtil.FIELD_ITEM_TYPE, TYPE_REGION);
		o.put(SettingsUtil.FIELD_ITEM_STATUS, 1);
		// o.put("item_code", "region");

		BasicDBObject orderBy = new BasicDBObject();
		orderBy.put(SettingsUtil.FIELD_PARENT_ITEM_CODE, 1);
		orderBy.put(SettingsUtil.FIELD_DISPLAY_ORDER, 1);
		DBCursor cur = getDictionaryCollection().find(o).sort(orderBy);
		DBObject dbObject;
		Region rn;
		while (cur.hasNext()) {
			dbObject = cur.next();
			rn = new Region();
			rn.setCode((String) dbObject.get(SettingsUtil.FIELD_ITEM_CODE));
			rn.setName((String) dbObject.get(SettingsUtil.FIELD_ITEM_NAME));
			rn.setParentCode((String) dbObject.get(SettingsUtil.FIELD_PARENT_ITEM_CODE));
			rn.setDisplayOrder((String) dbObject.get(SettingsUtil.FIELD_DISPLAY_ORDER));
			rn.setArea((String) dbObject.get(SettingsUtil.FIELD_AREA));
			regionList.add(rn);
		}
		log.info("load region data size:==> " + regionList.size());
		log.info("load region data from mongodb end...");
		
		log.info("delete region data from mysqldb start...");
		int delCount = regionMapper.deleteAllRegion();
		log.info("delete region data size:==> " + delCount);
		log.info("delete region data from mysqldb end...");
		
		
		log.info("save region data to mysql db start...");
		regionMapper.insertRegion(regionList);
		log.info("save region data to mysql db end...");
		log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
    }
}