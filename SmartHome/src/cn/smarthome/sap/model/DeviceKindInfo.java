package cn.smarthome.sap.model;

import cn.smarthome.sap.db.AbstractBaseModel;

public class DeviceKindInfo extends AbstractBaseModel {
	private static final long serialVersionUID = 1L;

	private String id;
	private int kindID; //设备种类ID
	private int kindFlag; //分类标识
	private String kindName; //设备种类名称
	private String description;
	
	public DeviceKindInfo(){
		super();
	}
	
	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public int getKindID() {
		return kindID;
	}

	public void setKindID(int kindID) {
		this.kindID = kindID;
	}

	public int getKindFlag() {
		return kindFlag;
	}

	public void setKindFlag(int kindFlag) {
		this.kindFlag = kindFlag;
	}

	public String getKindName() {
		return kindName;
	}

	public void setKindName(String kindName) {
		this.kindName = kindName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
