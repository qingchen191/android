package cn.smarthome.sap.activity;

import java.util.Date;
import java.util.List;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import cn.smarthome.sap.R;
import cn.smarthome.sap.dao.AreaInfoDao;
import cn.smarthome.sap.dao.DeviceDetailInfoDao;
import cn.smarthome.sap.dao.DeviceKindInfoDao;
import cn.smarthome.sap.dao.DeviceTypeInfoDao;
import cn.smarthome.sap.dao.LogInfoDao;
import cn.smarthome.sap.dao.UserInfoDao;
import cn.smarthome.sap.db.SqliteHelper;
import cn.smarthome.sap.model.AreaInfo;
import cn.smarthome.sap.model.DeviceDetailInfo;
import cn.smarthome.sap.model.DeviceKindInfo;
import cn.smarthome.sap.model.DeviceTypeInfo;
import cn.smarthome.sap.model.UserInfo;
import cn.smarthome.sap.service.SocketService;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends Activity {

	private static final String[] DUMMY_CREDENTIALS = new String[] {
			"foo@example.com:hello", "bar@example.com:world" };

	/**
	 * The default username to populate the username field with.
	 */
	public static final String EXTRA_USERNAME = "请输入用户名";

	public static final int RESULT_CODE = 1;

	private static final String TAG = "LoginActivity";

	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private UserLoginTask mAuthTask = null;

	// Values for username and password at the time of the login attempt.
	private String userName;
	private String password;

	// UI references.
	private EditText userNameView;
	private EditText passwordView;
	private View loginFormView;
	private View loginStatusView;
	private TextView loginStatusMessageView;
	private CheckBox keepPasswordCheckBox;
	private CheckBox autoLoginCheckBox;
	
	private SqliteHelper sh;
	private UserInfoDao uiDao;
	private UserInfo ui;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);

		loginFormView = findViewById(R.id.login_form);
		loginStatusView = findViewById(R.id.login_status);
		loginStatusMessageView = (TextView) findViewById(R.id.login_status_message);
		
		userNameView = (EditText) findViewById(R.id.userName);
		passwordView = (EditText) findViewById(R.id.password);
		keepPasswordCheckBox = (CheckBox) findViewById(R.id.keepPassword);
		autoLoginCheckBox = (CheckBox) findViewById(R.id.autoLogin);
		
		sh = new SqliteHelper(this);
		uiDao = new UserInfoDao(sh);
		ui = uiDao.getLastUser();
		if(ui != null){
			userName = ui.getLoginUserID();
			userNameView.setText(userName);
			
			if(ui.getKeepPassword() == 1){
				password = ui.getPassword();
				passwordView.setText(password);
				
				keepPasswordCheckBox.setChecked(true);
				if(ui.getAutoLogin() == 1){
					autoLoginCheckBox.setChecked(true);
					attemptLogin();
				}
			}
		} else { //第一次打开应用时，插入测试数据

			//插入数据库测试数据
			AreaInfoDao areaDao = new AreaInfoDao(sh);
			DeviceDetailInfoDao deviceDetailDao = new DeviceDetailInfoDao(sh);
			DeviceKindInfoDao deviceKindDao = new DeviceKindInfoDao(sh);
			DeviceTypeInfoDao deviceTypeDao = new DeviceTypeInfoDao(sh);
			
//			for(int i = 0; i<3;i++){
//				AreaInfo area = new AreaInfo();
//				area.setAreaID(i);
//				area.setAreaImageID(R.drawable.room_gallery_0);
//				area.setAreaName("区域" + i);
//				areaDao.saveOrUpdate(area);
//				for(int j = 0; j<3;j++){
//					DeviceDetailInfo deviceDetail = new DeviceDetailInfo();
//					deviceDetail.setAreaID(i);
//					deviceDetail.setDeviceIndex("deviceIndex" + j);
//					deviceDetail.setDeviceName("设备名"+ i + j);
//					deviceDetail.setStatus(j);
//					deviceDetail.setKindID(1);
//					deviceDetail.setTypeID(1);
//					deviceDetailDao.saveOrUpdate(deviceDetail);
//				}
//			}
			AreaInfo area = new AreaInfo();
			area.setAreaID(1);
			area.setAreaImageID(R.drawable.room_gallery_0);
			area.setAreaName("客厅");
			areaDao.saveOrUpdate(area);
			
			area = new AreaInfo();
			area.setAreaID(2);
			area.setAreaImageID(R.drawable.room_gallery_0);
			area.setAreaName("卧室");
			areaDao.saveOrUpdate(area);

			area = new AreaInfo();
			area.setAreaID(3);
			area.setAreaImageID(R.drawable.room_gallery_0);
			area.setAreaName("厨房");
			areaDao.saveOrUpdate(area);
			
			DeviceKindInfo deviceKind = new DeviceKindInfo();
			deviceKind.setKindID(1);
			deviceKind.setKindName("照明");
			deviceKindDao.saveOrUpdate(deviceKind);
			
			deviceKind = new DeviceKindInfo();
			deviceKind.setKindID(2);
			deviceKind.setKindName("电器");
			deviceKindDao.saveOrUpdate(deviceKind);
			
			DeviceTypeInfo deviceType = new DeviceTypeInfo();
			deviceType.setKindID(1);
			deviceType.setTypeID(1);
			deviceType.setDeviceImageID(R.drawable.device_light_normal_off);
			deviceType.setTypeName("白炽灯");
			deviceTypeDao.saveOrUpdate(deviceType);

			deviceType = new DeviceTypeInfo();
			deviceType.setKindID(1);
			deviceType.setTypeID(2);
			deviceType.setDeviceImageID(R.drawable.device_light_normal_off);
			deviceType.setTypeName("节能灯");
			deviceTypeDao.saveOrUpdate(deviceType);
			

			DeviceDetailInfo deviceDetail = new DeviceDetailInfo();
			deviceDetail.setAreaID(1);
			deviceDetail.setDeviceIndex("13");
			deviceDetail.setDeviceName("客厅的白炽灯");
			deviceDetail.setStatus(0);
			deviceDetail.setKindID(1);
			deviceDetail.setTypeID(1);
			deviceDetailDao.saveOrUpdate(deviceDetail);
			
			deviceDetail = new DeviceDetailInfo();
			deviceDetail.setAreaID(1);
			deviceDetail.setDeviceIndex("deviceIndex12");
			deviceDetail.setDeviceName("客厅的节能灯");
			deviceDetail.setStatus(1);
			deviceDetail.setKindID(1);
			deviceDetail.setTypeID(2);
			deviceDetailDao.saveOrUpdate(deviceDetail);
			
			deviceDetail = new DeviceDetailInfo();
			deviceDetail.setAreaID(2);
			deviceDetail.setDeviceIndex("deviceIndex21");
			deviceDetail.setDeviceName("卧室的白炽灯");
			deviceDetail.setStatus(1);
			deviceDetail.setKindID(1);
			deviceDetail.setTypeID(1);
			deviceDetailDao.saveOrUpdate(deviceDetail);
			
			deviceDetail = new DeviceDetailInfo();
			deviceDetail.setAreaID(2);
			deviceDetail.setDeviceIndex("deviceIndex22");
			deviceDetail.setDeviceName("卧室的节能灯");
			deviceDetail.setStatus(0);
			deviceDetail.setKindID(1);
			deviceDetail.setTypeID(2);
			deviceDetailDao.saveOrUpdate(deviceDetail);

			deviceDetail = new DeviceDetailInfo();
			deviceDetail.setAreaID(3);
			deviceDetail.setDeviceIndex("deviceIndex22");
			deviceDetail.setDeviceName("厨房的节能灯");
			deviceDetail.setStatus(0);
			deviceDetail.setKindID(1);
			deviceDetail.setTypeID(2);
			deviceDetailDao.saveOrUpdate(deviceDetail);
		}

		// Set up the login form.
//		userName = getIntent().getStringExtra(EXTRA_USERNAME);
		userNameView.setText(userName);

		passwordView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {
							attemptLogin();
							return true;
						}
						return false;
					}
				});


		findViewById(R.id.sign_in_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						attemptLogin();
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig){
		super.onConfigurationChanged(newConfig);
	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid username, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptLogin() {
		if (mAuthTask != null) {
			return;
		}

		// Reset errors.
		userNameView.setError(null);
		passwordView.setError(null);

		// Store values at the time of the login attempt.
		userName = userNameView.getText().toString();
		password = passwordView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(password)) {
			passwordView.setError(getString(R.string.error_field_required));
			focusView = passwordView;
			cancel = true;
		} else if (password.length() < 4) {
			passwordView.setError(getString(R.string.error_invalid_password));
			focusView = passwordView;
			cancel = true;
		}

		// Check for a valid username.
		if (TextUtils.isEmpty(userName)) {
			userNameView.setError(getString(R.string.error_field_required));
			focusView = userNameView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			loginStatusMessageView.setText(R.string.login_progress_signing_in);
			showProgress(true);
			mAuthTask = new UserLoginTask();
			mAuthTask.execute((Void) null);
		}
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			loginStatusView.setVisibility(View.VISIBLE);
			loginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							loginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			loginFormView.setVisibility(View.VISIBLE);
			loginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							loginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			loginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.

			try {
				// Simulate network access.
				// NameValuePair userName = new BasicNameValuePair("username",
				// "username");
				// NameValuePair password = new BasicNameValuePair("password",
				// "password");
				// String response = CustomerHttpClient.post("", userName,
				// password);
				// JSONObject jsonResult = new JSONObject(response);
				// int resultId =
				// Integer.parseInt(jsonResult.getString("userid"));
				// Log.i(TAG, "用户登录结果：" + jsonResult);

				Thread.sleep(2000);
			} catch (InterruptedException e) {
				return false;
				// } catch (JSONException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// return false;
			}
			
			LogInfoDao logDao = new LogInfoDao(sh);
			logDao.insertLog("operate", "登录成功！");

			for (String credential : DUMMY_CREDENTIALS) {
				String[] pieces = credential.split(":");
				if (pieces[0].equals(userName)) {
					// Account exists, return true if the password matches.
					return pieces[1].equals(password);
				}
			}
			
			return true;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;
			showProgress(false);

			if (success) {
				Log.i(TAG, "登录成功");
				//把用户信息保存到本地数据库
				saveUserInfo();
				
				//启动socket service
				if(!isServiceRunning(SocketService.class.getName())){
					Intent socketIntent=new Intent(LoginActivity.this, SocketService.class);
					startService(socketIntent);
				}

				//跳转到主页面
				Intent intent = new Intent();
				intent.setClass(LoginActivity.this, Main2Activity.class);
				// intent.putExtra("back", "come from second activiy");//传递参数
				startActivity(intent);
				// setResult(RESULT_CODE, intent);

				sh.release();
				finish();
				
			} else {
				Log.i(TAG, "登录失败");
				passwordView
						.setError(getString(R.string.error_incorrect_password));
				passwordView.requestFocus();
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}
	}

	private void saveUserInfo() {
		// 
		Log.i(TAG, "" + userName + "|" + password +"|" +keepPasswordCheckBox.isChecked() + "|" + autoLoginCheckBox.isChecked());
		if(ui == null){
			ui = new UserInfo();
		}
		ui.setId(null);
		ui.setUserID(1);
		ui.setLoginUserID(userName);
		ui.setPassword(password);
		ui.setKeepPassword(keepPasswordCheckBox.isChecked()?1:0);
		ui.setAutoLogin(autoLoginCheckBox.isChecked()?1:0);
		ui.setUpdateTime(new Date());
		uiDao.deleteAllUsers();
		uiDao.saveOrUpdate(ui);
	}
	
	private boolean isServiceRunning(String className) {

        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager)
        this.getSystemService(Context.ACTIVITY_SERVICE); 
        List<ActivityManager.RunningServiceInfo> serviceList 
                   = activityManager.getRunningServices(30);

        if (!(serviceList.size()>0)) {
            return false;
        }

        for (int i=0; i<serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(className) == true) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }
	
}
