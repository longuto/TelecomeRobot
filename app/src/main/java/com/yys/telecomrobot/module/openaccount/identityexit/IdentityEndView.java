package com.yys.telecomrobot.module.openaccount.identityexit;

/**
 * Created by yltang3 on 2017/11/22.
 */

public interface IdentityEndView {

    void onTick(long millisUntilFinished);

    void onFinish();

    void onFail();

    void brushcardSucc();

    void brushcardFail();
}
