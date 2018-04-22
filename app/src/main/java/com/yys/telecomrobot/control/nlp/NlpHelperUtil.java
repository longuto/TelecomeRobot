package com.yys.telecomrobot.control.nlp;

import com.iflytek.aipsdk.nlp.INlpListener;
import com.iflytek.aipsdk.nlp.NlpHelper;
import com.yys.telecomrobot.R;
import com.yys.telecomrobot.utils.UiUtils;

/**
 * Created by yltang3 on 2017/8/31.
 * 根据AI+的语义理解Demo - AIPSDKDemo_a编写
 */
public class NlpHelperUtil {

    public interface CallBack {
        void getResultByBus(String s, int i);            // 走业务
        void getResultByChat(String s, int i);      // 走闲聊
    }

    private static NlpHelperUtil mInstance; // 单例
    private NlpHelper mNlpHelper;
    private static CallBack mCallBack;
    private boolean mIsNlpByChat;   // 语义是否走闲聊

    public static NlpHelperUtil getInstance() {
        if (null == mInstance) {
            synchronized (NlpHelperUtil.class) {
                if (null == mInstance) {
                    mInstance = new NlpHelperUtil();
                }
            }
        }
        return mInstance;
    }

    private NlpHelperUtil() {
        mNlpHelper = new NlpHelper();
    }

    public void getResultByBus(String content) {
        mIsNlpByChat = false;
        mNlpHelper.getResult(UiUtils.getString(R.string.nlp_url), content, iNlpListener);
    }

    public void getResultByChat(String content) {
        mIsNlpByChat = true;
        mNlpHelper.getResult(UiUtils.getString(R.string.nlp_url_chat), content, iNlpListener);
    }

    INlpListener iNlpListener = new INlpListener() {
        @Override
        public void onResult(String s, int i) {
            if(mIsNlpByChat) {
                if(null != mCallBack) mCallBack.getResultByChat(s, i);
            } else {
                if(null != mCallBack) mCallBack.getResultByBus(s, i);
            }
        }
    };

    public void addCallBack(CallBack callBack) {
        mCallBack = callBack;
    }

    public void removeCallBack() {
        if(null != mCallBack) {
            mCallBack = null;
        }
    }
}
