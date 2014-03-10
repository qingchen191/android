package cn.smarthome.sap.service;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import cn.smarthome.sap.R;
import cn.smarthome.sap.activity.MainActivity;
import cn.smarthome.sap.dao.LogInfoDao;
import cn.smarthome.sap.db.SqliteHelper;
import cn.smarthome.sap.socket.SocketThread;
import cn.smarthome.sap.socket.SocketUtil;
import cn.smarthome.sap.util.Constants;
import cn.smarthome.sap.util.NotiUtil;
import cn.smarthome.sap.util.PhoneInfoUtil;
import cn.smarthome.sap.util.SapUtil;

public class SocketService extends Service {

	private static final String TAG = "SocketService";
	private IBinder binder = new SocketService.LocalBinder();

	private SqliteHelper sh;
	private LogInfoDao logDao;

	@Override
	public IBinder onBind(Intent intent) {

		return binder;
	}

	@Override
	public void onCreate() {
		Log.i(TAG, "onCreate");
		// 这里可以启动媒体播放器
		// if(mediaPlayer==null)
		// mediaPlayer=MediaPlayer.create(this, uri);
		super.onCreate();
		
		sh = new SqliteHelper(this);
		logDao = new LogInfoDao(sh);
		logDao.insertLog(TAG, "调用onCreate方法。");

//		socket = new SocketClient("10.101.13.191", 9999);
//		socket.run();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		Log.i(TAG, "onStart");
		super.onStart(intent, startId);
		logDao.insertLog(TAG, "调用onStart方法。");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i(TAG, "onStartCommand");
		logDao.insertLog(TAG, "调用onStartCommand方法。");
		
		new SocketThread(sh, this).start();// 开启客户端socket线程
		String msgTypeLGI = Constants.MSGTYPE_LGI;
		final int userID = SapUtil.getCurrentUser(this).getUserID();
		final String phoneMAC = PhoneInfoUtil.getLocalMacAddress(this);
		String messageLGI = msgTypeLGI + "," + userID + "," + phoneMAC;
		
		SocketUtil.sendMessage(messageLGI); //发送建立socket连接消息
		
		// 启动心跳线程
		Timer heartBeatTimer = new Timer();
		TimerTask heartBeatTask = new TimerTask() {

			@Override
			public void run() {
				//显示通知
//				NotiUtil.showDefaultNotification(SocketService.this);
				String msgTypeHBT = Constants.MSGTYPE_HBT;
				String messageHBT = msgTypeHBT + "," + userID + "," + phoneMAC;
				
				SocketUtil.sendMessage(messageHBT);
				Log.i(TAG, "send heart to server.");
				logDao.insertLog(TAG, "send heart to server.");
			}
		};
		
		heartBeatTimer.schedule(heartBeatTask, 1000 * 60, 1000 * 60 * 3); //3分钟一次心跳
		
		Log.i(TAG, "SocketThread started!!!!!!!!!");
//		socket.sendMsg("test android");
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		Log.i(TAG, "onDestroy");
		super.onDestroy();
	}

	// 定义内容类继承Binder
	public class LocalBinder extends Binder {
		// 返回本地服务
		SocketService getService() {
			return SocketService.this;
		}
	}
	
	 // 默认显示的的Notification  
    private void showDefaultNotification() {  
  
        // 创建一个通知  
        Notification mNotification = new Notification();  
  
        // 设置属性值  
        mNotification.icon = R.drawable.ic_launcher;  
        mNotification.tickerText = "您有新的通知。。。";  
        
        mNotification.when = System.currentTimeMillis(); // 立即发生此通知  
  
        // 带参数的构造函数,属性值如上  
        // Notification mNotification = = new Notification(R.drawable.icon,"NotificationTest", System.currentTimeMillis()));  
  
        // 添加声音效果  
        mNotification.defaults |= Notification.DEFAULT_SOUND;  
  
        // 添加震动,后来得知需要添加震动权限 : Virbate Permission  
        //mNotification.defaults |= Notification.DEFAULT_VIBRATE ;   
  
        //添加状态标志   
  
        //FLAG_AUTO_CANCEL          该通知能被状态栏的清除按钮给清除掉  
        //FLAG_NO_CLEAR                 该通知能被状态栏的清除按钮给清除掉  
        //FLAG_ONGOING_EVENT      通知放置在正在运行  
        //FLAG_INSISTENT                通知的音乐效果一直播放  
        mNotification.flags = Notification.FLAG_INSISTENT ;  
  
        //将该通知显示为默认View  
        PendingIntent contentIntent = PendingIntent.getActivity  
                           (SocketService.this, 0,new Intent("cn.smarthome.sap.activity.CategoryActivity"), PendingIntent.FLAG_UPDATE_CURRENT);  
        mNotification.setLatestEventInfo(SocketService.this, "发送心跳消息", "一般般哟。。。。",contentIntent);  
          
        // 设置setLatestEventInfo方法,如果不设置会App报错异常  
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);  
          
        //注册此通知   
        // 如果该NOTIFICATION_ID的通知已存在，会显示最新通知的相关信息 ，比如tickerText 等  
        mNotificationManager.notify(2, mNotification);  
  
    }  

}
