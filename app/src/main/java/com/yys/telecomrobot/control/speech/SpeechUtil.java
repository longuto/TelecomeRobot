package com.yys.telecomrobot.control.speech;

import android.os.Bundle;
import android.util.Log;

import com.iflytek.aipsdk.asr.RecognizerListener;
import com.iflytek.aipsdk.asr.RecognizerResult;
import com.iflytek.aipsdk.asr.SpeechRecognizer;
import com.iflytek.aipsdk.common.InitListener;
import com.iflytek.aipsdk.util.ErrorCode;
import com.iflytek.aipsdk.util.SpeechConstant;
import com.iflytek.aipsdk.util.SpeechError;
import com.yys.telecomrobot.R;
import com.yys.telecomrobot.utils.LogUtils;
import com.yys.telecomrobot.utils.UiUtils;

/**
 * Created by yltang3 on 2017/9/13.
 * 根据AI+的语义理解Demo - AIPSDKDemo_a编写
 */

public class SpeechUtil {

    private static String TAG = SpeechUtil.class.getSimpleName();

    public interface OnRecognizerCallBack {
        void onVolumeChanged(int i, byte[] bytes);  // 音量回调接口
        void onBeginOfSpeech();    // 开始说话
        void onEndOfSpeech();      // 结束说话
        void onResult(RecognizerResult recognizerResult, boolean b);    // 返回说话内容
        void onError(SpeechError speechError);     // 错误内容
        void onEvent(int i, int i1, int i2, Bundle bundle);     // 事件
        void onWakeUp(String s, int i);     // 唤醒
    }

    public static SpeechUtil getInstance() {    // CallBack写在这，是为防止多次调用getResult出现的问题
        if (null == mInstance) {
            synchronized (SpeechUtil.class) {
                if (null == mInstance) {
                    mInstance = new SpeechUtil();
                }
            }
        }
        return mInstance;
    }

    private static SpeechUtil mInstance; // 单例
    private SpeechRecognizer mIat;  // 语音识别客户端
    private boolean mIsStart;   // 是否已经开启说话
    private OnRecognizerCallBack mCallBack;

    private SpeechUtil() {
        mIat = SpeechRecognizer.createRecognizer(UiUtils.getContext(), mInitListener);
        setParam(); // 设置当前参数
    }

    /** 设置当前参数 */
    private void setParam() {
        mIat.setParameter(SpeechConstant.PARAM, UiUtils.getString(R.string.rcg_params));    // 开启识别
    }

    /** 监听器 */
    InitListener mInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            if(ErrorCode.SUCCESS != code) {
                UiUtils.showToast("初始化失败，错误码");
            }
        }
    };

    /** 听写监听器 */
    RecognizerListener mRecognizerListener = new RecognizerListener() {
        @Override
        public void onVolumeChanged(int volume, byte[] data) {
            Log.i(TAG, "当前音量为：" + volume);
            if(null != mCallBack) {
                mCallBack.onVolumeChanged(volume, data);
            }
        }

        @Override
        public void onBeginOfSpeech() {
            Log.i(TAG, "开始说话...");
            mIsStart = true;
            if(null != mCallBack) {
                mCallBack.onBeginOfSpeech();
            }
        }

        @Override
        public void onEndOfSpeech() {
            Log.i(TAG, "结束说话...");
            mIsStart = false;
            if(null != mCallBack) {
                mCallBack.onEndOfSpeech();
            }
        }

        @Override
        public void onResult(RecognizerResult recognizerResult, boolean isLast) {
            Log.i(TAG, "说话内容---onResult");
            if(null != mCallBack) {
                mCallBack.onResult(recognizerResult, isLast);
            }
        }

        @Override
        public void onError(SpeechError speechError) {
            Log.i(TAG, "说话出错---onError");
            mIsStart = false;
            if(null != mCallBack) {
                mCallBack.onError(speechError);
            }
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            Log.i(TAG, "说话事件---onEvent");
            if(null != mCallBack) {
                mCallBack.onEvent(eventType, arg1, arg2, obj);
            }
        }

        @Override
        public void onWakeUp(String str, int code) {
            Log.i(TAG, "唤醒词为" + str + ",错误码为：" + code);
            mIsStart = false;
            // 0表示唤醒成功, 非0表示不成功
            if(ErrorCode.SUCCESS != code) {
                UiUtils.showToast("唤醒失败，错误码为" + code);
            }
            if(null != mCallBack) {
                mCallBack.onWakeUp(str, code);
            }
        }
    };

    //-----------------暴露的公共方法-----------------
    /** 增加监听 */
    public void addCallBack(OnRecognizerCallBack callBack) {
        mCallBack = callBack;
    }

    /** 移除监听 */
    public void removeCallBack() {
        if(null != mCallBack) {
            mCallBack = null;
        }
    }

    /** 开始识别之前,需要先注册监听 */
    public void startSpeechRecognizer() {
        int error;
        if(!mIsStart) {
            mIsStart = true;
            error = mIat.startListening(mRecognizerListener);
        } else {
            LogUtils.i(TAG, "已开启识别");
            return;
        }

        if(ErrorCode.SUCCESS != error) {
            UiUtils.showToast("听写失败错误码为");
        } else {
            LogUtils.i(TAG, "开启识别正常");
        }
    }

    /** 停止识别 */
    public void stopSpeechRecognizer() {
        if(null != mIat) {
            mIsStart = false;
            mIat.stopListening();
        }
    }

    /** 退出时释放连接 */
    public void onDestroy() {
        mIat.stopListening();
        mIat.destroy();
    }
}
