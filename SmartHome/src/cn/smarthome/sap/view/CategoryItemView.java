package cn.smarthome.sap.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import cn.smarthome.sap.R;
import cn.smarthome.sap.activity.ItemActivity;
import cn.smarthome.sap.activity.SceneActivity;
import cn.smarthome.sap.dao.DeviceDetailInfoDao;
import cn.smarthome.sap.db.SqliteHelper;
import cn.smarthome.sap.model.DeviceDetailInfo;
import cn.smarthome.sap.socket.SocketUtil;
import cn.smarthome.sap.util.Constants;

public class CategoryItemView extends ListView {

	private static final String TAG = "CategoryItemView";
	private ArrayList<HashMap<String, Object>> listItems; // 存放文字、图片信息
	private SimpleAdapter listItemAdapter; // 适配器
	private Context context;
	private SqliteHelper sh;

	public CategoryItemView(Context context) {
		super(context);
		this.context = context;
	}

	public CategoryItemView(final Context context, int categoryID) {
		super(context);
		this.context = context;
		sh = new SqliteHelper(context);
		Log.e(TAG, "创建CategoryItemView ： " + categoryID);
		DeviceDetailInfoDao deviceDetailDao = new DeviceDetailInfoDao(sh);
		String selection = "where typeid = ?";
		String[] selectionArgs = new String[]{"" + categoryID};
		String orderBy = "order by deviceIndex";
		List<DeviceDetailInfo> deviceDetailList = deviceDetailDao.getList(selection, selectionArgs, orderBy);

		Log.e(TAG,"获取对应typeid的设备详情数量：" + deviceDetailList.size());
		listItems = new ArrayList<HashMap<String, Object>>();
		for (DeviceDetailInfo deviceDetail : deviceDetailList) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ItemTitle", deviceDetail.getDeviceName()); // 文字
			map.put("ItemIndex", deviceDetail.getDeviceIndex());
			int status = deviceDetail.getStatus();
			map.put("ItemImage", status == 1 ? R.drawable.device_light_normal_on : R.drawable.device_light_normal_off);// 图片
			map.put("ItemStatus", status == 1 ? "开" : "关");
			listItems.add(map);
		}
		// 生成适配器的Item和动态数组对应的元素
		listItemAdapter = new SimpleAdapter(context, listItems,// 数据源
				R.layout.area_item_layout,// ListItem的XML布局实现
				// 动态数组与ImageItem对应的子项
				new String[] { "ItemImage", "ItemTitle", "ItemIndex", "ItemStatus" },
				// ImageItem的XML文件里面的一个ImageView,三个TextView ID
				new int[] { R.id.areaItemImageView, R.id.areaItemName, R.id.areaItemIndex, R.id.areaItemStatus });
		
		this.setAdapter(listItemAdapter);
		
		setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				//获得选中项的HashMap对象   
                HashMap<String,Object> map=(HashMap<String,Object>)getItemAtPosition(position); 
                String itemIndex = map.get("ItemIndex").toString();
                String itemName = map.get("ItemTitle").toString();
                String itemStatus = map.get("ItemStatus").toString();
                Toast.makeText(view.getContext(), "您选择了"+ itemIndex, Toast.LENGTH_SHORT).show();
                Log.i(TAG, "您选择了"+ itemIndex);
                
                Intent itemIntent = new Intent();
                itemIntent.putExtra("deviceAddress", itemIndex);
                itemIntent.putExtra("deviceName", itemName);
                itemIntent.putExtra("itemStatus", itemStatus.equals("开") ? "1" : "0");
                itemIntent.setClass(context, ItemActivity.class);
                context.startActivity(itemIntent);
			}
			
		});
		
	}

}
