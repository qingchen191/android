package cn.smarthome.sap.util;

import java.io.DataOutputStream;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

public class PhoneInfoUtil {

	public static synchronized boolean getRootAhth() {
		Process process = null;
		DataOutputStream os = null;
		try {
			process = Runtime.getRuntime().exec("su");
			os = new DataOutputStream(process.getOutputStream());
			os.writeBytes("exit\n");
			os.flush();
			int exitValue = process.waitFor();
			if (exitValue == 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			Log.d("*** DEBUG ***", "Unexpected error - Here is what I know: "
					+ e.getMessage());
			return false;
		} finally {
			try {
				if (os != null) {
					os.close();
				}
				process.destroy();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static String getNetworkState(Context context) {

		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		State mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
				.getState();
		State wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.getState();
		// 如果3G、wifi、2G等网络状态是连接的，则退出，否则显示提示信息进入网络设置界面
		if (mobile == State.CONNECTED || mobile == State.CONNECTING) {
			return "移动网络";
		} else if (wifi == State.CONNECTED || wifi == State.CONNECTING) {
			return "WIFI";
		} else {
			return "没有网络连接";
		}
	}

	public static String getLocation(Context context) {
		// 定位信息

		LocationManager locationManager;

		Location location;
		/* 位置获取经纬度 */

		locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);

		location = locationManager
				.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

		int i = 0;

		while (location == null) {

			Log.e("log", location + "");

			location = locationManager
					.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

			if (i++ > 100)
				break;
			return "无法获取经纬度";
		}

		double latitude = location.getLatitude();

		double longitude = location.getLongitude();

		// txt.setText("纬度："+latitude +"n"+"经度："+longitude);

		return latitude + "," + longitude;
	}

	public static String getLocalMacAddress(Context context) {

		WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

		WifiInfo info = wifi.getConnectionInfo();
		
		return "qq-ww-ee-rr";  //测试MAC地址

//		return info.getMacAddress();

	}
}
