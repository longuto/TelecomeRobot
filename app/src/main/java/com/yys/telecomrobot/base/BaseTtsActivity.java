package com.yys.telecomrobot.base;

import com.yys.telecomrobot.control.tts.TtsSingleton;

/**
 * Created by yltang3 on 2017/11/17.
 *
 * 只有语音合成能力
 */

public abstract class BaseTtsActivity extends BaseActivity {

    private TtsSingleton mTtsSingleton;

    @Override
    protected void initAbility() {
        mTtsSingleton = TtsSingleton.getInstance();
    }

    public TtsSingleton getTtsSingleton() {
        return mTtsSingleton;
    }

    public void speak(String content) {
        if(null != mTtsSingleton) mTtsSingleton.startSynthesizer(content);
    }
}
