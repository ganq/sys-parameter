package com.mysoft.b2b.basicsystem.settings.api;

import java.io.Serializable;

/**
 * 供应商标签。
 * Created liuza 2014/06/12。
 */
@SuppressWarnings("serial")
public class SupplierTag implements Serializable {
    
    // 名称
    private String name;
    // 代码
    private String code;
    // 排序
    private String displayOrder;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(String displayOrder) {
        this.displayOrder = displayOrder;
    }
}
