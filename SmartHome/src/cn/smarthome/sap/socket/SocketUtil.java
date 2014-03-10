package cn.smarthome.sap.socket;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import cn.smarthome.sap.util.Constants;

import android.util.Log;

public class SocketUtil {

	private static final String TAG = "SocketUtil";
	
    public static Socket socket = null;
    public static DataOutputStream dos = null;
    public static String receivedContent = "";
    public static BufferedReader in = null;
    
    
    public static boolean sendMessage(String message){
    	boolean rtn = false;
    	if(null != socket){
    		if(socket.isConnected()){
        		Log.i(TAG, "send message to server.[" + message + "]");
        		message = message + "\r\n";
        		try {
    				dos.write(message.getBytes());
    				rtn = true;
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		} else {
//    			socket.connect(remoteAddr);
        		Log.e(TAG,"Send message failed, socket is disconnected!");
    		}
    	} else {
//    		socket = new Socket(Constants.CENTERHOST, Constants.CENTERPORT);
//    		dos = new DataOutputStream(socket.getOutputStream());
    		Log.e(TAG,"Send message failed, socket is null!");
    	}
    	return rtn;
    }

}
