/**
 * 
 */
package com.mysoft.b2b.basicsystem.settings.util;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.mysoft.b2b.basicsystem.settings.api.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;

import com.mysoft.b2b.commons.exception.PlatformUncheckException;

/**
 * @author liucz
 * 
 */
public class SettingsUtil {
	public static final String COLLECTION_NAME = "infr_dictionary";
	public static final String FIELD_ITEM_ID = "_id";
	public static final String FIELD_ITEM_TYPE = "item_type";
	public static final String FIELD_ITEM_CODE = "item_code";
	public static final String FIELD_ITEM_NAME = "item_name";
	public static final String FIELD_ITEM_VALUE = "item_value";
	public static final String FIELD_ITEM_STATUS = "item_status";
	public static final String FIELD_PARENT_ITEM_CODE = "parent_item_code";
	public static final String FIELD_LAST_MODIFIED_TIME = "last_modified_time";
	public static final String FIELD_DISPLAY_ORDER = "display_order";
	public static final String FIELD_AREA = "area";
	public static final String FIELD_RATE = "rate";
	//
	public static final String CACHE_KEY_MAP_REGION_NODE = "RegionNode";
	public static final String CACHE_KEY_MAP_PROVIDER_TYPE = "ProviderType";
    public static final String CACHE_KEY_MAP_SUPPLIER_TAG_TYPE = "supplierTagType";
	public static final String CACHE_KEY_MAP_COMPANY_PROPERTY = "CompanyProperty";
	public static final String CACHE_KEY_MAP_SYSTEM_PARAMETER = "SystemParameter";
	public static final String CACHE_KEY_MAP_DICTIONRY = "Dictionry";
	public static final String CACHE_KEY_MAP_CURRENCY_TYPE = "CurrencyType";
	public static final String CACHE_KEY_MAP_DEVELOP_Q_TYPE = "DevelopQualificationType";
	public static final String CACHE_KEY_MAP_DEVELOP_BUSINESS_AREA_TYPE = "DeveloperBusinessAreaType";
	//
	public static final String CACHE_DICTIONRY = "dictionry";
	public static final String CACHE_REGION = "region";
	public static final String CACHE_PROVIDER_TYPE = "providerType";
    public static final String CACHE_SUPPLIER_TAG_TYPE = "supplierTagType";
	public static final String CACHE_COMPANY_PROPERTY = "companyProperty";
	public static final String CACHE_SYSTEM_PARAMETER = "systemParameter";
	public static final String CACHE_CURRENCY_TYPE = "currencyType";
	public static final String CACHE_DEVELOP_Q_TYPE = "developQualificationType";
	public static final String CACHE_DEVELOP_BUSINESS_AREA_TYPE = "developerBusinessAreaType";
	//
	public static final int SORT_VALUE_TYPE_INT = 1;
	public static final int SORT_VALUE_TYPE_STRING = 2;

	/**
	 * 取当前时间
	 * 
	 * @return String
	 */
	public static String getNowDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datetime = null;
		try {
			datetime = format.format(new Date());
		} catch (Exception e) {
			throw new PlatformUncheckException(e.getMessage(), null, e);
		}
		return datetime;
	}

	/**
	 * 克隆区域节点（不含子节点）
	 * 
	 * @param sourceRegionNode
	 *            来源区域节点
	 * @return
	 */
	public static RegionNode cloneRegionNodeWhitoutChildren(RegionNode sourceRegionNode) {
		RegionNode targetRegionNode = new RegionNode();
		initRegion(sourceRegionNode, targetRegionNode);
		return targetRegionNode;
	}

	/**
	 * 区域节点转区域对象。
	 * 
	 * @param sourceRegionNode
	 *            来源区域节点
	 * @return
	 */
	public static Region convertRegionNode2Region(RegionNode sourceRegionNode) {
		Region targetRegion = new Region();
		initRegion(sourceRegionNode, targetRegion);
		return targetRegion;
	}

	/**
	 * 复制区域对象值。
	 * 
	 * @param sourceRegion
	 *            来源区域
	 * @param targetRegion
	 *            目标区域
	 */
	private static void initRegion(Region sourceRegion, Region targetRegion) {
		targetRegion.setCode(sourceRegion.getCode());
		targetRegion.setDisplayOrder(sourceRegion.getDisplayOrder());
		targetRegion.setHierarchyLevel(sourceRegion.getHierarchyLevel());
		targetRegion.setIsLeaf(sourceRegion.getIsLeaf());
		targetRegion.setName(sourceRegion.getName());
		targetRegion.setParentCode(sourceRegion.getParentCode());
		targetRegion.setArea(sourceRegion.getArea());
	}

	/**
	 * 通用数据字典对象转区域对象。
	 * 
	 * @param sourceItem
	 *            来源通用数据字典对象
	 * @return
	 */
	public static Region convertItem2Region(Item sourceItem) {
		Region targetRegion = new Region();
		targetRegion.setCode(sourceItem.getItemCode());
		targetRegion.setDisplayOrder(sourceItem.getDisplayOrder());
		targetRegion.setHierarchyLevel(0);
		targetRegion.setIsLeaf(0);
		targetRegion.setName(sourceItem.getItemName());
		targetRegion.setParentCode(sourceItem.getParentItemCode());
		targetRegion.setArea(sourceItem.getArea());
		return targetRegion;
	}

	/**
	 * 通用数据字典对象转公司类别对象。
	 * 
	 * @param sourceItem
	 *            来源通用数据字典对象
	 * @return
	 */
	public static ProviderType convertItem2ProviderType(Item sourceItem) {
		ProviderType providerType = new ProviderType();
		providerType.setCode(sourceItem.getItemCode());
		providerType.setDisplayOrder(sourceItem.getDisplayOrder());
		providerType.setName(sourceItem.getItemName());
		return providerType;
	}

    /**
     * 通用数据字典对象转公司标签对象。
     *
     * @param sourceItem
     *            来源通用数据字典对象
     * @return
     */
    public static SupplierTag convertItem2SupplierTag(Item sourceItem) {
        SupplierTag providerType = new SupplierTag();
        providerType.setCode(sourceItem.getItemCode());
        providerType.setDisplayOrder(sourceItem.getDisplayOrder());
        providerType.setName(sourceItem.getItemName());
        return providerType;
    }
	/**
	 * 通用数据字典对象转公司类别对象。
	 * 
	 * @param sourceItem
	 *            来源通用数据字典对象
	 * @return
	 */
	public static CompanyProperty convertItem2CompanyProperty(Item sourceItem) {
		CompanyProperty companyProperty = new CompanyProperty();
		companyProperty.setCode(sourceItem.getItemCode());
		companyProperty.setDisplayOrder(sourceItem.getDisplayOrder());
		companyProperty.setName(sourceItem.getItemName());
		return companyProperty;
	}
	
	/**
	 * 通用数据字典对象转货币类别对象。
	 * @param sourceItem  来源通用数据字典对象
	 * @return 
	 */
	public static CurrencyType convertItem2CurrencyType(Item sourceItem) {
		CurrencyType currencyType = new CurrencyType();
		currencyType.setCode(sourceItem.getItemCode());
		currencyType.setDisplayOrder(sourceItem.getDisplayOrder());
		currencyType.setName(sourceItem.getItemName());
		currencyType.setStatus(sourceItem.getItemStatus());
		currencyType.setRate(sourceItem.getRate());
		return currencyType;
	}
	/**
	 * 通用数据字典对象转开发资质对象
	 * @param sourceItem
	 * @return
	 */
	public static DevelopQualificationType convertItem2DevelopQualificationType(Item sourceItem) {
		DevelopQualificationType developQualificationType = new DevelopQualificationType();
		developQualificationType.setCode(sourceItem.getItemCode());
		developQualificationType.setDisplayOrder(sourceItem.getDisplayOrder());
		developQualificationType.setName(sourceItem.getItemName());
		return developQualificationType;
	}
	
	/**
	 * 通用数据字典对象转开发商业务领域对象
	 * @param sourceItem
	 * @return
	 */
	public static DeveloperBusinessArea convertItem2DeveloperBusinessArea(Item sourceItem) {
		DeveloperBusinessArea developerBusinessArea = new DeveloperBusinessArea();
		developerBusinessArea.setCode(sourceItem.getItemCode());
		developerBusinessArea.setDisplayOrder(sourceItem.getDisplayOrder());
		developerBusinessArea.setName(sourceItem.getItemName());
		developerBusinessArea.setStatus(sourceItem.getItemStatus());
		return developerBusinessArea;
	}
	
	/**
	 * 通用数据字典对象转公司类别对象。
	 * 
	 * @param sourceItem
	 *            来源通用数据字典对象
	 * @return
	 */
	public static SystemParameter convertItem2SystemParameter(Item sourceItem) {
		SystemParameter systemParameter = new SystemParameter();
		systemParameter.setCode(sourceItem.getItemCode());
		systemParameter.setDisplayOrder(sourceItem.getDisplayOrder());
		systemParameter.setName(sourceItem.getItemName());
		return systemParameter;
	}

	/**
	 * 字符是否为null或者空串
	 * 
	 * @param value
	 *            字符
	 * @return boolean
	 */
	public static boolean isNullOrEqualsEmpty(final String value) {
		if (value == null || value.equals("")) {
			return true;
		}
		return false;
	}

	/**
	 * 根据属性名获取属性值
	 * 
	 * @param bean
	 *            对象
	 * @param name
	 *            属性名
	 * @return 属性值
	 */
	public static String getValueByProperty(final Object bean, final String name) {
		String value = null;
		Object o;
		try {
			o = BeanUtils.getPropertyDescriptor(bean.getClass(), name).getReadMethod().invoke(bean);
			if (o != null) {
				value = o.toString();
			}
		} catch (BeansException e) {
			throw new PlatformUncheckException(e.getMessage(), null, e);
		} catch (IllegalArgumentException e) {
			throw new PlatformUncheckException(e.getMessage(), null, e);
		} catch (IllegalAccessException e) {
			throw new PlatformUncheckException(e.getMessage(), null, e);
		} catch (InvocationTargetException e) {
			throw new PlatformUncheckException(e.getMessage(), null, e);
		}
		
		/*
		 * try { value = BeanUtils.getPropertyDescriptor(bean.getClass(),
		 * name).getValue(name); value = BeanUtils.getProperty(bean, name); }
		 * catch (IllegalAccessException e) { e.printStackTrace(); } catch
		 * (InvocationTargetException e) { e.printStackTrace(); } catch
		 * (NoSuchMethodException e) { e.printStackTrace(); }
		 */
		return value;
	}

	/**
	 * 集合是否为空
	 * 
	 * @param collection
	 *            集合
	 * @return boolean
	 */
	@SuppressWarnings("rawtypes")
	public static boolean checkIsNullOrEmpty(final Collection collection) {
		if (collection == null) {
			return true;
		}
		if (collection.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * 根据属性名按升序排序List
	 * 
	 * @param listBean
	 *            Beand List
	 * @param property
	 *            排序属性名
	 * @param valueType
	 *            属性值类型
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void sortListByPropertyInAsc(final List listBean, final String property, final int valueType) {
		if (checkIsNullOrEmpty(listBean)) {
			return;
		}
		Collections.sort(listBean, new Comparator() {
			public int compare(final Object obj0, final Object obj1) {
				String val0 = getValueByProperty(obj0, property);
				if (isNullOrEqualsEmpty(val0)) {
					val0 = "0";
				}
				String val1 = getValueByProperty(obj1, property);
				if (isNullOrEqualsEmpty(val1)) {
					val1 = "0";
				}
				if (valueType == SORT_VALUE_TYPE_INT) {
					long i0 = Long.parseLong(val0);
					long i1 = Long.parseLong(val1);
					if (i0 == i1) {
						return 0;
					} else if (i0 > i1) {
						return 1;
					} else {
						return -1;
					}
				} else {
					return val0.compareTo(val1);
				}
			}
		});
	}

	/**
	 * 多属性按字符升序排序List
	 * 
	 * @param listBean
	 *            Beand List
	 * @param properties
	 *            排序属性名(example: name01,name02,name03)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void sortListByPropertiesInAsc(final List listBean, final String properties) {
		if (checkIsNullOrEmpty(listBean)) {
			return;
		}
		Collections.sort(listBean, new Comparator() {
			public int compare(final Object obj0, final Object obj1) {
				String val0;
				String val1;
				int result = 0;
				String[] names = properties.split(",");
				for (int i = 0; i < names.length; i++) {
					val0 = getValueByProperty(obj0, names[i]);
					if (isNullOrEqualsEmpty(val0)) {
						val0 = "0";
					}
					val1 = getValueByProperty(obj1, names[i]);
					if (isNullOrEqualsEmpty(val1)) {
						val1 = "0";
					}
					result = val0.compareTo(val1);
					if (result != 0) {
						break;
					}
				}

				return result;
			}
		});
	}

}
