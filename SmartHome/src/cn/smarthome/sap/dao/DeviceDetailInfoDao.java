package cn.smarthome.sap.dao;

import android.content.ContentValues;
import cn.smarthome.sap.db.AbstractBaseDao;
import cn.smarthome.sap.db.SqliteHelper;
import cn.smarthome.sap.model.DeviceDetailInfo;

public class DeviceDetailInfoDao extends AbstractBaseDao<DeviceDetailInfo> {

	public DeviceDetailInfoDao(SqliteHelper sqliteHelper){
		setSqliteHelper(sqliteHelper);
	}
	
	public DeviceDetailInfo getDeviceByAddress(String address){
		
		return null;
	}

	public void updateStatus(String deviceAddress, String deviceStatus) {
		// TODO Auto-generated method stub
		ContentValues values = new ContentValues();
		values.put("status", Integer.parseInt(deviceStatus));//key为字段名，value为值
		
		update(values, "deviceIndex=?", new String[]{deviceAddress});
	}

}
