package com.yys.telecomrobot.utils;

import android.content.Intent;
import android.provider.Settings;

/**
 * Created by yltang3 on 2017/11/24.
 */

public class WewinsBarUtil {

    public static void openBar() {
        Intent intent = new Intent();
        Settings.System.putInt(UiUtils.getContext().getContentResolver(), "alway_hide_statusbar", 0);    // 显示
        intent.setAction("com.android.systembar.hide");
        UiUtils.getContext().sendBroadcast(intent);
    }

    public static void closeBar() {
        Intent intent = new Intent();
        Settings.System.putInt(UiUtils.getContext().getContentResolver(), "alway_hide_statusbar", 1);    // 隐藏
        intent.setAction("com.android.systembar.hide");
        UiUtils.getContext().sendBroadcast(intent);
    }
}
