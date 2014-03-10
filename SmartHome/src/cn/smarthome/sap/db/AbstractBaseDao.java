package cn.smarthome.sap.db;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;
import cn.smarthome.sap.util.DateUtils;
import cn.smarthome.sap.util.StrUtils;

/**
 * 数据库操作基类
 * 
 * @param <T>
 */

public abstract class AbstractBaseDao<T extends AbstractBaseModel> {
	protected static String TAG = AbstractBaseDao.class.getSimpleName();
	private static Map<String, Integer> TYPES = new HashMap<String, Integer>();
	public static final int TYPE_STRING=1;
	public static final int TYPE_INTEGER=2;
	public static final int TYPE_LONG=3;
	public static final int TYPE_SHORT=4;
	public static final int TYPE_FLOAT=5;
	public static final int TYPE_DOUBLE=6;
	
	static {
		TYPES.put("date", TYPE_STRING);
		TYPES.put("string", TYPE_STRING);
		TYPES.put("integer", TYPE_INTEGER);
		TYPES.put("int", TYPE_INTEGER);
		TYPES.put("long", TYPE_LONG);
		TYPES.put("short", TYPE_SHORT);
		TYPES.put("float", TYPE_FLOAT);
		TYPES.put("double", TYPE_DOUBLE);
	}
	//protected SQLiteDatabase dbHandler;
	protected SqliteHelper sqliteHelper;

	public AbstractBaseDao() {

	}

	public void setSqliteHelper(SqliteHelper sqliteHelper) {
		this.sqliteHelper = sqliteHelper;
	}
	
	
	public long getCount(){
		return getCount(null, null, null, null);
	}
	
	public long getCount(String selection, String[] selectionArgs){
		return getCount(selection, selectionArgs, null, null);
	}
	
	public long getCount(String selection, String[] selectionArgs,String groupBy, String having){
		String sqlString="SELECT COUNT(*) AS NUM FROM "+this.getEntityClass().getSimpleName();
		if(!StrUtils.isEmpty(selection)){
			sqlString+=" "+selection;
		}
		if(!StrUtils.isEmpty(groupBy)){
			sqlString+=" "+groupBy;
			if(!StrUtils.isEmpty(having)){sqlString+=having;}
		}
		Cursor cursor=this.execSql(sqlString,selectionArgs);
		cursor.moveToFirst();
		return cursor.getLong(0);
	}

//	public PageList<Map<String, Object>> queryPageList(String[] columns,int pageNum, int maxPerPage){
//		return queryPageList(columns, null, null, null, null, null, pageNum, maxPerPage);
//	}
//	
//	public PageList<Map<String, Object>> queryPageList(String[] columns,String selection, String[] selectionArgs,int pageNum, int maxPerPage){
//		return queryPageList(columns, selection, selectionArgs, null, null, null, pageNum, maxPerPage);
//	}
//	
//	public PageList<Map<String, Object>> queryPageList(String[] columns,String selection, String[] selectionArgs,String orderBy,int pageNum, int maxPerPage){
//		return queryPageList(columns, selection, selectionArgs, null, null, orderBy, pageNum, maxPerPage);
//	}
//	
//	public PageList<Map<String, Object>> queryPageList(String[] columns,String selection, String[] selectionArgs,String groupBy, String having, String orderBy,int pageNum, int maxPerPage){
//		long recordCount=getCount(selection,selectionArgs,groupBy,having);
//		PageListImpl<Map<String, Object>> page = new PageListImpl<Map<String, Object>>();
//		page.calculatePageInfo(pageNum, maxPerPage, recordCount);
//		Cursor cursor=sqliteHelper.getReaderHandler().query(this.getEntityClass().getSimpleName(), columns, selection, selectionArgs, groupBy, having, orderBy,maxPerPage+","+page.getStartRecordNum());
//		List<Map<String, Object>> list=cursor2List(cursor,columns);
//		page.setDataList(list);
//		return page;
//	}
//	
//	public PageList<T> getPageList(int pageNum, int maxPerPage){
//		return getPageList(null, null, null, null, null, pageNum, maxPerPage);
//	}
//	
//	public PageList<T> getPageList(String selection, String[] selectionArgs,int pageNum, int maxPerPage){
//		return getPageList(selection, selectionArgs, null, null, null, pageNum, maxPerPage);
//	}
//	
//	public PageList<T> getPageList(String selection, String[] selectionArgs,String orderBy,int pageNum, int maxPerPage){
//		return getPageList(selection, selectionArgs, null, null, orderBy, pageNum, maxPerPage);
//	}
//	
//	public PageList<T> getPageList(String selection, String[] selectionArgs,String groupBy, String having, String orderBy,int pageNum, int maxPerPage){
//		long recordCount=getCount(selection, selectionArgs, groupBy, having);
//		PageListImpl<T> page = new PageListImpl<T>();
//		page.calculatePageInfo(pageNum, maxPerPage, recordCount);
//		String sqlString="SELECT * FROM "+this.getEntityClass().getSimpleName();
//		if(!StrUtils.isEmpty(selection)){
//			sqlString+=" "+selection;
//		}
//		if(!StrUtils.isEmpty(groupBy)){
//			sqlString+=" "+groupBy;
//			if(!StrUtils.isEmpty(having)){sqlString+=having;}
//		}
//		if(!StrUtils.isEmpty(orderBy)){
//			sqlString+=" "+orderBy;
//		}
//		sqlString+=" LIMIT "+maxPerPage+" OFFSET "+page.getStartRecordNum();
//		Cursor cursor=this.execSql(sqlString);
//		List<T> list=cursor2List(cursor);
//		page.setDataList(list);
//		return page;
//	}
	
	public List<Map<String,Object>> queryList(String[] columns){
		return queryList(columns, null, null, null, null, null);
	}
	
	public List<Map<String,Object>> queryList(String[] columns, String selection, String[] selectionArgs){
		return queryList(columns, selection, selectionArgs, null, null, null);
	}
	
	public List<Map<String,Object>> queryList(String[] columns, String selection, String[] selectionArgs,String orderBy){
		return queryList(columns, selection, selectionArgs, null, null, orderBy);
	}
	
	public List<Map<String,Object>> queryList(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy){
		Cursor cursor=queryCursor(columns, selection, selectionArgs, groupBy, having, orderBy);
		return cursor2List(cursor,columns);
	}
	
	public Cursor queryCursor(String[] columns){
		return queryCursor(columns, null, null, null, null, null);
	}
	
	public Cursor queryCursor(String[] columns, String selection, String[] selectionArgs){
		return queryCursor(columns, selection, selectionArgs, null, null, null);
	}
	
	public Cursor queryCursor(String[] columns, String selection, String[] selectionArgs,String orderBy){
		return queryCursor(columns, selection, selectionArgs, null, null, orderBy);
	}
	
	
	public Cursor queryCursor(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy){
		return sqliteHelper.getReaderHandler().query(this.getEntityClass().getSimpleName(), columns, selection, selectionArgs, groupBy, having, orderBy);
	}
	
	public List<T> getList(){
		return getList(null, null, null, null, null);
	}
	
	public List<T> getList(String selection, String[] selectionArgs){
		return getList(selection, selectionArgs, null, null, null);
	}
	
	public List<T> getList(String selection, String[] selectionArgs,String orderBy){
		return getList(selection, selectionArgs, null, null, orderBy);
	}
	
	public List<T> getList(String selection, String[] selectionArgs,String groupBy, String having, String orderBy){
		Cursor cursor=getCursor(selection, selectionArgs, groupBy, having, orderBy);
		List<T> list=cursor2List(cursor);
		return list;
	}
	
	public Cursor getCursor(){
		return getCursor(null, null, null, null, null);
	}
	
	public Cursor getCursor(String selection, String[] selectionArgs){
		return getCursor(selection, selectionArgs, null, null, null);
	}
	
	public Cursor getCursor(String selection, String[] selectionArgs,String orderBy){
		return getCursor(selection, selectionArgs, null, null, orderBy);
	}
	
	public Cursor getCursor(String selection, String[] selectionArgs,String groupBy, String having, String orderBy){
		String sqlString="SELECT * FROM "+this.getEntityClass().getSimpleName();
		if(!StrUtils.isEmpty(selection)){
			sqlString+=" "+selection;
		}
		if(!StrUtils.isEmpty(groupBy)){
			sqlString+=" "+groupBy;
			if(!StrUtils.isEmpty(having)){sqlString+=having;}
		}
		if(!StrUtils.isEmpty(orderBy)){
			sqlString+=" "+orderBy;
		}
		Cursor cursor=execSql(sqlString,selectionArgs);
		return cursor;
	}

	public T getById(String id){
		if(StrUtils.isEmpty(id))return null;
		String sqlString="SELECT * FROM "+this.getEntityClass().getSimpleName()+" WHERE id=?";
		Cursor cursor=execSql(sqlString, new Object[]{id});
		List<T> list=cursor2List(cursor);
		if(null == list||list.size()==0)return null;
		return list.get(0);
	}

	public void saveOrUpdate(T obj) {
		String sqlString;
		List<Object> objList = new LinkedList<Object>();
		String tmp = "";
		String tmp2 = "";
		int i = 0;
		boolean isInsert=StrUtils.isEmpty(obj.getId());
		if(isInsert)obj.setId(UUID.randomUUID().toString().replaceAll("-", ""));
		JSONObject json = obj.toJSON();
		Iterator<?> it = json.keys();
		while (it.hasNext()) {
			String field = (String) it.next();
			if(!isInsert&&field.equals("id"))continue;
			if (i++ != 0) {
				tmp += ",";
				if(isInsert)tmp2 += ",";
			}
			tmp += isInsert?field:field+"=?";
			if(isInsert)tmp2 += "?";
			try {
				Object value = json.get(field);
				if(value instanceof Date){
					value=DateUtils.date2String((Date)value);
				}
				objList.add(value);
			} catch (JSONException e) {
				//e.printStackTrace();
				Log.e(TAG, e.getMessage());
			}
		}
		if(isInsert){
			sqlString = "INSERT INTO " + obj.getClass().getSimpleName() + "("
			+ tmp + ") VALUES(" + tmp2 + ")";
		}else{
			objList.add(obj.getId());
			sqlString = "UPDATE " + obj.getClass().getSimpleName()
			+ " SET "+tmp+" WHERE id=?";
		}
		execSql(sqlString, objList.toArray());		
	}
	
	public void delete(String id){
		if(StrUtils.isEmpty(id))return;
		String sqlString="DELETE FROM "+this.getEntityClass().getSimpleName()+" WHERE id=?";
		execSql(sqlString, new Object[]{id});
	}
	
	public void delete(T obj) {
		if(StrUtils.isEmpty(obj.getId()))return;
		delete(obj.getId());
	}
	
	public int delete(String whereClause, String[] whereArgs){
		return sqliteHelper.getWriterHandler().delete(this.getEntityClass().getSimpleName(), whereClause, whereArgs);
	}
	
	public long insert(ContentValues values){
		return sqliteHelper.getWriterHandler().insert(this.getEntityClass().getSimpleName(), null, values);
	}
	
	public int update(ContentValues values, String whereClause, String[] whereArgs){
		return sqliteHelper.getWriterHandler().update(this.getEntityClass().getSimpleName(), values, whereClause, whereArgs);
	}
	
	public Cursor execSql(String sqlString) {
		return execSql(sqlString,null);
	}
	
	public Cursor execSql(String sqlString,Object[] bindArgs){
		Log.d(TAG, "SQL:\r\n"+sqlString);
		if(bindArgs != null){
			for (int i = 0; i < bindArgs.length; i++) {
				Log.d(TAG, ""+i+"="+bindArgs[i]);
			}
		}
		Cursor cursor=null;
		if(StrUtils.startsWith(sqlString, "select")){
			//String[] tmps=(String[])Array.newInstance(String.class, bindArgs.length);
			String[] strArgs=(null == bindArgs)?null:Arrays.asList(bindArgs).toArray(new String[]{});
			cursor=sqliteHelper.getReaderHandler().rawQuery(sqlString,strArgs);
		}else{
			sqliteHelper.getWriterHandler().execSQL(sqlString,bindArgs);
		}
		return cursor;
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> cursor2List(Cursor cursor,String[] columns){
		Log.d(TAG, "RESULT:");
		List<Map<String,Object>>  rtnlist=null;
		if(null == cursor||cursor.getCount()==0)return rtnlist;
		T t=(T)this.getEntityObject();
		Map<String,String> fieldMap=t.toFieldMap();
		/*Iterator<String> it=fieldMap.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			fieldMap.put(key.toUpperCase(), fieldMap.get(key));
			fieldMap.remove(key);
		}*/
		rtnlist=new ArrayList<Map<String,Object>>();
		while (cursor.moveToNext()) {
			//String[] columnNames=cursor.getColumnNames();
			Map<String,Object> map=new HashMap<String, Object>();
			for (String column : columns) {
				String column2=""+Character.toUpperCase(column.charAt(0))+column.substring(1);
				String columnType=fieldMap.get(column2);
				Object obj=getValue(cursor, column, columnType);
				map.put(column, obj);
			}
			JSONObject json=new JSONObject(map);
			Log.d(TAG,json.toString());
			rtnlist.add(map);
		}
		return rtnlist;
	}
	
	@SuppressWarnings("unchecked")
	public List<T> cursor2List(Cursor cursor){
		Log.d(TAG, "RESULT:");
		List<T> rtnlist=null;
		if(null == cursor||cursor.getCount()==0)return rtnlist;
		rtnlist=new ArrayList<T>();
		T obj=null;
		while (cursor.moveToNext()) {
			obj=(T)this.getEntityObject();
			Map<String, String> fieldMap=obj.toFieldMap();
			Iterator<String> it=obj.toFieldSet().iterator();
			while (it.hasNext()) {
				String fieldName = (String) it.next();
				try {
					String fieldType=fieldMap.get(fieldName);
					Class clazz=getGenericClass(fieldType);
					Method method=obj.getClass().getMethod("set"+fieldName, clazz);
					Object objArg=getValue(cursor,fieldName,fieldType);
					if(fieldType.equalsIgnoreCase("date")){
						objArg=DateUtils.string2Date(objArg.toString());
					/*}else if(fieldType.equalsIgnoreCase("float")){
						objArg=StrUtils.fixFloat2(Float.parseFloat(objArg.toString()));
					}else if(fieldType.equalsIgnoreCase("double")){
						objArg=StrUtils.fixDouble2(Double.parseDouble(objArg.toString()));
					*/}
					method.invoke(obj, objArg);
				} catch (Exception e) {
					//e.printStackTrace();
					Log.e(TAG, e.getMessage());
					continue;
				}
			}
			Log.d(TAG,obj.toJSONString());
			rtnlist.add(obj);
		}
		return rtnlist;
	}
	
	protected Class<?> getEntityClass() {
		return getGenericClass(this.getClass(), 0);
	}

	@SuppressWarnings("unchecked")
	protected Class getGenericClass(Class clazz, int index) {
		Type genType = clazz.getGenericSuperclass();
		if (genType instanceof ParameterizedType) {
			Type[] params = ((ParameterizedType) genType)
					.getActualTypeArguments();
			if ((params != null) && (params.length >= (index - 1))) {
				return (Class) params[index];
			}
		}// end if.
		return null;
	}

	protected Object getEntityObject() {
		Class<?> clazz = this.getEntityClass();
		Object obj = null;
		try {
			obj = clazz.newInstance();
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
		return obj;
	}
	
	private int getTypeIndex(String typeString) {
		typeString=typeString.toLowerCase();
		return TYPES.containsKey(typeString)?TYPES.get(typeString):0;
	}
	
	@SuppressWarnings("unchecked")
	private Class getGenericClass(String type){
		if(type.equalsIgnoreCase("string")){
			return String.class;
		}else if(type.equalsIgnoreCase("date")){
			return Date.class;
		}else if(StrUtils.startsWith(type, "int")){
			return int.class;
		}else if(StrUtils.startsWith(type, "short")){
			return short.class;
		}else if(StrUtils.startsWith(type, "long")){
			return long.class;
		}else if(StrUtils.startsWith(type, "float")){
			return float.class;
		}else if(StrUtils.startsWith(type, "double")){
			return double.class;
		}else{
			return Object.class;
		}
	}
	
	private Object getValue(Cursor cursor,String fieldName,String fieldType){
		int columnIndex=cursor.getColumnIndex(fieldName.toUpperCase());
		Object objArg=null;
		switch (getTypeIndex(fieldType)) {
		case TYPE_DOUBLE:
			objArg=cursor.getDouble(columnIndex);
			break;
		case TYPE_FLOAT:
			objArg=cursor.getFloat(columnIndex);
			break;
		case TYPE_INTEGER:
			objArg=cursor.getInt(columnIndex);
			break;
		case TYPE_LONG:
			objArg=cursor.getLong(columnIndex);
			break;
		case TYPE_SHORT:
			objArg=cursor.getShort(columnIndex);
			break;
		case TYPE_STRING:
		default:
			objArg=cursor.getString(columnIndex);
		}
		return objArg;
	}
}
