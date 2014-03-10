package cn.smarthome.sap.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TabHost.TabContentFactory;
import cn.smarthome.sap.R;
import cn.smarthome.sap.dao.AreaInfoDao;
import cn.smarthome.sap.db.SqliteHelper;
import cn.smarthome.sap.model.AreaInfo;
import cn.smarthome.sap.view.AreaItemView;

public class SceneActivity extends Activity {


	private ViewPager viewPager;//页卡内容
	private ImageView imageView;// 动画图片
	private TextView textView1,textView2,textView3;
	private List<View> views;// Tab页面列表
	private int offset = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int bmpW;// 动画图片宽度
	private View view1,view2,view3;//各个页卡
	private SqliteHelper sh;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_scene);
		InitImageView();
		InitTextView();
		InitViewPager();
	}

	private void InitViewPager() {
		viewPager=(ViewPager) findViewById(R.id.vPager);
		views=new ArrayList<View>();
//		LayoutInflater inflater=getLayoutInflater();
//		view1=inflater.inflate(R.layout.lay1, null);
//		view2=inflater.inflate(R.layout.lay2, null);
//		view3=inflater.inflate(R.layout.lay3, null);
//		views.add(view1);
//		views.add(view2);
//		views.add(view3);
		

		sh = new SqliteHelper(this);
		AreaInfoDao areaDao = new AreaInfoDao(sh);
		List<AreaInfo> areaList = areaDao.getList();
		LinearLayout footerLayout = (LinearLayout)findViewById(R.id.linearLayoutFooter);
		for(int i = 0; i < areaList.size(); i++){
			AreaInfo area = areaList.get(i);
        	AreaItemView mView=new AreaItemView(SceneActivity.this, area.getAreaID());
        	views.add(mView);
        	
        	TextView tv = new TextView(this);
        	tv.setText(area.getAreaName());
        	tv.setWidth(200);
        	tv.setSelected(true);
        	tv.setTextSize(20.0f);
        	
        	tv.setOnClickListener(new MyOnClickListener(i));
        	footerLayout.addView(tv);
		}
		
		
		viewPager.setAdapter(new MyViewPagerAdapter(views));
		viewPager.setCurrentItem(0);
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}
	 /**
	  *  初始化头标
	  */

	private void InitTextView() {
//		textView1 = (TextView) findViewById(R.id.text1);
//		textView2 = (TextView) findViewById(R.id.text2);
//		textView3 = (TextView) findViewById(R.id.text3);

//		textView1.setOnClickListener(new MyOnClickListener(0));
//		textView2.setOnClickListener(new MyOnClickListener(1));
//		textView3.setOnClickListener(new MyOnClickListener(2));
	}

	/**
	 2      * 初始化动画，这个就是页卡滑动时，下面的横线也滑动的效果，在这里需要计算一些数据
	 3 */

	private void InitImageView() {
		imageView= (ImageView) findViewById(R.id.cursor);
		bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.tabfooter).getWidth();// 获取图片宽度
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;// 获取分辨率宽度
		offset = (screenW / 3 - bmpW) / 2;// 计算偏移量
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		imageView.setImageMatrix(matrix);// 设置动画初始位置
	}
	/** 
	 *     
	 * 头标点击监听 3 */
	private class MyOnClickListener implements OnClickListener{
        private int index=0;
        public MyOnClickListener(int i){
        	index=i;
        }
		public void onClick(View v) {
			viewPager.setCurrentItem(index);			
		}
		
	}
	
	public class MyViewPagerAdapter extends PagerAdapter{
		private List<View> mListViews;
		
		public MyViewPagerAdapter(List<View> mListViews) {
			this.mListViews = mListViews;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) 	{	
			container.removeView(mListViews.get(position));
		}


		@Override
		public Object instantiateItem(ViewGroup container, int position) {			
			 container.addView(mListViews.get(position), 0);
			 return mListViews.get(position);
		}

		@Override
		public int getCount() {			
			return  mListViews.size();
		}
		
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {			
			return arg0==arg1;
		}
	}

    public class MyOnPageChangeListener implements OnPageChangeListener{

    	int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
		int two = one * 2;// 页卡1 -> 页卡3 偏移量
		public void onPageScrollStateChanged(int arg0) {
			
			
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
			
			
		}

		public void onPageSelected(int arg0) {
			/*两种方法，这个是一种，下面还有一种，显然这个比较麻烦
			Animation animation = null;
			switch (arg0) {
			case 0:
				if (currIndex == 1) {
					animation = new TranslateAnimation(one, 0, 0, 0);
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, 0, 0, 0);
				}
				break;
			case 1:
				if (currIndex == 0) {
					animation = new TranslateAnimation(offset, one, 0, 0);
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, one, 0, 0);
				}
				break;
			case 2:
				if (currIndex == 0) {
					animation = new TranslateAnimation(offset, two, 0, 0);
				} else if (currIndex == 1) {
					animation = new TranslateAnimation(one, two, 0, 0);
				}
				break;
				
			}
			*/
			Animation animation = new TranslateAnimation(one*currIndex, one*arg0, 0, 0);//显然这个比较简洁，只有一行代码。
			currIndex = arg0;
			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(300);
			imageView.startAnimation(animation);
			Toast.makeText(SceneActivity.this, "您选择了"+ viewPager.getCurrentItem()+"页卡", Toast.LENGTH_SHORT).show();
		}
    	
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.scene, menu);
		return true;
	}

}