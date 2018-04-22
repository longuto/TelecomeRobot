package com.yys.telecomrobot.utils;

import android.os.Handler;
import android.os.Looper;

import com.cmcc.nativepackage.IDCard;
import com.ist.nativepackage.Fkj;
import com.yys.telecomrobot.app.MessageConstant;

/**
 * Created by yltang3 on 2017/9/5.
 */

public class CheckRobotAbilityUtil {

    public interface CallBack {
        void onSuccess();

        void onFailure(String errorMsg);
    }

    private static String TAG = CheckRobotAbilityUtil.class.getSimpleName();

    private static CheckRobotAbilityUtil mInstance;

    private Handler mHandler;

    public static CheckRobotAbilityUtil getInstance() {
        if (null == mInstance) {
            synchronized (CheckRobotAbilityUtil.class) {
                if (null == mInstance) {
                    mInstance = new CheckRobotAbilityUtil();
                }
            }
        }
        return mInstance;
    }

    private CheckRobotAbilityUtil() {
        mHandler = new Handler(Looper.getMainLooper());
    }

    public void check(final CallBack callBack) {

        ThreadPool.getInstance().getProcessorsPools().execute(new Runnable() {
            @Override
            public void run() {
                int ret = IDCard.openIDCard(0, "", "");
                if(0 != ret) {
                    LogUtils.i(TAG, "连接身份证失败");
                    IDCard.closeIDCard();
                    checkFail(callBack, MessageConstant.CHECK_CARD_ERROR);
                    return;
                }
                LogUtils.i(TAG, "连接身份证成功");
                ret = IDCard.initialIDCard();
                if(0 != ret) {
                    LogUtils.i(TAG, "初始化二代身份证失败");
                    IDCard.closeIDCard();
                    checkFail(callBack, MessageConstant.CHECK_CARD_ERROR);
                    return;
                }
                LogUtils.i(TAG, "初始化二代身份证成功");
                IDCard.closeIDCard();

                ret = Fkj.init(0);    // 制卡机初始化复位
                if (0 != ret) {     // 初始化失败
                    LogUtils.i(TAG, "初始化失败");
                    checkFail(callBack, MessageConstant.CHECK_FKJ_ERROR);
                    return;
                }
                LogUtils.i(TAG, "初始化成功");

                int[] states = Fkj.getStatus(0);     // 查询制卡机的状态
                if (null == states) {     // 查询不到制卡机状态
                    LogUtils.i(TAG, "查询不到制卡机状态");
                    checkFail(callBack, MessageConstant.CHECK_FKJ_ERROR);
                    return;
                }
                LogUtils.i(TAG, "制卡机状态正常");

                ret = Fkj.moveCard(0, 1);     // 移动SIM卡到写卡位置
                if (0 != ret) {     // 移动SIM到写卡位置失败
                    LogUtils.i(TAG, "移动SIM到写卡位置失败ret:" + ret);
                    checkFail(callBack, MessageConstant.CHECK_FKJ_HAVECARD);
                    return;
                }
                LogUtils.i(TAG, "移动SIM到写卡位置成功");

                Fkj.uninit();   // 逆初始化
                postMainThread(true, "", callBack);
                return;
            }
        });
    }

    private void checkFail(CallBack callback, String errorMsg) {
        Fkj.uninit();   // 逆初始化
        postMainThread(false, errorMsg, callback);
    }

    /** 在主线程执行回调方法 */
    private void postMainThread(final boolean flag, final String msg, final CallBack callBack) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if(flag) {
                    callBack.onSuccess();
                } else {
                    callBack.onFailure(msg);
                }
            }
        });
    }
}
