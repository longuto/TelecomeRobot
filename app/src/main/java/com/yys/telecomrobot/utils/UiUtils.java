package com.yys.telecomrobot.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.yys.telecomrobot.app.MyApplication;

/**
 * Created by longuto on 2016/10/27.
 */
public class UiUtils {

    /**获取上下文*/
    public static Context getContext() {
        return MyApplication.getContext();
    }

    /**吐司的简化写法*/
    private static Toast mToast;
    public static void showToast(String content) {
        if(mToast == null) {
            mToast = Toast.makeText(getContext(), content, Toast.LENGTH_SHORT);
        }
        mToast.setText(content);
        mToast.show();
    }

    /**获取UiTid*/
    public static int getMainThreadId() {
        return MyApplication.getUiTid();
    }

    //--------------------------常用的获取资源简化写法---------------------------------------------

    /**
     * 根据id获取字符串
     */
    public static String getString(int id) {
        return getContext().getResources().getString(id);
    }

    /**
     * 根据id获取图片
     */
    public static Drawable getDrawable(int id) {
        return getContext().getResources().getDrawable(id);
    }

    /**
     * 根据id获取颜色值
     */
    public static int getColor(int id) {
        return getContext().getResources().getColor(id);
    }

    /**
     * 获取颜色状态集合
     */
    public static ColorStateList getColorStateList(int id) {
        return getContext().getResources().getColorStateList(id);
    }

    /**
     * 根据id获取尺寸
     */
    public static int getDimen(int id) {
        return getContext().getResources().getDimensionPixelSize(id);
    }

    /**
     * 根据id获取字符串数组
     */
    public static String[] getStringArray(int id) {
        return getContext().getResources().getStringArray(id);
    }

    /**
     * dp转px
     */
    public static int dip2px(float dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (density * dp + 0.5);
    }

    /**
     * px转dp
     */
    public static float px2dip(float px) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return px / density;
    }

    /**
     * 获取屏幕的宽度
     * @return
     */
    public static int getScreenWidth() {
        DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获取屏幕的高度
     * @return
     */
    public static int getScreenHeight() {
        DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    private UiUtils() {}    //私有构造方法
}
