package com.yys.telecomrobot.module.openaccount.identityenter;

/**
 * Created by yltang3 on 2017/11/20.
 */

public interface IdentityStartView {

    /**
     * 倒计时每秒回调
     */
    void onTick(long millisUntilFinished);

    /**
     * 倒计时结束
     */
    void onFinish();

    /**
     * 打开刷身份证能力失败
     */
    void onFail();

    /**
     * 刷身份证成功
     * @param idcards 身份证信息数组
     * @param imgs  图片数组
     */
    void brushcardSucc(String[] idcards, byte[] imgs);

    /**
     * 活体监测联网授权成功
     * @param uuid
     */
    void netWorkSucc(String uuid);

    /**
     * 活体监测联网授权失败
     */
    void netWorkFail();
}
