package cn.smarthome.sap.app;

import android.os.Handler;
import android.os.Message;
import android.view.View;

public class ViewRefreshHandler extends Handler {
	private View view;
	public ViewRefreshHandler(View view){
		this.view = view;
	}
	 @Override  
     public void handleMessage(Message msg) {  
         super.handleMessage(msg);  
         if(msg.what == 1) { // 更新UI  
//        	 try {
//				Class cc =Class.forName("cn.smarthome.sap.activity.CategoryActivity");
//			} catch (ClassNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} 
//        	 view.invalidate(); 
        	 SapAPP.getInstance().refreshAllViews();
         }  
     }  
}
