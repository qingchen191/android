package cn.smarthome.sap.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;
import cn.smarthome.sap.R;
import cn.smarthome.sap.TestActivity;
import cn.smarthome.sap.activity.AreaActivity;
import cn.smarthome.sap.activity.CategoryActivity;
import cn.smarthome.sap.activity.ItemActivity;
import cn.smarthome.sap.activity.SceneActivity;
import cn.smarthome.sap.socket.SocketUtil;

public class MainImageAdapter extends BaseAdapter {
	private Context mContext;
	private static final String TAG = "ImageAdapter";

	public MainImageAdapter(Context c) {
		mContext = c;
	}

	@Override
	public int getCount() {
		return mThumbIds.length;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	// create a new ImageView for each item referenced by the Adapter
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		if (convertView == null) { // if it's not recycled, initialize some
									// attributes
			imageView = new ImageView(mContext);
			// imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
			imageView.setScaleType(ImageView.ScaleType.FIT_START);
			imageView.setPadding(8, 8, 8, 8);
			imageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent();
					switch (position) {
					case 0:
						intent.setClass(mContext, TestActivity.class);
						break;
					case 1:
						intent.setClass(mContext, AreaActivity.class);
						break;
					case 2:
//						sendMessage();

						intent.setClass(mContext, CategoryActivity.class);
						break;
					case 3:
						intent.setClass(mContext, ItemActivity.class);
						break;
					case 4:
						intent.setClass(mContext, ItemActivity.class);
						break;
					case 5:
						intent.setClass(mContext, ItemActivity.class);
						break;
					}

					mContext.startActivity(intent);
				}

			});
		} else {
			imageView = (ImageView) convertView;
		}

		imageView.setImageResource(mThumbIds[position]);
		return imageView;
	}

	protected void sendMessage() {
		String content = "send message by click image.";

		if (SocketUtil.sendMessage(content)) {
			Toast.makeText(mContext, "发送消息成功！", Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(mContext, "发送消息失败！", Toast.LENGTH_LONG).show();
		}
	}

	// references to our images
	private Integer[] mThumbIds = { R.drawable.main_home_scene,
			R.drawable.main_home_area, R.drawable.main_home_device,
			R.drawable.main_home_monitor, R.drawable.main_home_system,
			R.drawable.main_home_more };
}
