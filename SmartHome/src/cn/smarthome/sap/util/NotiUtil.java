package cn.smarthome.sap.util;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import cn.smarthome.sap.R;
import cn.smarthome.sap.activity.CategoryActivity;

public class NotiUtil {

	 // 默认显示的的Notification  
   public static void showDefaultNotification(Context context) {  
 
       // 创建一个通知  
       Notification mNotification = new Notification();  
 
       // 设置属性值  
       mNotification.icon = R.drawable.ic_launcher;  
       mNotification.tickerText = "您有新的通知。。。";  
       
       mNotification.when = System.currentTimeMillis(); // 立即发生此通知  
 
       // 带参数的构造函数,属性值如上  
       // Notification mNotification = = new Notification(R.drawable.icon,"NotificationTest", System.currentTimeMillis()));  
 
       // 添加声音效果  
       // 自定义声音
//       mNotification.sound = Uri.parse("file:///sdcard/notification/ringer.mp3");
       mNotification.defaults |= Notification.DEFAULT_SOUND;  
 
       // 添加震动,后来得知需要添加震动权限 : Virbate Permission
       // 自定义震动
//       long[] vibrate = {0,100,200,300};  
//       mNotification.vibrate = vibrate;  
       mNotification.defaults |= Notification.DEFAULT_VIBRATE ;   

       // 添加闪光效果
       // 自定义闪光
//       mNotification.ledARGB = 0xff00ff00;  
//       mNotification.ledOnMS = 300;  
//       mNotification.ledOffMS = 1000;  
//       mNotification.flags |= Notification.FLAG_SHOW_LIGHTS; 
       mNotification.defaults |= Notification.DEFAULT_LIGHTS; 
 
       //添加状态标志   
 
       //FLAG_AUTO_CANCEL         通知被点击后，自动消失
       //FLAG_NO_CLEAR            点击'Clear'时，不清楚该通知(QQ的通知无法清除，就是用的这个
       //FLAG_ONGOING_EVENT       通知放置在正在运行  
       //FLAG_INSISTENT           让声音、振动无限循环，直到用户响应
       mNotification.flags = Notification.FLAG_AUTO_CANCEL ;  
 
       //将该通知显示为默认View  
       PendingIntent contentIntent = PendingIntent.getActivity  
                          (context, 0,new Intent(context, CategoryActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);  
       mNotification.setLatestEventInfo(context, "发送心跳消息", "一般般哟。。。。",contentIntent);  
         
       // 设置setLatestEventInfo方法,如果不设置会App报错异常  
       NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);  
         
       //注册此通知   
       // 如果该NOTIFICATION_ID的通知已存在，会显示最新通知的相关信息 ，比如tickerText 等  
       mNotificationManager.notify(2, mNotification);  
 
   }  
}
