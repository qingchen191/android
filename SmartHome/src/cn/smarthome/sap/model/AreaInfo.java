package cn.smarthome.sap.model;

import cn.smarthome.sap.db.AbstractBaseModel;

public class AreaInfo extends AbstractBaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private String areaName; //区域名称
	private int areaID; //区域ID
	private int areaImageID; //区域图片ID
	
	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}
	
	public AreaInfo(){
		super();
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public int getAreaID() {
		return areaID;
	}

	public void setAreaID(int areaID) {
		this.areaID = areaID;
	}

	public int getAreaImageID() {
		return areaImageID;
	}

	public void setAreaImageID(int areaImageID) {
		this.areaImageID = areaImageID;
	}

	
}
