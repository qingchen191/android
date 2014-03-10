package cn.smarthome.sap.db;

import java.io.Serializable;

import java.lang.reflect.Method;

import java.util.HashMap;

import java.util.Iterator;

import java.util.Map;

import java.util.Set;

import org.json.JSONObject;

import android.util.Log;

/**
 * 
 * 实体模型基类
 * 
 */

public abstract class AbstractBaseModel implements Serializable {

	private static final long serialVersionUID = -4685989463430616599L;

	private static String TAG = AbstractBaseModel.class.getSimpleName();

	private static Map<String, String> TYPES = new HashMap<String, String>();

	static {

		TYPES.put("date", "TEXT");

		TYPES.put("string", "TEXT");

		TYPES.put("integer", "INTEGER");

		TYPES.put("int", "INTEGER");

		TYPES.put("short", "INTEGER");

		TYPES.put("long", "INTEGER");

		TYPES.put("float", "REAL");

		TYPES.put("double", "REAL");

	}

	public AbstractBaseModel() {

		TAG = this.getClass().getSimpleName();

	}

	public abstract String getId();

	public abstract void setId(String id);

	public Set<String> toFieldSet() {

		return this.toFieldMap().keySet();

	}

	public Map<String, String> toFieldMap() {

		Map<String, String> fieldMap = new HashMap<String, String>();

		Method[] methods = this.getClass().getMethods();

		try {

			String propertyName;

			String typeString;

			for (Method method : methods) {

				String methodName = method.getName();

				if (!methodName.startsWith("get")

				|| methodName.equalsIgnoreCase("getClass")

				|| methodName.equalsIgnoreCase("get"))

					continue;

				typeString = method.getReturnType().getSimpleName();

				propertyName = methodName.substring(3);

				fieldMap.put(propertyName, typeString);

			}

		} catch (Exception e) {

			Log.e(TAG, e.getMessage());

		}

		return fieldMap;

	}

	public String toCreateTableString() {

		StringBuilder sb = new StringBuilder();

		sb.append("CREATE TABLE ");

		sb.append(this.getClass().getSimpleName());

		sb.append(" (");

		int i = 0;

		Map<String, String> fieldMap = toFieldMap();

		Iterator<String> it = fieldMap.keySet().iterator();

		while (it.hasNext()) {

			String field = it.next();

			String type = fieldMap.get(field).toLowerCase();

			if (i++ != 0)

				sb.append(",");

			sb.append(field.toUpperCase());

			sb.append(" "
					+ (TYPES.containsKey(type) ? TYPES.get(type) : "NONE"));

			if (field.equalsIgnoreCase("id"))

				sb.append(" PRIMARY KEY");

		}

		sb.append(");");

		return sb.toString();

	}

	public String toJSONString() {

		return this.toJSON().toString();

	}

	public JSONObject toJSON() {

		Method[] methods = this.getClass().getMethods();

		JSONObject json = new JSONObject();

		Object propertyValue;

		String propertyName;

		try {

			for (Method method : methods) {

				String methodName = method.getName();

				if (!methodName.startsWith("get")

				|| methodName.equalsIgnoreCase("getClass")

				|| methodName.equalsIgnoreCase("get"))

					continue;

				propertyValue = method.invoke(this, new Object[] {});

				propertyName = methodName.substring(3);

				propertyName = Character.toLowerCase(propertyName.charAt(0))

				+ propertyName.substring(1);

				json.put(propertyName, propertyValue);

			}

		} catch (Exception e) {

			Log.e(TAG, e.getMessage());

		}

		return json;

	}

}