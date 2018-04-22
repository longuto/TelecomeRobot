package com.yys.telecomrobot.module.start;

import android.os.SystemClock;

import com.yys.telecomrobot.utils.CheckRobotAbilityUtil;

/**
 * Created by yltang3 on 2017/11/17.
 */

public class MainPresenterImp implements MainPresenter {

    MainView mMainView;

    public MainPresenterImp(MainView mainView) {
        this.mMainView = mainView;
    }

    @Override
    public void checkFkj() {
        CheckRobotAbilityUtil.getInstance().check(new CheckRobotAbilityUtil.CallBack() {
            @Override
            public void onSuccess() {
                mMainView.checkSucc();
            }

            @Override
            public void onFailure(String errorMsg) {
                mMainView.checkFail(errorMsg);
            }
        });
    }

    long[] mHints = new long[5];

    @Override
    public void exitMethod() {
        System.arraycopy(mHints, 1, mHints, 0, mHints.length - 1);
        mHints[mHints.length - 1] = SystemClock.uptimeMillis();
        if (SystemClock.uptimeMillis() - mHints[0] <= 1200) {
            mMainView.exitView();
        }
    }
}
