/**
 * 
 */
package com.mysoft.b2b.basicsystem.settings.api;

import java.util.List;

/**
 * （云平台）基础参数(数据字典),提供基础参数(数据字典)相关服务
 * @author liucz
 *
 */
public interface DictionaryService {	
	public static final String TYPE_REGION = "region";
	public static final String TYPE_PROVIDER_TYPE = "provider_type";
	public static final String TYPE_COMPANY_PROPERTY = "company_property";
	public static final String TYPE_SYSTEM_PARAMETER = "system_parameter";
	public static final String TYPE_CURRENCY_PROPERTY = "currency_type";
	public static final String TYPE_DEVELOP_Q_TYPE = "develop_qualification_type";
	public static final String TYPE_DEVELOP_BUSINESS_AREA_TYPE = "developer_business_area_type";
    public static final String TYPE_SUPPLIER_TAG_TYPE = "supplier_tag_type";
	
	/**
	 * 查询区域树接口(返回启用状态的整个区域树)
	 * 
	 * @return RegionNode
	 * 
	 */
	List<RegionNode> getRegionRootHierarchy();
	
	/**
	 * 查询上级区域树接口(返回启用状态的当前节点至根节点树)
	 * 
	 * @param code 区域代码
	 * @return RegionNode
	 * 
	 */
	RegionNode getRegionSuperHierarchy(String code);		
	
	/**
	 * 查询子级区域树接口(返回启用状态的当前节点至末级节点树)
	 * 
	 * @param code 区域代码
	 * @return RegionNode
	 * 
	 */
	RegionNode getRegionHierarchyByCode(String code);
	
	/**
	 * 查询子区域列表接口(返回启用状态的直接下级区域记录列表)
	 * 
	 * @param code 区域代码
	 * @return List<Region>
	 * 
	 */
	List<Region> getChildrenRegions(String code);
	
	/**
	 * 根据片区查询区域列表接口(返回启用状态的区域记录列表)
	 * 
	 * @param area 区域代码
	 * @return List<Region>
	 * 
	 */
	List<Region> getRegionsByArea(String area);
	
	/**
	 * 查询单个区域接口(返回指定的区域记录[不区分状态])
	 * 
	 * @param code 区域代码
	 * @return Region
	 * 
	 */
	Region getRegionByCode(String code);
	
	/**
	 * 查询指定级数的区域列表接口(返回启用状态的指定级数的区域记录列表)
	 * 
	 * @param level 级数
	 * @return List<Region>
	 * 
	 */
	List<Region> getChildrenRegionsWithLevel(int level);
		
	/**
	 * 查询全部公司类别接口(返回启用状态的全部公司类别列表)
	 * 
	 * @return List<ProviderType>
	 * 
	 */
	List<ProviderType> getProviderTypes();
	
	/**
	 * 查询指定公司类别接口(返回指定的公司类别记录[不区分状态])
	 * 
	 * @param code 公司类别代码
	 * @return ProviderType
	 * 
	 */
	ProviderType getProviderTypeByCode(String code);
	
	/**
	 * 查询全部企业性质接口(返回启用状态的全部公司性质列表)
	 * 
	 * @return List<CompanyProperty>
	 * 
	 */
	List<CompanyProperty> getCompanyPropertys();
	
	/**
	 * 查询指定企业性质接口(返回指定的企业性质记录[不区分状态])
	 * 
	 * @param code 企业性质代码
	 * @return CompanyProperty
	 * 
	 */
	CompanyProperty getCompanyPropertyByCode(String code);
	
	/**
	 * 查询全部系统参数接口(返回启用状态的全部系统参数列表)
	 * 
	 * @return List<SystemParameter>
	 * 
	 */
	List<SystemParameter> getSystemParameters();
	
	/**
	 * 查询指定系统参数接口(返回指定的系统参数记录[不区分状态])
	 * 
	 * @param code 系统参数代码
	 * @return SystemParameter
	 * 
	 */
	SystemParameter getSystemParameterByCode(String code);
	
	/**
	 *查询数据字典记录列表接口(返回启用状态的指定数据类型的全部列表)
	 * 
	 * @param itemType 数据类型代码
	 * @return List<Item>
	 * 
	 */
	List<Item> getItems(String itemType);
	
	/**
	 * 查询指定数据字典记录接口（返回指定的数据字典类型和代码的记录[不区分状态]）。
	 * 
	 * @param itemType 数据类型代码
	 * @param itemCode 数据代码
	 * @return Item
	 * 
	 */
	Item getItemByCode(String itemType, String itemCode);
	
	/**
	 *查询整个数据字典记录列表接口(返回全部数据字典记录)
	 * 
	 * @return List<Item>
	 * 
	 */
	List<Item> getDitionary();
	
	/**
	 * 查询所有的币种
	 * @return
	 */
	List<CurrencyType> getCurrencyTypes();
	/**
	 * 根据币种编码查询币种信息
	 * @param code
	 * @return
	 */
	CurrencyType getCurrencyTypeByCode(String code);
	/**
	 * 更新币种信息（汇率、是否启用）
	 * @param code
	 * @param rate
	 * @param status
	 * @return
	 */
	boolean updateCurrencyTypeByCode(String code, Double rate, int status);
	/**
	 * 根据币种和数字值，经过汇率计算得到人民币
	 * @param code 币种编码
	 * @param num 待转化的数字
	 * @return 人民币数值
	 */
	Double getRMBRate(String code, float num);
	
	/**
	 * 查询所有的开发资质类型
	 * @return
	 */
	List<DevelopQualificationType> getDevelopQualifications();
	
	/**
	 * 根据编码查询开发资质
	 * @param code
	 * @return
	 */
	DevelopQualificationType getDevelopQualificationByCode(String code);
	
	/**
	 * 查询所有开发商业务领域类型对象
	 * @return
	 */
	List<DeveloperBusinessArea> getDeveloperBusinessAreas();
	/**
	 * 根据编码查询开发商业务领域对象
	 * @param code
	 * @return
	 */
	DeveloperBusinessArea getDeveloperBusinessAreaByCode(String code);


    /**
     * 查询所有的公司标签
     * @return
     */
    List<SupplierTag> getSupplierTagTypes();

    /**
     * 根据编码查询公司标签
     * @param code
     * @return
     */
    SupplierTag getSupplierTagByKey(String code);
    
	/**
	 * 同步区域数据到Mysql数据库
	 * 
	 * @return
	 */
	void syncRegionDataToMysqlDB();
	
}
