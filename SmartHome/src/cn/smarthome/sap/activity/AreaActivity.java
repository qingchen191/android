package cn.smarthome.sap.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.smarthome.sap.R;
import cn.smarthome.sap.R.layout;
import cn.smarthome.sap.R.menu;
import cn.smarthome.sap.dao.AreaInfoDao;
import cn.smarthome.sap.db.SqliteHelper;
import cn.smarthome.sap.model.AreaInfo;
import cn.smarthome.sap.view.AreaItemView;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TabHost.TabContentFactory;;

public class AreaActivity extends Activity {

	private static final String TAG = "AreaActivity";
	private SqliteHelper sh;
	private Map<String, View> views;// Tab页面列表
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_area);
		
		
		TabHost tabs = (TabHost)findViewById(R.id.tabhostArea);
		tabs.setup();

		sh = new SqliteHelper(this);
		views = new HashMap<String, View>();
		TabSpec spec;
		AreaInfoDao areaDao = new AreaInfoDao(sh);
		List<AreaInfo> areaList = areaDao.getList();
		for(final AreaInfo area : areaList){
			spec = tabs.newTabSpec("tag"+area.getAreaID());
			spec.setIndicator (area.getAreaName());
//			AreaItemView mView=new AreaItemView(AreaActivity.this, area.getAreaID());
//			views.put("tag"+area.getAreaID(), mView);
//			spec.setContent(R.layout.area_item_layout);
			
			spec.setContent(new TabContentFactory () {
	            
	            public View createTabContent(String tag) {
	            	AreaItemView mView=new AreaItemView(AreaActivity.this, area.getAreaID());
//	            	return views.get(tag);
	            	return mView;
	            }
	        });
			
			tabs.addTab(spec);
		}
		tabs.setCurrentTab(1);
		tabs.setOnTabChangedListener(new OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String tabId) {
				// TODO Auto-generated method stub
				
			}
		});
		
//		TabSpec spec = tabs.newTabSpec("tab1");
//		
//        spec.setContent(new TabContentFactory () {
//            
//            public View createTabContent(String tag) {
//            	AreaItemView mView=new AreaItemView(AreaActivity.this);
//            	LinearLayout myLinear=new LinearLayout(AreaActivity.this);
//            	//LinearLayout.LayoutParams.WRAP_CONTENT可以设定为你需要的值
//            	LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
//            					LinearLayout.LayoutParams.FILL_PARENT,
//            					LinearLayout.LayoutParams.FILL_PARENT
//            			);
//            	myLinear.addView(mView,params1);
//                return myLinear;
//            }
//        }); // 此处是写死的，应该动态生成。
//        spec.setIndicator ("客厅");
//        
//        tabs.addTab(spec);
//        
//        spec = tabs.newTabSpec("tab2");
//        spec.setContent(R.id.tab2);
//        spec.setIndicator("卧室");
//        tabs.addTab(spec);
//        
//        tabs.setCurrentTab(1);
        
        
        //动态添加tab
//        Button button = (Button)findViewById(R.id.c92_tabhost);
//        button.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View arg0) {
//                TabHost.TabSpec spec = tabs.newTabSpec ("tag1");
//                spec.setContent(new TabHost.TabContentFactory () {
//                   
//                    public View createTabContent (String tag) {
//                        return new AnalogClock(Chapter9Test3.this);
//                    }
//                });
//                spec.setIndicator("Clock");
//                tabs.addTab(spec);
//            }
//        });
        
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.area, menu);
		return true;
	}

}
