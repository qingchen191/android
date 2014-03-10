package cn.smarthome.sap.dao;

import cn.smarthome.sap.db.AbstractBaseDao;
import cn.smarthome.sap.db.SqliteHelper;
import cn.smarthome.sap.model.LogInfo;

public class LogInfoDao extends AbstractBaseDao<LogInfo> {

	public LogInfoDao(SqliteHelper sqliteHelper){
		setSqliteHelper(sqliteHelper);
	}
	
	public void insertLog(String type, String content){
		saveOrUpdate(new LogInfo(type, content));
	}
}
