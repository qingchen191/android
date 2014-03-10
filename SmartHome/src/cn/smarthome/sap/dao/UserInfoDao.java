package cn.smarthome.sap.dao;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import cn.smarthome.sap.db.AbstractBaseDao;
import cn.smarthome.sap.db.SqliteHelper;
import cn.smarthome.sap.model.UserInfo;

public class UserInfoDao extends AbstractBaseDao<UserInfo> {

	public UserInfoDao(SqliteHelper sqliteHelper){
		setSqliteHelper(sqliteHelper);
	}
	
//	public UserInfo getUserInfo(){
//		
//	}
	
	public List<UserInfo> getAllUser(){
		return getList(null, null, "order by updateTime desc");
	}
	
	public List<String> getAllUserName(){
		List<String> userNameList = new ArrayList<String>();
		List<UserInfo> userInfoList = getAllUser();
		for(UserInfo ui : userInfoList){
			userNameList.add(ui.getLoginUserID());
		}
		return userNameList;
	}
	public UserInfo getLastUser(){
		List<UserInfo> userInfoList = getAllUser();
		if(null != userInfoList && userInfoList.size() > 0){
			return userInfoList.get(0);
		} else {
			return null;
		}
	}
	
	public void deleteAllUsers(){
		Log.i(TAG, "deleteAllUsers");
		String sqlString="DELETE FROM "+this.getEntityClass().getSimpleName();
		execSql(sqlString, new Object[]{});
	}
}
