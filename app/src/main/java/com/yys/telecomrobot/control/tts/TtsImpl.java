package com.yys.telecomrobot.control.tts;

import android.content.Context;
import android.media.AudioManager;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.util.ResourceUtil;
import com.yys.telecomrobot.utils.LogUtils;
import com.yys.telecomrobot.utils.UiUtils;

/**
 * 语音合成的实现方法
 * Created by yltang3 on 2017/2/27.
 */
public class TtsImpl {

    private static String TAG = TtsImpl.class.getSimpleName();  //Log标记
    private Context mContext;   //上下文
    private SpeechSynthesizer mTts; //SpeechSynthesizer对象
    private String mVoicer = "jiajia";  //默认发音人
//    private String mVoicer = "catherine";  //默认发音人

    public TtsImpl(Context context) {
        mContext = context;
        mTts = SpeechSynthesizer.createSynthesizer(mContext, mTstInitListener);
        setParam();
    }

    /**
     * 设置语音合成参数
     */
    private void setParam() {
        //设置引擎类型
        setParam(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL);
        //设置发音人资源路径
        setParam(ResourceUtil.TTS_RES_PATH, getResourcePath(mVoicer));
        //设置发音人
        setParam(SpeechConstant.VOICE_NAME, mVoicer);
        //设置语速
        setParam(SpeechConstant.SPEED, "55");
        //设置音量，范围 0~100
        setParam(SpeechConstant.VOLUME, "60");
        //设置播放器音频流类型
        setParam(SpeechConstant.STREAM_TYPE, String.valueOf(AudioManager.STREAM_MUSIC));
        //设置合成音频保存位置（可自定义保存位置） ，保存在“./sdcard/iflytek.pcm”
//		setParam(SpeechConstant.TTS_AUDIO_PATH, "./sdcard/iflytek.pcm");
    }

    /** 根据名称获取assets资源路径 */
    private String getResourcePath(String voicer) {
        StringBuffer tempBuffer = new StringBuffer();
        //合成通用资源
        tempBuffer.append(ResourceUtil.generateResourcePath(mContext, ResourceUtil.RESOURCE_TYPE.assets, "tts/common.jet"));
        tempBuffer.append(";");
        //发音人资源
        tempBuffer.append(ResourceUtil.generateResourcePath(mContext, ResourceUtil.RESOURCE_TYPE.assets, "tts/" + voicer + ".jet"));
        return tempBuffer.toString();
    }

    /**
     * 初始化监听
     */
    InitListener mTstInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            if(ErrorCode.SUCCESS != code) {
                UiUtils.showToast("初始化语音合成失败，错误码：" + code);
            }
        }
    };

    // ------------- 暴露的公共方法-----------------------

    /** 设置语音合成参数 */
    public void setParam(String key, String value) {
        if(mTts != null) {
            mTts.setParameter(key, value);
        }
    }

    /** 开始说话 */
    public void startSynthesizer(String content, SynthesizerListener synthesizerListener) {
        int code = 0;
        if(mTts != null) {
            code = mTts.startSpeaking(content, synthesizerListener);
        }
        LogUtils.i(TAG, "startSynthesizer,ErrorCode为:" + code);
        if(ErrorCode.SUCCESS != code) {
            UiUtils.showToast("调用startSpeaking方法失败，错误码：" + code);
        }
    }

    /** 停止说话 */
    public void stopSynthesizer() {
        LogUtils.i(TAG, "stopSynthesizer");
        if(mTts != null) {
            mTts.stopSpeaking();
        }
    }

    /** 暂停说话 */
    public void pausedSynthesizer() {
        LogUtils.i(TAG, "pausedSynthesizer");
        if(mTts != null) {
            mTts.pauseSpeaking();
        }
    }

    /** 继续说话 */
    public void resumedSynthesizer() {
        LogUtils.i(TAG, "resumedSynthesizer");
        if(mTts != null) {
            mTts.resumeSpeaking();
        }
    }

    /** 是否说话 */
    public boolean isSpeaking() {
        if(mTts != null) {
            return mTts.isSpeaking();
        }
        return false;
    }

    /** 在app的销毁方法中调用此功能 */
    public void destory() {
        mTts.stopSpeaking();
        mTts.destroy();
        mTts = null;
    }

}
