package cn.smarthome.sap;

import java.io.BufferedReader;
import java.net.Socket;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class TestActivity extends Activity {

	TextView tv;// 用来显示服务器返回的消息 ：“您好，客户端！”
    Handler mHandler;
    
    Socket socket;
    String receivedContent = "";
	private BufferedReader in = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Button btn = (Button) findViewById(R.id.btnconnect);// 连接服务器的按钮
        btn.setOnClickListener(listener);
        tv = (TextView) findViewById(R.id.tv);
//        mHandler = new MessageHandler();
    }

    OnClickListener listener = new OnClickListener() {

        @Override
        public void onClick(View v) {

    		String url = "http://127.0.0.1:8080/HessianServer/ihessian";

//    		HessianProxyFactory factory = new HessianProxyFactory();
//    		BasicAPI basic;
//    		try {
//    			basic = (BasicAPI) factory.create(BasicAPI.class, url);
//    			 tv.setText(basic.hello());//接收客户端线程发来的Message对象，用来显示
//    			System.out.println("hello(): " + basic.hello());
//    		} catch (MalformedURLException e) {
//    			// TODO Auto-generated catch block
//    			e.printStackTrace();
//    		}
        }
    };
//
//    /**
//     * 异步操作，TextView显示服务器发来的消息
//     * 
//     * @author S.Rain
//     * 
//     */
//    class MessageHandler extends Handler {
//
//        @Override
//        public void handleMessage(Message msg) {
//            tv.setText(msg.obj.toString());//接收客户端线程发来的Message对象，用来显示
//        }
//
//    }
//
//    /**
//     * 客户端线程。连到服务器：192.168.1.102:9998 发送消息，之后接收服务器消息，并在TextView显示
//     * 
//     * @author S.Rain
//     * 
//     */
//    class ReceiveMessageThread extends Thread {
//
//        public void run() {
//            try {
//            	Log.i("sssssssssss", "socket thread run.");
//                socket = new Socket("10.101.13.191", 9999);
//                DataOutputStream dos = new DataOutputStream(
//                        socket.getOutputStream());
//                String content = "test socket client message.\r\n";
//                dos.write(content.getBytes());
//                
//
//                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//
////                DataInputStream dis = new DataInputStream(
////                        socket.getInputStream());
////                Message msg = mHandler.obtainMessage();
////                msg.obj = dis.readUTF();
////                mHandler.sendMessage(msg);
////                socket.close();
////                dos.close();
////                dis.close();
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//            
//
//    		try {
//    			while (true) {
//    				if (socket.isConnected()) {
//    					if (!socket.isInputShutdown()) {
//    						if ((receivedContent = in.readLine()) != null) {
//    							Log.i("sssssssssss", receivedContent);
//    							// TODO 处理接收到的数据。
//    						}
//    					} else {
//    						Log.e("sssssssssss", "read client.isInputShutdown");
//    					}
//    				} else {
//    					Log.e("sssssssssss", "read client.isDisConnected");
//    				}
//    			}
//    		} catch (Exception e) {
//    			e.printStackTrace();
//    		}
//
//        }
//    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test, menu);
		return true;
	}

}
