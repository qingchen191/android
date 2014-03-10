package cn.smarthome.sap.model;

import cn.smarthome.sap.db.AbstractBaseModel;

public class DeviceDetailInfo extends AbstractBaseModel {
	private static final long serialVersionUID = 1L;

	private String id;
	private int kindID; //设备种类ID
	private int typeID; //设备类型ID
	private int areaID; //所在区域ID
	private String deviceIndex; //设备序列号
	private String deviceName; //设备名称
	private int currentUserID; //使用人ID
	private int status; //设备状态
	private String statusValue; //设备状态值
	
	public DeviceDetailInfo(){
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

	public String getDeviceIndex() {
		return deviceIndex;
	}

	public void setDeviceIndex(String deviceIndex) {
		this.deviceIndex = deviceIndex;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public int getCurrentUserID() {
		return currentUserID;
	}

	public void setCurrentUserID(int currentUserID) {
		this.currentUserID = currentUserID;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getAreaID() {
		return areaID;
	}

	public void setAreaID(int areaID) {
		this.areaID = areaID;
	}

	public String getStatusValue() {
		return statusValue;
	}

	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}

	
}
