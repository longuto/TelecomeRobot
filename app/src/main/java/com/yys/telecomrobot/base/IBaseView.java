package com.yys.telecomrobot.base;

import android.content.Context;

/**
 * Created by yltang3 on 2017/11/16.
 *
 * 基类界面数据
 */

public interface IBaseView {

    /** 显示进度文本 */
    void showProgress(String content);

    /** 隐藏进度文本 */
    void hideProgress();

    /**
     * 获取当前上下文对象
     * @return
     */
    Context getContext();

    /**
     * 结束当前页面
     */
    void close();
}
