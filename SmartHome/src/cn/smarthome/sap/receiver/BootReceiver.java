package cn.smarthome.sap.receiver;

import cn.smarthome.sap.dao.LogInfoDao;
import cn.smarthome.sap.db.SqliteHelper;
import cn.smarthome.sap.service.SocketService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class BootReceiver extends BroadcastReceiver {

	public static String BOOT_S = "android.intent.action.BOOT_COMPLETED";
	private static final String TAG = "BootReceiver";  

	private SqliteHelper sh;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Log.i(TAG, "Boot this system , BootBroadcastReceiver onReceive()");

		sh = new SqliteHelper(context);
		LogInfoDao logDao = new LogInfoDao(sh);
		logDao.insertLog("receiver", "收到事件。" + intent.getAction());
		if (intent.getAction().equals(BOOT_S)) {
			Toast.makeText(context, "You have Receiver Broadcast，开机了！",
					Toast.LENGTH_LONG).show();
			// 通过发送一个Intent的方式来启动一个服务
			// 如果是本地service也可以直接写上类名
			logDao.insertLog("receiver", "事件为android.intent.action.BOOT_COMPLETED开机事件。");

			context.startService(new Intent(context, SocketService.class));
		}
	}

}
