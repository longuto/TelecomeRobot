package com.yys.telecomrobot.module.start;

/**
 * Created by yltang3 on 2017/11/17.
 *
 * 主界面控制逻辑回调方式
 */

public interface MainView {

    /**
     * 检测发卡机能力成功
     */
    void checkSucc();

    /**
     * 检测发卡机能力失败
     * @param msg 失败原因
     */
    void checkFail(String msg);

    void exitView();
}
