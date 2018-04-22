package com.renying.tool;


import java.lang.reflect.Method;

public class MySystemProperties {
	private static final String TAG = "MySystemProperties";

	// String SystemProperties.get(String key){}
	public static String get(String key, String def) {
		init();
		
		String value = def;
		
		try {
			value = (String) mGetMethod.invoke(mClassType, key);
			if(value.length()==0)
				value=def;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return value;
	}
	
	//int SystemProperties.get(String key, int def){}
	public static int getInt(String key, int def) {
		init();
		
		int value = def;
		try {
			Integer v = (Integer) mGetIntMethod.invoke(mClassType, key, def);
			value = v.intValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
	
	public static void setInt(String key, int val) {
		init();
		
		try {
			mSetIntMethod.invoke(mClassType, key, val);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	public static int getSdkVersion() {
		return getInt("ro.build.version.sdk", -1);
	}
	
	//-------------------------------------------------------------------
	private static Class<?> mClassType = null;
	private static Method mGetMethod = null;
	private static Method mGetIntMethod = null;
	private static Method mSetIntMethod = null;
	
	private static void init() {
		try {
			if (mClassType == null) {
				mClassType = Class.forName("android.os.SystemProperties");
				
				mGetMethod = mClassType.getDeclaredMethod("get", String.class);
				mGetIntMethod = mClassType.getDeclaredMethod("getInt", String.class, int.class);
				mSetIntMethod = mClassType.getDeclaredMethod("setInt", String.class, int.class);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}