package cn.smarthome.sap.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import cn.smarthome.sap.R;
import cn.smarthome.sap.dao.DeviceDetailInfoDao;
import cn.smarthome.sap.db.SqliteHelper;
import cn.smarthome.sap.model.DeviceDetailInfo;

public class AreaItemView extends ListView {

	private static final String TAG = "AreaItemView";
	private ArrayList<HashMap<String, Object>> listItems; // 存放文字、图片信息
	private SimpleAdapter listItemAdapter; // 适配器
	private Context context;
	private SqliteHelper sh;

	public AreaItemView(Context context) {
		super(context);
		this.context = context;
		initListView();
	}

	public AreaItemView(Context context, int areaID) {
		super(context);
		this.context = context;
		setId(areaID);
		sh = new SqliteHelper(context);
		Log.e(TAG, "创建AreaItemView ： " + areaID);
		DeviceDetailInfoDao deviceDetailDao = new DeviceDetailInfoDao(sh);
		String selection = "where areaid = ?";
		String[] selectionArgs = new String[]{"" + areaID};
		String orderBy = "order by deviceIndex";
		List<DeviceDetailInfo> deviceDetailList = deviceDetailDao.getList(selection, selectionArgs, orderBy);

		Log.e(TAG,"获取对应areaid的设备详情数量：" + deviceDetailList.size());
		listItems = new ArrayList<HashMap<String, Object>>();
		for (DeviceDetailInfo deviceDetail : deviceDetailList) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ItemTitle", deviceDetail.getDeviceName()); // 文字
			map.put("ItemImage", R.drawable.device_light_normal_off);// 图片
			listItems.add(map);
		}
		// 生成适配器的Item和动态数组对应的元素
		listItemAdapter = new SimpleAdapter(context, listItems,// 数据源
				R.layout.area_item_layout,// ListItem的XML布局实现
				// 动态数组与ImageItem对应的子项
				new String[] { "ItemImage", "ItemTitle" },
				// ImageItem的XML文件里面的一个ImageView,两个TextView ID
				new int[] { R.id.areaItemImageView, R.id.areaItemName });
		
		this.setAdapter(listItemAdapter);
		
		
	}

	private void initListView() {
		listItems = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < 6; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ItemTitle", "Music： " + i); // 文字
			map.put("ItemImage", R.drawable.ic_launcher);// 图片
			listItems.add(map);
		}
		// 生成适配器的Item和动态数组对应的元素
		listItemAdapter = new SimpleAdapter(context, listItems,// 数据源
				R.layout.area_item_layout,// ListItem的XML布局实现
				// 动态数组与ImageItem对应的子项
				new String[] { "ItemImage", "ItemTitle" },
				// ImageItem的XML文件里面的一个ImageView,两个TextView ID
				new int[] { R.id.areaItemImageView, R.id.areaItemName });
		
		this.setAdapter(listItemAdapter);
	}
}
