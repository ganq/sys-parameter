package com.mysoft.b2b.basicsystem.settings.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * （云平台）数据字典--区域节点
 * 
 * @author liucz
 * 
 */
public class RegionNode extends Region implements Serializable {
	private static final long serialVersionUID = 1602412974471822606L;
	// 子分类
	private List<RegionNode> childRegionNodes = new ArrayList<RegionNode>();
	public List<RegionNode> getChildRegionNodes() {
		return childRegionNodes;
	}
	public void setChildRegionNodes(List<RegionNode> childRegionNodes) {
		this.childRegionNodes = childRegionNodes;
	}
	
	public void addChildRegionNode(RegionNode child) {
		getChildRegionNodes().add(child);
	}
}
