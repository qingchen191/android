package cn.smarthome.sap.util;

public class StrUtils {

	public static int str2int(String string, int i) {
		try {
			return Integer.parseInt(string);
		} catch (NumberFormatException e) {
			return i;
		}
	}

	public static String null2string(String string, String string2) {
		if (null == string || "".equals(string.trim())) {
			return string2;
		} else {
			return string;
		}
	}

	public static boolean isEmpty(String string) {
		// 
		if (null == string || "".equals(string.trim())){
			return true;
		}else {
			return false;
		}
		
	}

	public static boolean startsWith(String str, String startStr) {
		// 
		if(str != null && str.toLowerCase().startsWith(startStr.toLowerCase())){
			return true;
		} else {
			return false;
		}
		
	}

}
