package com.renying.tool;

import com.yys.telecomrobot.utils.PropertyUtils;

/**
 * Created by yltang3 on 2017/9/11.
 */

public class AiuiObj {

    public static String getSystemProperties(String key){
        return PropertyUtils.get(key,"-1");
    }
}
