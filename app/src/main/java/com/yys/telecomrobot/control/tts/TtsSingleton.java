package com.yys.telecomrobot.control.tts;

import com.iflytek.cloud.SynthesizerListener;
import com.yys.telecomrobot.utils.UiUtils;

/**
 * 语音合成工具类
 * Created by yltang3 on 2017/2/27.
 */
public class TtsSingleton {

    private static TtsSingleton mInstance = new TtsSingleton(); //单例模式

    /** 私有构造方法 */
    private TtsSingleton() {
        mTtsImpl = new TtsImpl(UiUtils.getContext());
    };

    /** 获取单例对象 */
    public static TtsSingleton getInstance() {
        return mInstance;
    }

    private TtsImpl mTtsImpl;

    private SynthesizerListener mSynthesizerListener;

    private boolean mIsPlayTts = true;    //是否启动语音合成功能

    /** 设置参数 */
    public void setParam(String key, String value) {
        mTtsImpl.setParam(key, value);
    }

    /** 设置合成监听 */
    public void setSynthesizerListener(SynthesizerListener synthesizerListener) {
        mSynthesizerListener = synthesizerListener;
    }

    /** 销毁 */
    public void destory() {
        mTtsImpl.destory();
    }

    /** 设置当前播放控制 */
    public void setPlayConfig(boolean isPlayer) {
        mIsPlayTts = isPlayer;
    }

    /** 开始说话 */
    public void startSynthesizer(String content) {
        if(mIsPlayTts) {
            mTtsImpl.startSynthesizer(content, mSynthesizerListener);
        }
    }

    /** 停止说话 */
    public void stopSynthesizer() {
        if(mIsPlayTts) {
            mTtsImpl.stopSynthesizer();
        }
    }

    /** 暂停说话 */
    public void pausedSynthesizer() {
        if(mIsPlayTts) {
            mTtsImpl.pausedSynthesizer();
        }
    }

    /** 继续说话 */
    public void resumedSynthesizer() {
        if(mIsPlayTts) {
            mTtsImpl.resumedSynthesizer();
        }
    }

    /** 当前是否说话 */
    public boolean isSpeaking() {
        return mTtsImpl.isSpeaking();
    }

}
