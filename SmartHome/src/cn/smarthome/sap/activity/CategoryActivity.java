package cn.smarthome.sap.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;
import cn.smarthome.sap.R;
import cn.smarthome.sap.dao.DeviceTypeInfoDao;
import cn.smarthome.sap.db.SqliteHelper;
import cn.smarthome.sap.model.DeviceTypeInfo;
import cn.smarthome.sap.view.CategoryItemView;

public class CategoryActivity extends Activity {

	private ViewPager viewPager;// 页卡内容
	private List<View> views;// Tab页面列表
	private SqliteHelper sh;
	private MyViewPagerAdapter adapter;
	private DeviceTypeInfoDao deviceTypeDao;
	ViewGroup group;
	ImageView imageView;
	ImageView[] imageViews;
	private static int c_id = 0;
	private int tabCount = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_category);
		InitViewPager();

		group = (ViewGroup) findViewById(R.id.viewGroup);
		
		imageViews = new ImageView[tabCount];
		for (int i = 0; i < tabCount; i++) {
			imageView = new ImageView(CategoryActivity.this);
			imageView.setLayoutParams(new LayoutParams(20, 20));
			imageView.setPadding(10, 0, 10, 0);
			imageViews[i] = imageView;
			if (i == 0) {
				imageViews[i].setBackgroundResource(R.drawable.guide_dot_white);
			} else {
				imageViews[i].setBackgroundResource(R.drawable.guide_dot_black);
			}
			group.addView(imageView);
		}
	}

	private void InitViewPager() {
		viewPager = (ViewPager) findViewById(R.id.categoryPager);
		views = new ArrayList<View>();

		sh = new SqliteHelper(this);
		deviceTypeDao = new DeviceTypeInfoDao(sh);

		refreshViews();

		adapter = new MyViewPagerAdapter(views);

		viewPager.setAdapter(adapter);
		viewPager.setCurrentItem(0);
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	private void refreshViews() {
		views.clear();

		List<DeviceTypeInfo> deviceTypeList = deviceTypeDao.getList();
		tabCount = deviceTypeList.size();
		for (int i = 0; i < deviceTypeList.size(); i++) {
			DeviceTypeInfo deviceType = deviceTypeList.get(i);
			CategoryItemView mView = new CategoryItemView(
					CategoryActivity.this, deviceType.getTypeID());

			views.add(mView);
		}

	}

	public class MyViewPagerAdapter extends PagerAdapter {
		private List<View> mListViews;

		public MyViewPagerAdapter(List<View> mListViews) {
			this.mListViews = mListViews;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(mListViews.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(mListViews.get(position), 0);
			return mListViews.get(position);
		}

		@Override
		public int getCount() {
			return mListViews.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}
	}

	class MyOnPageChangeListener implements OnPageChangeListener {

		// 当滑动状态改变时调用
		@Override
		public void onPageScrollStateChanged(int arg0) {
			// arg0=arg0%list.size();

		}

		// 当当前页面被滑动时调用
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		// 当新的页面被选中时调用
		@Override
		public void onPageSelected(int arg0) {
			if (arg0 > 2) {
				arg0 = arg0 % tabCount;
			}
			c_id = arg0;
			for (int i = 0; i < imageViews.length; i++) {
				imageViews[arg0]
						.setBackgroundResource(R.drawable.guide_dot_white);
				if (arg0 != i) {
					imageViews[i]
							.setBackgroundResource(R.drawable.guide_dot_black);
				}
			}

			Log.e("-------------", "当前是第" + c_id + "页");
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.category, menu);
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();// 在下一行加入代码
		int index = viewPager.getCurrentItem();
		refreshViews();
		adapter = new MyViewPagerAdapter(views);
		// adapter.notifyDataSetChanged();

		viewPager.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		viewPager.setCurrentItem(index);
		Log.i("TTTTTTTTTTTTTT", "onResume +　adapter.notifyDataSetChanged()");
	}

}
