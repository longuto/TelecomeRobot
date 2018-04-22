package com.yys.telecomrobot.app;

import android.content.Context;
import android.content.SharedPreferences;

import com.yys.telecomrobot.utils.UiUtils;

/**
 * 配置个人偏爱设置
 * Created by longuto on 2016/10/27.
 */
public class SharedUtils {

    private final String CONFIG_NAME = "normal_config"; //默认配置文件的名称

    public static final String ROBOTNO = "robotNo"; // 机器人编号
    public static final String OPID = "opId";   // 操作员ID
    public static final String OPORGID = "opOrgId"; //区域

    SharedPreferences sp;

    /**系统默认配置文件*/
    public SharedUtils() {
        sp = UiUtils.getContext().getSharedPreferences(CONFIG_NAME, Context.MODE_PRIVATE);
    }

    /**自定义配置文件*/
    public SharedUtils(String name) {
        sp = UiUtils.getContext().getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    /**获取对象*/
    public SharedPreferences getSharedPreferences() {
        return sp;
    }


    /**将字符串储存至XML*/
    public void putStringToXml(String key, String value) {
        sp.edit().putString(key, value).commit();
    }

    /**从XML中获取指定名称的字符串值,没有返回默认值*/
    public String getStringFromXml(String key, String defValue) {
        return sp.getString(key, defValue);
    }

    /**从XML中获取指定名称的字符串值,没有返回null*/
    public String getStringFromXml(String key) {
        return getStringFromXml(key, null);
    }

    /**将布尔值储存至XML*/
    public void putBooleanToXml(String key, boolean value) {
        sp.edit().putBoolean(key, value).commit();
    }

    /**从XML中获取指定名称的布尔值,没有返回默认值*/
    public boolean getBooleanFromXml(String key, boolean defValue) {
        return sp.getBoolean(key, defValue);
    }

    /**从XML中获取指定名称的布尔值值,没有返回false*/
    public boolean getBooleanFromXml(String key) {
        return getBooleanFromXml(key, false);
    }

    /**将整型储存至XML*/
    public void putIntegerToXml(String key, int value) {
        sp.edit().putInt(key, value).commit();
    }

    /**从XML中获取指定名称的整型,没有返回默认值*/
    public int getIntegerFromXml(String key, int defValue) {
        return sp.getInt(key, defValue);
    }

    /**从XML中获取指定名称的整型,没有返回false*/
    public int getIntegerFromXml(String key) {
        return getIntegerFromXml(key, 0);
    }
}
