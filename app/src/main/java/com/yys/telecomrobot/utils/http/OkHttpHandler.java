package com.yys.telecomrobot.utils.http;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;

/**
 * Created by hfwei on 2017/1/6.
 */

public class OkHttpHandler extends Handler {

    static final int MESSAGE_REQUEST_SUCCESS = 1;
    static final int MESSAGE_REQUEST_FAILURE = 2;

    public OkHttpHandler(Looper looper) {
        super(looper);
    }

    @Override
    public void handleMessage(Message message) {
        OkHttpRequestCallback okHttpRequestCallback;
        String result;
        switch (message.what) {
            case MESSAGE_REQUEST_SUCCESS:
                okHttpRequestCallback = (OkHttpRequestCallback) message.obj;
                result = message.getData().getString("result");
                if (null != okHttpRequestCallback) {
                    okHttpRequestCallback.onSuccess(result);
                }
                break;
            case MESSAGE_REQUEST_FAILURE:
                okHttpRequestCallback = (OkHttpRequestCallback) message.obj;
                result = message.getData().getString("result");
                if (null != okHttpRequestCallback && !TextUtils.isEmpty(result)) {
                    okHttpRequestCallback.onFailure(result);
                }
                break;
            default:
                break;
        }
    }
}
