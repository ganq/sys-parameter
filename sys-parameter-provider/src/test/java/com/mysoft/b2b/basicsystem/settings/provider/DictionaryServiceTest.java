package com.mysoft.b2b.basicsystem.settings.provider;

import java.util.ArrayList;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mysoft.b2b.basicsystem.settings.api.CompanyProperty;
import com.mysoft.b2b.basicsystem.settings.api.CurrencyType;
import com.mysoft.b2b.basicsystem.settings.api.DevelopQualificationType;
import com.mysoft.b2b.basicsystem.settings.api.DeveloperBusinessArea;
import com.mysoft.b2b.basicsystem.settings.api.DictionaryService;
import com.mysoft.b2b.basicsystem.settings.api.Item;
import com.mysoft.b2b.basicsystem.settings.api.ProviderType;
import com.mysoft.b2b.basicsystem.settings.api.Region;
import com.mysoft.b2b.basicsystem.settings.api.RegionNode;
import com.mysoft.b2b.basicsystem.settings.api.SupplierTag;
import com.mysoft.b2b.basicsystem.settings.api.SystemParameter;
import com.mysoft.b2b.basicsystem.settings.test.BaseTestCase;

@RunWith(SpringJUnit4ClassRunner.class)
public class DictionaryServiceTest extends BaseTestCase {
	private static final Logger logger = Logger
			.getLogger(DictionaryServiceTest.class);

	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
	private CacheManager cacheManager;

	public void printTree(List<RegionNode> tree) {
		if (tree == null) {
			return;
		}
		RegionNode rn;
		for (int i = 0; i < tree.size(); i++) {
			rn = tree.get(i);
			logger.info(rn.getParentCode() + "-" + rn.getCode() + "-"
					+ rn.getName() + "-" + rn.getDisplayOrder());
			printTree(rn.getChildRegionNodes());
		}
	}
	
	
	
	@Test
	public void testGetRegionRootHierarchy()  {
		List<RegionNode> rs = dictionaryService.getRegionRootHierarchy();
		logger.info("*****testGetRegionRootHierarchy begin*****");
		printTree(rs);
		logger.info("*****testGetRegionRootHierarchy end*****");
		/*
		 * Cache dicCache = cacheManager.getCache("dicCache"); Element element =
		 * new Element("key", rs); dicCache.put(element);*/
		 
	}

	@Test
	public void testGetRegionSuperHierarchy()  {
		RegionNode prn = dictionaryService.getRegionSuperHierarchy("52a7d6cb24b72a26b80983f4");
		logger.info("*****testGetRegionSuperHierarchy begin*****");
		if (prn != null) {
			List<RegionNode> rs = new ArrayList<RegionNode>();
			rs.add(prn);
			printTree(rs);
		}
		logger.info("*****testGetRegionSuperHierarchy end*****");
	}

	@Test
	public void testGetRegionHierarchyByCode()  {
		RegionNode prn = dictionaryService.getRegionHierarchyByCode("52a7d6cb24b72a26b80983f2");
		logger.info("*****testGetRegionHierarchyByCode begin*****");
		if (prn != null) {
			List<RegionNode> rs = new ArrayList<RegionNode>();
			rs.add(prn);
			printTree(rs);
		}
		logger.info("*****testGetRegionHierarchyByCode end*****");
	}
	
	@Test
	public void testGetRegionsByArea()  {
		List<Region> regions = dictionaryService.getRegionsByArea("east");
		logger.info("*****testGetRegionsByArea begin*****");
		logger.info(regions.size());
		Region region;
		for (int i = 0; i < regions.size(); i++) {
			region = regions.get(i);
			logger.info(region.getCode() + "-" + region.getName() + "-"
					+ region.getDisplayOrder());
		}
		logger.info("*****testGetRegionsByArea end*****");
	}

	@Test
	public void testGetChildrenRegionsWithLevel()  {
		List<Region> regions = dictionaryService.getChildrenRegionsWithLevel(1);

		logger.info("*****testGetChildrenRegionsWithLevel begin*****");
		logger.info(regions.size());
		Region region;
		for (int i = 0; i < regions.size(); i++) {
			region = regions.get(i);
			logger.info(region.getParentCode() + "-" + region.getCode() + "-"
					+ region.getName() + "-" + region.getDisplayOrder());
		}
		logger.info("*****testGetChildrenRegionsWithLevel end*****");
	}

	@Test
	public void testGetChildrenRegions()  {
		List<Region> regions = dictionaryService.getChildrenRegions("52a6cece24b74838aa7bcbca");

		logger.info("*****testGetChildrenRegions begin*****");
		logger.info(regions.size());
		Region region;
		for (int i = 0; i < regions.size(); i++) {
			region = regions.get(i);
			logger.info(region.getCode() + "-" + region.getName() + "-"
					+ region.getDisplayOrder());
		}
		logger.info("*****testGetChildrenRegions end*****");
	}

	@Test
	public void testGetRegionByCode()  {
		Region region = dictionaryService.getRegionByCode("52a6cece24b74838aa7bcbca");

		logger.info("*****testGetRegionByCode begin*****");
		if (region != null) {
			logger.info(region.getCode() + "-" + region.getName() + "-"
					+ region.getDisplayOrder());
		}
		logger.info("*****testGetRegionByCode end*****");
	}

	@Test
	public void testGetProviderTypes()  {
		List<ProviderType> providerTypes = dictionaryService.getProviderTypes();

		logger.info("*****testGetProviderTypes begin*****");
		ProviderType providerType;
		for (int i = 0; i < providerTypes.size(); i++) {
			providerType = providerTypes.get(i);
			logger.info(providerType.getCode() + "-" + providerType.getName()
					+ "-" + providerType.getDisplayOrder());
		}
		logger.info("*****testGetProviderTypes end*****");
	}
    @Test
    public void testGetSupplierTagTypes()  {
        List<SupplierTag> supplierTagList = dictionaryService.getSupplierTagTypes();

        logger.info("*****testGetSupplierTagTypes begin*****");
        SupplierTag supplierTag;
        for (int i = 0; i < supplierTagList.size(); i++) {
            supplierTag = supplierTagList.get(i);
            logger.info(supplierTag.getCode() + "-" + supplierTag.getName()
                    + "-" + supplierTag.getDisplayOrder());
        }
        logger.info("*****testGetSupplierTagTypes end*****");
    }

	@Test
	public void testGetProviderTypeByCode()  {
		ProviderType providerType = dictionaryService
				.getProviderTypeByCode("52a6cece24b74838aa7bcc51");

		logger.info("*****testGetProviderTypeByCode begin*****");
		if (providerType != null) {
			logger.info(providerType.getCode() + "-" + providerType.getName()
					+ "-" + providerType.getDisplayOrder());
		}
		logger.info("*****testGetProviderTypeByCode end*****");
	}

	@Test
	public void testGetCompanyPropertys()  {
		List<CompanyProperty> companyPropertys = dictionaryService
				.getCompanyPropertys();

		logger.info("*****testGetProviderTypes begin*****");
		CompanyProperty companyProperty;
		for (int i = 0; i < companyPropertys.size(); i++) {
			companyProperty = companyPropertys.get(i);
			logger.info(companyProperty.getCode() + "-"
					+ companyProperty.getName() + "-"
					+ companyProperty.getDisplayOrder());
		}
		logger.info("*****testGetProviderTypes end*****");
	}

	@Test
	public void testGetCompanyPropertyByCode()  {
		CompanyProperty companyProperty = dictionaryService
				.getCompanyPropertyByCode("52a7d6cb24b72a26b80983fd");

		logger.info("*****testGetProviderTypeByCode begin*****");
		if (companyProperty != null) {
			logger.info(companyProperty.getCode() + "-"
					+ companyProperty.getName() + "-"
					+ companyProperty.getDisplayOrder());
		}
		companyProperty = dictionaryService
				.getCompanyPropertyByCode("52a7d6cb24b72a26b80983ff");
		if (companyProperty != null) {
			logger.info("停用："+companyProperty.getCode() + "-"
					+ companyProperty.getName() + "-"
					+ companyProperty.getDisplayOrder());
		}
		logger.info("*****testGetProviderTypeByCode end*****");
	}
	
	@Test
	public void testGetSystemParameters()  {
		List<SystemParameter> systemParameters = dictionaryService
				.getSystemParameters();
		logger.info("*****testGetSystemParameters begin*****");
		SystemParameter systemParameter;
		for (int i = 0; i < systemParameters.size(); i++) {
			systemParameter = systemParameters.get(i);
			logger.info(systemParameter.getCode() + "-"
					+ systemParameter.getName() + "-"
					+ systemParameter.getDisplayOrder());
		}
		logger.info("*****testGetSystemParameters end*****");
	}
		
	@Test
	public void testGetSystemParameterByCode()  {
		SystemParameter systemParameter = dictionaryService
				.getSystemParameterByCode("loginfailtimes");

		logger.info("*****testGetSystemParameterByCode begin*****");
		if (systemParameter != null) {
			logger.info(systemParameter.getCode() + "-"
					+ systemParameter.getName() + "-"
					+ systemParameter.getDisplayOrder());
		}
		logger.info("*****testGetSystemParameterByCode end*****");
	}
	
	
	@SuppressWarnings("static-access")
    @Test
	public void testGetItems()  {
		List<Item> items = dictionaryService.getItems(dictionaryService.TYPE_COMPANY_PROPERTY);

		logger.info("*****testGetItems begin*****");
		Item item;
		for (int i = 0; i < items.size(); i++) {
			item = items.get(i);
			logger.info(item.getItemType() + "-"
					+ item.getItemCode() + "-"
					+ item.getItemName() + "-"
					+ item.getDisplayOrder());
		}
		logger.info("*****testGetItems end*****");
	}
	
	@Test
	public void testGetItemByCode()  {
		Item item = dictionaryService
				.getItemByCode(DictionaryService.TYPE_COMPANY_PROPERTY, "52a7d6cb24b72a26b80983fd");

		logger.info("*****testGetItemByCode begin*****");
		if (item != null) {
			logger.info(item.getItemType() + "-"
					+ item.getItemCode() + "-"
					+ item.getItemName() + "-"
					+ item.getDisplayOrder());
		}
		logger.info("*****testGetItemByCode end*****");
	}
	
	
	@Test
	public void testGetDitionary()  {
		List<Item> items = dictionaryService.getDitionary();

		logger.info("*****testGetDitionary begin*****");
		Item item;
		for (int i = 0; i < items.size(); i++) {
			item = items.get(i);
			logger.info(item.getItemType() + "-"
					+ item.getItemCode() + "-"
					+ item.getItemName() + "-"
					+ item.getDisplayOrder());
		}
		logger.info("*****testGetDitionary end*****");
	}
	
	@Test
	public void testSaveOnline()  {
		dictionaryService.getDitionary();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSaveUser() {
		Cache dicCache = cacheManager.getCache("dicCache");
		for (int i = 0; i < 10; i++) {
			Element element = dicCache.get("key");

			if (element != null) {
				printTree((List<RegionNode>) element.getObjectValue());
				logger.info(element);
				// element = new Element("key", cacheObject);
				// dicCache.put(element);
				// System.out.println("cacheObject[" + cacheObject + "]" +
				// ",无法从缓存中取到");
			} else {
				// cacheObject = (CacheObject) element.getValue();
				// System.out.println("cacheObject[" + cacheObject + "]" +
				// ",从缓存中取到");
			}
		}
	}

	
	@Test
	public void testGetCurrencyTypes()  {
		List<CurrencyType> items = dictionaryService.getCurrencyTypes();
		logger.info("*****testGetCurrencyTypes begin*****");
		for (CurrencyType item: items) {
			logger.info(item.toString());
		}
		logger.info("*****testGetCurrencyTypes end*****");
	}
	
	@Test
	public void testGetCurrencyTypeByCode()  {
		CurrencyType item = dictionaryService.getCurrencyTypeByCode("USD");
		logger.info("*****testGetCurrencyTypeByCode begin*****");
		
			logger.info(item.toString());
		
		logger.info("*****testGetCurrencyTypeByCode end*****");
	}
	
	@Test
	public void testGetRMBRate()  {
		Double item = dictionaryService.getRMBRate("USD", 1001);
		logger.info("*****testGetCurrencyTypeByCode begin*****");
		logger.info(item);
		logger.info("*****testGetCurrencyTypeByCode end*****");
	}
	
	@Test
	public void testGetDevelopQualifications()  {
		List<DevelopQualificationType> items = dictionaryService.getDevelopQualifications();
		logger.info("*****testGetDevelopQualifications begin*****");
		for(DevelopQualificationType item : items)
			logger.info(item.toString());
		logger.info("*****testGetDevelopQualifications end*****");
	}
	
	@Test
	public void testGetDevelopQualificationByCode()  {
		DevelopQualificationType item = dictionaryService.getDevelopQualificationByCode("1");
		logger.info("*****testDevelopQualificationByCode begin*****");
		logger.info(item);
		logger.info("*****testDevelopQualificationByCode end*****");
	}
	
	@Test
	public void testGetDevelopBusinessAreas()  {
		List<DeveloperBusinessArea> items = dictionaryService.getDeveloperBusinessAreas();
		logger.info("*****testGetDevelopBusinessAreas begin*****");
		for(DeveloperBusinessArea item : items)
			logger.info(item.toString());
		logger.info("*****testGetDevelopBusinessAreas end*****");
	}
	
	@Test
	public void testGetDeveloperBusinessAreaByCode()  {
		DeveloperBusinessArea item = dictionaryService.getDeveloperBusinessAreaByCode("1");
		logger.info("*****testGetDeveloperBusinessAreaByCode begin*****");
		logger.info(item);
		logger.info("*****testGetDeveloperBusinessAreaByCode end*****");
	}
	
	@Test
	public void testUpdateCurrencyTypeByCode(){
	    
		logger.info("*****testUpdateCurrencyTypeByCode begin*****");
		dictionaryService.updateCurrencyTypeByCode("USD", 6.303, 1);
		CurrencyType item = dictionaryService.getCurrencyTypeByCode("USD");
		logger.info(item);
		logger.info("*****testUpdateCurrencyTypeByCode end*****");
	}


}
