package com.yys.telecomrobot.control.voice;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.iflytek.aipsdk.asr.RecognizerResult;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.util.Logs;
import com.yys.telecomrobot.app.NetAddressConstant;
import com.yys.telecomrobot.control.nlp.NlpHelperUtil;
import com.yys.telecomrobot.control.speech.SpeechUtil;
import com.yys.telecomrobot.control.tts.TtsSingleton;
import com.yys.telecomrobot.model.KnowledgeRes;
import com.yys.telecomrobot.model.NlpHelperRes;
import com.yys.telecomrobot.model.RecResult;
import com.yys.telecomrobot.utils.GsonUtils;
import com.yys.telecomrobot.utils.TimerUtil;
import com.yys.telecomrobot.utils.http.OkHttpRequestCallback;
import com.yys.telecomrobot.utils.http.OkHttpUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yltang3 on 2017/9/14.
 * 交互流程2 -- 运营商产品2.0业务交互方式
 */

public class VoiceMethodBus {

    private static String TAG = VoiceMethodBus.class.getSimpleName();

    private boolean isNoNeedChat = false;  // 是否不需要请求闲聊， false请求  true不请求

    public interface CallBack {
        void start();
        void stop();
        void stopByError(String errorMsg);
        void startSpeech();
        void stopSpeech(String speechMsg);
        void business(String id);
        void volumeChanged(int i, byte[] bytes);
    }

    private static VoiceMethodBus mInstance; // 单例

    public static VoiceMethodBus getInstance() {
        if (null == mInstance) {
            synchronized (VoiceMethodBus.class) {
                if (null == mInstance) {
                    mInstance = new VoiceMethodBus();
                }
            }
        }
        return mInstance;
    }

    private VoiceMethodBus() {
        mTtsInstance = TtsSingleton.getInstance();
        mSpeechUtil = SpeechUtil.getInstance();
        mNlpHelperUtil = NlpHelperUtil.getInstance();
        mTimerUtil = TimerUtil.getInstance();
    }

    private TtsSingleton mTtsInstance;      // tts单例
    private SpeechUtil mSpeechUtil;
    private NlpHelperUtil mNlpHelperUtil;   // 语义理解单例
    private TimerUtil mTimerUtil;       // 时间控制单例
    private CallBack mCallBack;

    boolean mIsNeedSpeech; // 合成之后，是否需要识别
    boolean mAccSpeechContent;   // 识别到内容以后，是否更换识别内容
    String mContent;    // 识别到的文本内容

    int mCurrentStep;   // 当前的步骤
    int MAXSTEP = 3;    // 最大次数

    String[] contents = {"第一个引导词", "第二个引导词", "第三个引导词", "结束引导词"};

    /** 初始化能力 */
    private void initAbilityListen() {
        mTtsInstance.setSynthesizerListener(new SynthesizerListener() {
            @Override
            public void onSpeakBegin() {

            }

            @Override
            public void onBufferProgress(int i, int i1, int i2, String s) {

            }

            @Override
            public void onSpeakPaused() {

            }

            @Override
            public void onSpeakResumed() {

            }

            @Override
            public void onSpeakProgress(int i, int i1, int i2) {

            }

            @Override
            public void onCompleted(SpeechError speechError) {
                if(mIsNeedSpeech) {
                    doSpeech();
                }
                mIsNeedSpeech = false;
            }

            @Override
            public void onEvent(int i, int i1, int i2, Bundle bundle) {

            }
        });

        mSpeechUtil.addCallBack(new SpeechUtil.OnRecognizerCallBack() {
            @Override
            public void onVolumeChanged(int i, byte[] bytes) {
                if(null != mCallBack) mCallBack.volumeChanged(i, bytes);
            }

            @Override
            public void onBeginOfSpeech() {

            }

            @Override
            public void onEndOfSpeech() {
            }

            @Override
            public void onResult(RecognizerResult recognizerResult, boolean b) {
                Log.i(TAG, "onResult");
                if(null != recognizerResult && !TextUtils.isEmpty(recognizerResult.getResultString())) {
                    String firstContent = recognizerResult.getResultString();
                    RecResult recResult = (RecResult) GsonUtils.fromJson(firstContent, RecResult.class);
                    String content = null;
                    if(recResult != null) {
                        content = recResult.getResult();
                    }
                    if(TextUtils.isEmpty(content)) {
                        return;
                    }
                    if(mAccSpeechContent) {
                        mContent = content;
                        mAccSpeechContent = false;
                    }
                }
            }

            @Override
            public void onError(final com.iflytek.aipsdk.util.SpeechError speechError) {
                Logs.i(TAG, "识别错误码SpeechError：" + speechError);
                mTimerUtil.stop();
                if(null != mCallBack) mCallBack.stopByError("识别错误码SpeechError：" + speechError);
            }

            @Override
            public void onEvent(final int i, int i1, int i2, Bundle bundle) {
                Logs.i(TAG, "识别事件onEvent:" + i + i1 + i2);
            }

            @Override
            public void onWakeUp(String s, int i) {

            }
        });

        mNlpHelperUtil.addCallBack(new NlpHelperUtil.CallBack() {
            @Override
            public void getResultByBus(String s, int i) {
                Log.i(TAG, "错误码：" + i + ", 内容为：" + s);
                if(0 == i) {
                    if(!TextUtils.isEmpty(s)) {
                        Message msg = Message.obtain();
                        msg.what = 0x102;
                        msg.obj = s;
                        mHandler.sendMessage(msg);
                    } else {
                        if(null != mCallBack) mCallBack.stopByError("语义业务识别成功,返回内容为空");
                    }
                } else {
                    if(null != mCallBack) mCallBack.stopByError("语义业务识别错误码为：" + i + "，请联系维护人员");
                }
            }

            @Override
            public void getResultByChat(String s, int i) {
                Log.i(TAG, "错误码：" + i + ", 内容为：" + s);
                if(0 == i) {
                    if(!TextUtils.isEmpty(s)) {
                        Message msg = Message.obtain();
                        msg.what = 0x103;
                        msg.obj = s;
                        mHandler.sendMessage(msg);
                    } else {
                        if(null != mCallBack) mCallBack.stopByError("语义闲聊识别成功,返回内容为空");
                    }
                } else {
                    if(null != mCallBack) mCallBack.stopByError("语义识别闲聊错误码为：" + i + "，请联系维护人员");
                }
            }
        });
    }

    private void doSpeech() {
        if(null != mCallBack) mCallBack.startSpeech();  // 开启识别

        mAccSpeechContent = true;
        mContent = "";  // 开始识别时置为null
        Log.i(TAG, "startSpeechRecognizer");
        mSpeechUtil.startSpeechRecognizer();
        Logs.i(TAG, "----------------------开始计时喽-----------------------");
        mTimerUtil.start(new TimerUtil.CallBack() {
            @Override
            public void count(long countTime) {
                Log.i(TAG, "当前计数为：" + countTime + "，当前内容：" + mContent);
                if(countTime > 10) {    // 时间超过30s结束流程
                    mTimerUtil.stop();

                    if(!TextUtils.isEmpty(mContent)) {
                        Message msg = Message.obtain();
                        msg.what = 0x101;
                        mHandler.sendMessage(msg);
                    } else {
                        Message msg = Message.obtain();
                        msg.what = 0x104;
                        mHandler.sendMessage(msg);
                    }
                } else {
                    if(!TextUtils.isEmpty(mContent)) {
                        mTimerUtil.stop();      // 停止计时

                        Message msg = Message.obtain();
                        msg.what = 0x101;
                        mHandler.sendMessage(msg);
                    }
                }
            }
        });
    }

    /** 合成以后开启识别
     * @param content*/
    private void speechAfterTts(String content) {
        mIsNeedSpeech = true;
        mTtsInstance.startSynthesizer(content);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x101:
                    mSpeechUtil.stopSpeechRecognizer(); // 停止识别
                    if(null != mCallBack) mCallBack.stopSpeech(mContent);
                    Logs.i(TAG, "请求语义的业务内容为：" + mContent);
                    mNlpHelperUtil.getResultByBus(mContent);
                    break;
                case 0x102:
                    String jsonStrBus = (String) msg.obj;
                    NlpHelperRes nlpHelperRes = (NlpHelperRes) GsonUtils.fromJson(jsonStrBus, NlpHelperRes.class);
                    if(null != nlpHelperRes && !TextUtils.isEmpty(nlpHelperRes.getResult().getMatchInfo().getId()) && -1 != nlpHelperRes.getResult().getRc()) {
                        String id = nlpHelperRes.getResult().getMatchInfo().getId();
                        if(BusinessIdConfig.isBusinessChoosephone(id)) {
                            if(null != mCallBack) mCallBack.business(id);
                            return;
                        }
                    }

                    if(isNoNeedChat) {  // 不走闲聊
                        Logs.i(TAG, "不请求语义闲聊");
                        speechAfterTtsByBus();
                    } else {    // 走闲聊
                        Logs.i(TAG, "请求语义闲聊的内容为：" + mContent);
                        mNlpHelperUtil.getResultByChat(mContent);
                    }
                    break;
                case 0X103:
                    String jsonStrChat = (String) msg.obj;
                    NlpHelperRes nlpHelperResChat = (NlpHelperRes) GsonUtils.fromJson(jsonStrChat, NlpHelperRes.class);
                    if(null != nlpHelperResChat && !TextUtils.isEmpty(nlpHelperResChat.getResult().getMatchInfo().getId()) && -1 != nlpHelperResChat.getResult().getRc()) {
                        String idChat = nlpHelperResChat.getResult().getMatchInfo().getId();
                        Map<String, String> map = new HashMap<>();
                        map.put("konwledgePointId", idChat);
                        OkHttpUtil.get(NetAddressConstant.URL_KNOW, map, okHttpRequestCallBack);
                    } else {
                        if(null != mCallBack) mCallBack.stopByError("语义闲聊请求的id为空或解析错误或chat拒识");
                    }
                    break;
                case 0x104:
                    mSpeechUtil.stopSpeechRecognizer();
                    if(null != mCallBack) mCallBack.stopSpeech(mContent);

                    if(mCurrentStep++ < (MAXSTEP - 1)) {
                        speechAfterTts(getCurrentContent(mCurrentStep));
                    } else {
                        voiceOver();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    /** 结束当前对话流程 */
    private void voiceOver() {
        mTtsInstance.startSynthesizer(getCurrentContent(4));
        if(null != mCallBack) mCallBack.stop();
    }

    /** 获取当前的说话内容 */
    private String getCurrentContent(int mCurrentStep) {
        String temp = "";
        switch (mCurrentStep) {
            case 0:
                temp = contents[0];
                break;
            case 1:
                temp = contents[1];
                break;
            case 2:
                temp = contents[2];
                break;
            case 4:
            default:
                temp = contents[3];
        }
        return temp;
    }

    /** 设置当前对话的步骤 */
    private void speechAfterTtsByBus(String content) {
        mCurrentStep = 1;
        speechAfterTts(content);
    }

    /** 设置当前对话的步骤 */
    private void speechAfterTtsByBus() {
        mCurrentStep = 1;
        speechAfterTts(getCurrentContent(mCurrentStep));
    }


    OkHttpRequestCallback okHttpRequestCallBack = new OkHttpRequestCallback() {
        @Override
        public void onSuccess(String result) {
            Log.i(TAG, "结果：" + result);
            KnowledgeRes knowledgeRes = (KnowledgeRes) GsonUtils.fromJson(result, KnowledgeRes.class);
            if(null != knowledgeRes && !TextUtils.isEmpty(knowledgeRes.getContent())) {
                speechAfterTtsByBus(knowledgeRes.getContent());  // 闲聊的走法
            } else {
                if(null != mCallBack) mCallBack.stopByError("知识库返回的答案不能正常解析，请联系维护人员");
            }
        }

        @Override
        public void onFailure(String error) {
            Log.i(TAG, "错误：" + error);
            if(null != mCallBack) mCallBack.stopByError("访问知识库服务器出现异常，请联系维护人员");
        }
    };

    /** --------------------------暴露的公共方法-------------------------- */

    /** 增加回调监听 */
    public void addCallBack(CallBack callBack) {
        Log.i(TAG, "addCallBack----------------");
        initAbilityListen();  // 初始化能力监听
        mCallBack = callBack;
    }

    /** 移除回调监听 */
    public void removeCallBack() {
        Log.i(TAG, "removeCallBack----------------");
        if(null != mTtsInstance) {
            mTtsInstance.setSynthesizerListener(null);
        }
        if(null != mTimerUtil) {
            mTimerUtil.stop();
        }
        if(null != mSpeechUtil) {
            mSpeechUtil.stopSpeechRecognizer();
            mSpeechUtil.removeCallBack();
        }
        if(null != mNlpHelperUtil) {
            mNlpHelperUtil.removeCallBack();
        }
        if(null != mCallBack) {
            mCallBack = null;
        }
    }

    /** 设置是否走闲聊。false走，true不走 */
    public void setNeedChat(boolean isNoNeedChat) {
        this.isNoNeedChat = isNoNeedChat;
    }

    /** 设置引导词 */
    public void setContents(String[] contents) {
        this.contents = contents;
    }

    /** 开始进入流程-- 只在结束流程的时候用，包括：stop(), StopByError(), business(String id) 回调方法里使用 */
    public void start() {
        Log.i(TAG, "start----------------");
        if(null != mCallBack) mCallBack.start();
        mCurrentStep = 0;
        speechAfterTts(getCurrentContent(mCurrentStep));
    }

    /** 合成之后，开启业务，需要外部使用。慎用 */
    public void startAfterTts(String content) {
        Log.i(TAG, "startAfterTts----------------");
        if(null != mCallBack) mCallBack.start();
        mCurrentStep = 1;
        speechAfterTts(content);
    }

}
