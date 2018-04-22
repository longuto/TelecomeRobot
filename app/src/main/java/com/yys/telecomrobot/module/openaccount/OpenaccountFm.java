package com.yys.telecomrobot.module.openaccount;

import android.content.Intent;

import com.yys.telecomrobot.app.G;
import com.yys.telecomrobot.base.BaseFragment;
import com.yys.telecomrobot.base.BaseTtsActivity;
import com.yys.telecomrobot.control.tts.TtsSingleton;
import com.yys.telecomrobot.module.start.MainActivity;
import com.yys.telecomrobot.utils.LogUtils;

/**
 * Created by yltang3 on 2017/11/20.
 */

public abstract class OpenaccountFm extends BaseFragment {

    private OpenaccountActivity mCustomActivity;  // 当前的Activity

    protected TtsSingleton getTtsSingleton() {
        if(getActivity() instanceof BaseTtsActivity) {
            return ((BaseTtsActivity) getActivity()).getTtsSingleton();
        } else {
            LogUtils.e(G.TAG, "继承的父Activity没有tts能力");
            return null;
        }
    }

    protected void speak(String content) {
        if(getActivity() instanceof BaseTtsActivity) {
            ((BaseTtsActivity)getActivity()).speak(content);
        } else {
            LogUtils.e(G.TAG, "继承的父Activity没有tts能力");
        }
    }

    /** 跳转至首页 */
    protected void enterMainAct() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
        close();
    }

    /** 返回当前的Activity */
    protected OpenaccountActivity getCustomActivity() {
        if(null == mCustomActivity) {
            mCustomActivity = (OpenaccountActivity) getActivity();
        }
        return mCustomActivity;
    }
}
