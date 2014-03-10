package cn.smarthome.sap.db;

import java.util.LinkedList;

import java.util.List;

import cn.smarthome.sap.R;
import cn.smarthome.sap.util.StrUtils;

import android.content.Context;

import android.database.sqlite.SQLiteDatabase;

import android.database.sqlite.SQLiteOpenHelper;

import android.util.Log;

/**
 * 
 * 数据库初始化
 * 
 * @author wiley
 * 
 * 
 */

public class SqliteHelper {

	private static final String TAG = SqliteHelper.class.getSimpleName();

	private static SQLiteDatabase mWriterHandler;

	private static SQLiteDatabase mReaderHandler;

	private static int mVersion;

	private static String mDatabase;

	public static List<Class<? extends AbstractBaseModel>> mBeanList = new LinkedList<Class<? extends AbstractBaseModel>>();

	private DatabaseHelper mDBHelper;

	@SuppressWarnings("unchecked")
	public SqliteHelper(Context context) {

		Log.i(TAG, "new SqliteHelper");

		mVersion = StrUtils.str2int(
				context.getResources().getString(R.string.database_version), 1);

		mDatabase = StrUtils.null2string(
				context.getResources().getString(R.string.database_name),
				"smarthome.db");

		String[] modelBeans = context.getResources().getStringArray(
				R.array.model_beans);

		if (null != modelBeans) {

			for (String modelBean : modelBeans) {

				try {

					mBeanList.add((Class<? extends AbstractBaseModel>) Class
							.forName(modelBean));

				} catch (ClassNotFoundException e) {

					Log.d(TAG, "", e);

				}

			}

		}

		// 初始化数据库

		if (null != mBeanList && mBeanList.size() > 0) {

			mDBHelper = new DatabaseHelper(context);

			mWriterHandler = mDBHelper.getWritableDatabase();

		}

	}

	public SQLiteDatabase getWriterHandler() {

		if (null == mWriterHandler)

			mWriterHandler = mDBHelper.getWritableDatabase();

		return mWriterHandler;

	}

	public SQLiteDatabase getReaderHandler() {

		if (null == mReaderHandler)

			mReaderHandler = mDBHelper.getWritableDatabase();

		return mReaderHandler;

	}

	public void release() {

		if (null != mWriterHandler) {

			mWriterHandler.close();

			mWriterHandler = null;

		}

		if (null != mReaderHandler) {

			mReaderHandler.close();

			mReaderHandler = null;

		}

	}

	private static class DatabaseHelper extends SQLiteOpenHelper {

		DatabaseHelper(Context context) {

			super(context, mDatabase, null, mVersion);

		}

		@Override
		public void onCreate(SQLiteDatabase db) {

			Log.i(TAG, "onCreate ");

			createTable(db);

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

			Log.i(TAG, "Upgrading database from version " + oldVersion + " to "

			+ newVersion + ", which will destroy all old data");

			// 删除旧表格

			deleteTable(db);

			// 创建新表

			createTable(db);

		}

		@Override
		public void onOpen(SQLiteDatabase db) {

			super.onOpen(db);

			Log.i(TAG, "onOpen ");

		}

		/*
		 * 
		 * 创建所有表
		 */

		private void createTable(SQLiteDatabase db) {

			Log.i(TAG, "createTable begin");

			for (Class<? extends AbstractBaseModel> clazz : mBeanList) {

				try {

					String sqlString = clazz.newInstance()
							.toCreateTableString();

					Log.i(TAG, sqlString);

					db.execSQL(sqlString);

				} catch (IllegalAccessException e) {

					Log.e(TAG, "", e);

				} catch (InstantiationException e) {

					Log.e(TAG, "", e);

				}

			}

			Log.i(TAG, "createTable end");

		}

		/*
		 * 
		 * 删除所有表
		 */

		private void deleteTable(SQLiteDatabase db) {

			Log.i(TAG, "deleteTable begin");

			String sql = "DROP TABLE IF EXISTS ";

			for (Class<? extends AbstractBaseModel> clazz : mBeanList) {

				Log.i(TAG, sql + clazz.getSimpleName());

				db.execSQL(sql + clazz.getSimpleName());

			}

			Log.i(TAG, "deleteTable end");

		}

	}

}
