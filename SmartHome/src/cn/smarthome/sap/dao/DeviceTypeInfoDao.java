package cn.smarthome.sap.dao;

import cn.smarthome.sap.db.AbstractBaseDao;
import cn.smarthome.sap.db.SqliteHelper;
import cn.smarthome.sap.model.DeviceTypeInfo;

public class DeviceTypeInfoDao extends AbstractBaseDao<DeviceTypeInfo> {

	public DeviceTypeInfoDao(SqliteHelper sqliteHelper){
		setSqliteHelper(sqliteHelper);
	}

}
