package com.yys.telecomrobot.control.voice;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yltang3 on 2017/11/21.
 */

public class BusinessIdConfig {

    public static Map<String, String> businessChoosephoneMap = new HashMap<>();

    static {
        businessChoosephoneMap.put("321", "自助开卡");
        businessChoosephoneMap.put("322", "超级日租卡");
        businessChoosephoneMap.put("323", "乐享99套餐");
        businessChoosephoneMap.put("324", "乐享199套餐");
    }

    /**
     * 是否走语义，根据业务编写
     */
    public static boolean isBusinessChoosephone(String id) {
        for (String tmp : businessChoosephoneMap.keySet()) {
            if (tmp.equals(id)) {
                return true;
            }
        }
        return false;
    }

    public static String[] BUSINESSCONTENTS = {"很高心为您服务，我可以为您办理自助开卡", "您可以对我说自助开卡", "您可以点击自助开卡按钮", "看来不愿意和我说话了，我先安静会儿"};
    public static String[] PACKCONTENTS = {"三种超级不限量套餐详情请查看", "请说出您想办理的套餐", "请点击需要办理的套餐", "看来不愿意和我说话了，我先安静会儿"};
}
