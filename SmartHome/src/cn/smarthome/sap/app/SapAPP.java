package cn.smarthome.sap.app;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.view.View;

public class SapAPP extends Application {
	// 共享变量
	private ViewRefreshHandler handler = null;

	private List<View> allViews = new ArrayList<View>();

	// set方法
	public void setHandler(ViewRefreshHandler handler) {
		this.handler = handler;
	}

	// get方法
	public ViewRefreshHandler getHandler() {
		return handler;
	}

	public void addView(View view) {

		if (!allViews.contains(view)) {
			allViews.add(view);
		}

	}

	public List<View> getAllViews() {
		return this.allViews;
	}

	public void refreshAllViews() {
		for (View view : allViews) {
			view.invalidate();
		}
	}

	private static SapAPP instance;

	public static SapAPP getInstance() {
		return instance;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		instance = this;
	}
}
