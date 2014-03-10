package cn.smarthome.sap.util;

import android.content.Context;
import cn.smarthome.sap.dao.UserInfoDao;
import cn.smarthome.sap.db.SqliteHelper;
import cn.smarthome.sap.model.UserInfo;

public class SapUtil {

	private static SqliteHelper sh;
	private static UserInfoDao uiDao;
	private static UserInfo ui;
	
	public static UserInfo getCurrentUser(Context context){
		
		sh = new SqliteHelper(context);
		uiDao = new UserInfoDao(sh);
		ui = uiDao.getLastUser();
		
		return ui;
	}

}
