package cn.smarthome.sap.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;
import cn.smarthome.sap.R;
import cn.smarthome.sap.adapter.MainImageAdapter;
import cn.smarthome.sap.dao.UserInfoDao;
import cn.smarthome.sap.db.SqliteHelper;

public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";

	private SqliteHelper sh;
	private UserInfoDao uiDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
	    GridView gridview = (GridView) findViewById(R.id.gridview);
	    gridview.setNumColumns(2);
	    gridview.setAdapter(new MainImageAdapter(this));

	    gridview.setOnItemClickListener(new OnItemClickListener() {
	       

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
			}
	    });

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)  
    {  
        //得到当前选中的MenuItem的ID,  
        int item_id = item.getItemId();  
  
        switch (item_id)  
        {  
            case R.id.logout:  
            	
            	doLogout();
                /* 新建一个Intent对象 */  
                Intent intent = new Intent();  
                /* 指定intent要启动的类 */  
                intent.setClass(MainActivity.this, LoginActivity.class);  
                /* 启动一个新的Activity */  
                startActivity(intent);  
                /* 关闭当前的Activity */  
                MainActivity.this.finish();  
                break;  
            case R.id.action_settings:  
//                Activity01.this.finish();  
                break;  
        }  
        return true;  
    }

	private void doLogout() {
		Log.i(TAG, "LOGOUT");

		sh = new SqliteHelper(this);
		uiDao = new UserInfoDao(sh);
		uiDao.deleteAllUsers();
		
	}  
}
