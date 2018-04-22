package com.yys.telecomrobot.module.openaccount.identityexit;

/**
 * Created by yltang3 on 2017/11/22.
 */

public interface IdentityEndPresenter {

    /**
     * 倒计时刷身份证
     */
    void brushIdcard();

    /**
     * 停止刷身份证
     */
    void stopBrushCard();

    /**
     * 重新获取焦点
     */
    void onResume();

    /**
     * 失去焦点
     */
    void onPause();
}
