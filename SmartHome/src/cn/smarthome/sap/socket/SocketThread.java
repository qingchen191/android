package cn.smarthome.sap.socket;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import cn.smarthome.sap.activity.CategoryActivity;
import cn.smarthome.sap.app.SapAPP;
import cn.smarthome.sap.dao.DeviceDetailInfoDao;
import cn.smarthome.sap.dao.LogInfoDao;
import cn.smarthome.sap.db.SqliteHelper;
import cn.smarthome.sap.model.DeviceDetailInfo;
import cn.smarthome.sap.util.Constants;

import android.app.Service;
import android.content.Intent;
import android.os.Message;
import android.util.Log;

public class SocketThread extends Thread {

	private static final String TAG = "SocketThread";
	private SqliteHelper sh;
	private Service service;

	private SapAPP mAPP;  
	
	public SocketThread(SqliteHelper sh, Service service){
		this.sh = sh;
		this.service = service;
	}
	
    public void run() {
        try {

        	mAPP = SapAPP.getInstance();  
        	
    		LogInfoDao logDao = new LogInfoDao(sh);
    		logDao.insertLog(TAG, "线程启动。");
    		
    		
        	SocketUtil.socket = new Socket(Constants.CENTERHOST, Constants.CENTERPORT);
        	Log.i(TAG, "socket connected to server.");
        	SocketUtil.dos = new DataOutputStream(
        			SocketUtil.socket.getOutputStream());

//            SocketUtil.sendMessage("socket client started and connected to the server.");

            SocketUtil.in = new BufferedReader(new InputStreamReader(SocketUtil.socket.getInputStream()));

			while (true) {
				if (SocketUtil.socket.isConnected()) {
					if (!SocketUtil.socket.isInputShutdown()) {
						if ((SocketUtil.receivedContent = SocketUtil.in.readLine()) != null) {
							Log.i(TAG, SocketUtil.receivedContent);
							// TODO 处理接收到的数据。
							handleMessage(SocketUtil.receivedContent);

							refreshView();
						}
					} else {
						Log.e(TAG, "read client.isInputShutdown");
					}
				} else {
					SocketAddress socketAddress = new InetSocketAddress(Constants.CENTERHOST, Constants.CENTERPORT);
					SocketUtil.socket.connect(socketAddress);
					Log.e(TAG, "read client.isDisConnected");
				}
			}
        } catch (IOException e) {
            // 
            e.printStackTrace();
        }
        
    }

	private void refreshView() {
		Message msg = new Message();
		msg.what = 1;
		
		if(mAPP.getHandler() != null){
			mAPP.getHandler().sendMessage(msg);
		}
	}

	private void handleMessage(String receivedContent) {

		DeviceDetailInfoDao deviceDetailDao = new DeviceDetailInfoDao(sh);
		// 
		if(receivedContent.startsWith("CMD")){
    		String[] params = receivedContent.split(",");
    		
    		if(params[1].equals("SWT")){
    			// 判断命令执行结果状态
        		String deviceAddress = params[2];
        		String deviceStatus = params[3];
        		String cmdStatus = params[4];

        		deviceDetailDao.updateStatus(deviceAddress, deviceStatus);

        		
        		Intent intent = new Intent("cn.smarthome.sap.message.RECEIVER"); 
        		//发送Action为cn.smarthome.sap.message.RECEIVER的广播  
                intent.putExtra("deviceStatus", deviceStatus);  
                intent.putExtra("cmdStatus", cmdStatus);  
                service.sendBroadcast(intent);  
    		}
    	}
	}

}
