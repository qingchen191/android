package cn.smarthome.sap.dao;

import cn.smarthome.sap.db.AbstractBaseDao;
import cn.smarthome.sap.db.SqliteHelper;
import cn.smarthome.sap.model.DeviceKindInfo;

public class DeviceKindInfoDao extends AbstractBaseDao<DeviceKindInfo> {

	public DeviceKindInfoDao(SqliteHelper sqliteHelper){
		setSqliteHelper(sqliteHelper);
	}

}
