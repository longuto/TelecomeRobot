package com.yys.telecomrobot.app;

import java.io.File;

/**
 * Created by yltang3 on 2017/11/16.
 *
 * 全局变量存储类
 */

public class G {

    public static final String TAG = "TYL"; // TAG名称
    public static final String CRASHCOLLECTDIR = "telecomRobot" + File.separator + "crash";    // 崩溃日志保存地址
    public static final String LIVENESSPICDIR = "telecomRobot" + File.separator + "openaccount";    // 活体监测图片地址
    public static final int CAMERAID = 0;   // 摄像头ID


    /** 开户的套餐种类 */
    public static final String OPENACCOUNTPACK = "openAccountPack";    // 选择的套餐_键
    public static final String OPENACCOUNTSUPERDAY = "openAccountSuperday";    // 选择的套餐_值_超级日租卡
    public static final String OPENACCOUNTENJOY99 = "openAccountEnjoy99";    // 选择的套餐_值_乐享99
    public static final String OPENACCOUNTENJOY199 = "openAccountEnjoy199";    // 选择的套餐_值_乐享199
    public static final String UUID = "uuid";   // 活体监测的uuid

    /** 切换图标的方法 */
    public static final int PRE = 0x1000;    //未经过
    public static final int CURR = 0x1001;   //当前
    public static final int NEXT = 0x1002;   //经过

    /** 号码数据 */
    public static final String numbers[] = {"18752344671","18752344672","18752344673","18752344674","18752344675","18752344676","18752344677","18752344678","18752344679","18752344680","18752344681","18752344682","18752344683","18752344684","18752344685","18752344686","18752344687","18752344688","18752344689"};

    /**
     * 身份证相关信息
     */
    public static final String IDCARDINFO = "idcardInfo"; // 身份证信息
    public static final String IDCARDNUMBER = "idcardNumber"; // 身份证号码
    public static final String PHONE = "phone"; // 手机号码
    public static final String IMGS = "imgs"; // 图片字节数组

    /**
     * 活体监测图片地址相关
     */
    public static String TELECOM_OPENACC_IMAGE_REF1;    // 这是个变量
    public static String TELECOM_OPENACC_IMAGE;         // 这是个变量

    public static final String OPENACC_IMAGE_START = "telecomRobot" + File.separator + "openaccount" + File.separator;
    public static final String IMAGE_REF1 = "image_ref1.jpg";
    public static final String IMAGE = "image.jpg";

    public static final String api_key = "rJqMN8sTxQQmZamImMYxLktbrQxk3n9d";    //申请的key
    public static final String api_secret = "A2y9xJ2lOARR83DnNCF8zAjSFVJR_TbH"; // 申请的secret

}
