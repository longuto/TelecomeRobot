package com.yys.telecomrobot.utils.http;

/**
 * Created by hfwei on 2017/1/4.
 */

public interface OkHttpRequestCallback {
    void onSuccess(String result);
    void onFailure(String error);
}
