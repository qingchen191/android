package cn.smarthome.sap.model;

import cn.smarthome.sap.db.AbstractBaseModel;

public class DeviceTypeInfo extends AbstractBaseModel {
	private static final long serialVersionUID = 1L;

	private String id;
	private int kindID; //设备种类ID
	private int typeID; //设备类型ID
	private String typeName; //设备类型名称
	private int deviceImageID; //设备图片ID
	private String norm; //规格型号
	private String description;
	
	public DeviceTypeInfo(){
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

	public int getTypeID() {
		return typeID;
	}

	public void setTypeID(int typeID) {
		this.typeID = typeID;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	public int getDeviceImageID() {
		return deviceImageID;
	}

	public void setDeviceImageID(int deviceImageID) {
		this.deviceImageID = deviceImageID;
	}

	public String getNorm() {
		return norm;
	}

	public void setNorm(String norm) {
		this.norm = norm;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
