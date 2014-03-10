package cn.smarthome.sap.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.smarthome.sap.R;
import cn.smarthome.sap.socket.SocketUtil;
import cn.smarthome.sap.util.Constants;
import cn.smarthome.sap.util.PhoneInfoUtil;

public class ItemActivity extends Activity {

	private String deviceAddress;
	private String itemStatus;
	private MsgReceiver msgReceiver;
	private TextView tvDeviceName;
	private ImageView image;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_item);

		Intent intent = getIntent();
		deviceAddress = intent.getStringExtra("deviceAddress");
		itemStatus = intent.getStringExtra("itemStatus");
		tvDeviceName = (TextView) findViewById(R.id.txDeviceName);
		tvDeviceName.setText(intent.getStringExtra("deviceName"));
		image = (ImageView) findViewById(R.id.imageViewItem);
		
		if(itemStatus.equals("0")){
			image.setImageResource(R.drawable.device_default_light_close);
		} else {
			image.setImageResource(R.drawable.device_default_light_open);
		}


		image.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				sendMessage();
			}
		});

		// 动态注册广播接收器
		msgReceiver = new MsgReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("cn.smarthome.sap.message.RECEIVER");
		registerReceiver(msgReceiver, intentFilter);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.item, menu);
		return true;
	}

	public void exitbutton1(View v) {
		this.finish();
	}

	public void sendMessage() {

		String msgType = Constants.MSGTYPE_CMD;
		String cmdType = Constants.CMDTYPE_SWT;
		final String phoneMAC = PhoneInfoUtil.getLocalMacAddress(this);
		
		String command = itemStatus.equals("0") ? "1" : "0";


		String message = msgType + "," + cmdType + "," + phoneMAC + "," + deviceAddress + ","
				+ command;

		if (SocketUtil.sendMessage(message)) {
			Toast.makeText(this, "指令发送成功！", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, "指令发送失败！", Toast.LENGTH_SHORT).show();
		}
		// MainWeixin.instance.finish();//
	}

	@Override
	protected void onDestroy() {
		// 注销广播
		unregisterReceiver(msgReceiver);
		super.onDestroy();
	}

	/**
	 * 广播接收器
	 * 
	 */
	public class MsgReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// 获取设备状态，更新UI
			String deviceStatus = intent.getStringExtra("deviceStatus");
			String cmdStatus = intent.getStringExtra("cmdStatus");

			if(deviceStatus.equals("0")){
				image.setImageResource(R.drawable.device_default_light_close);
				itemStatus = "0";
			} else {
				image.setImageResource(R.drawable.device_default_light_open);
				itemStatus = "1";
			}
			if("0".equals(cmdStatus)){
				Toast.makeText(context, "设备指令执行成功！", Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(context, "设备指令执行失败！", Toast.LENGTH_LONG).show();
			}
		}

	}

}
