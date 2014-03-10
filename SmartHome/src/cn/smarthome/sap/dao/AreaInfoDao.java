package cn.smarthome.sap.dao;

import cn.smarthome.sap.db.AbstractBaseDao;
import cn.smarthome.sap.db.SqliteHelper;
import cn.smarthome.sap.model.AreaInfo;

public class AreaInfoDao extends AbstractBaseDao<AreaInfo> {

	public AreaInfoDao(SqliteHelper sqliteHelper){
		setSqliteHelper(sqliteHelper);
	}
}
