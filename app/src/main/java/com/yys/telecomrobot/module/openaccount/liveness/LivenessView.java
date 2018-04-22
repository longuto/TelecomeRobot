package com.yys.telecomrobot.module.openaccount.liveness;

/**
 * Created by yltang3 on 2017/11/21.
 */

public interface LivenessView {

    /**
     * 加载face++网络成功
     * @param result
     */
    void faceNetOnSucc(String result);

    /**
     * 加载face++网络失败
     * @param error
     */
    void faceNetOnFail(String error);
}
