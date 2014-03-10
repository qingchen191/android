package cn.smarthome.sap.activity;

import cn.smarthome.sap.R;
import cn.smarthome.sap.TestActivity;
import cn.smarthome.sap.R.layout;
import cn.smarthome.sap.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;

public class Main2Activity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main2);

		// 点击场景时
		findViewById(R.id.imageViewScene).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						Intent intent = new Intent();
						intent.setClass(Main2Activity.this, TestActivity.class);
						startActivity(intent);
					}
				});

		// 点击区域时
		findViewById(R.id.imageViewArea).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						Intent intent = new Intent();
						intent.setClass(Main2Activity.this, TestActivity.class);
						startActivity(intent);
					}
				});

		// 点击设备时
		findViewById(R.id.imageViewDevice).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						Intent intent = new Intent();
						intent.setClass(Main2Activity.this, CategoryActivity.class);
						startActivity(intent);
					}
				});

		// 点击监控时
		findViewById(R.id.imageViewMonitor).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						Intent intent = new Intent();
						intent.setClass(Main2Activity.this, TestActivity.class);
						startActivity(intent);
					}
				});

		// 点击系统时
		findViewById(R.id.imageViewSystem).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						Intent intent = new Intent();
						intent.setClass(Main2Activity.this, TestActivity.class);
						startActivity(intent);
					}
				});

		// 点击更多时
		findViewById(R.id.imageViewMore).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						Intent intent = new Intent();
						intent.setClass(Main2Activity.this, TestActivity.class);
						startActivity(intent);
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main2, menu);
		return true;
	}

}
