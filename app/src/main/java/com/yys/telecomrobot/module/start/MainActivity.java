package com.yys.telecomrobot.module.start;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.ist.nativepackage.Ultrasonic;
import com.renying.wumai.Xunfly;
import com.yys.telecomrobot.R;
import com.yys.telecomrobot.app.G;
import com.yys.telecomrobot.app.MessageConstant;
import com.yys.telecomrobot.base.BaseTtsActivity;
import com.yys.telecomrobot.control.voice.BusinessIdConfig;
import com.yys.telecomrobot.control.voice.VoiceMethodBus;
import com.yys.telecomrobot.module.openaccount.OpenaccountActivity;
import com.yys.telecomrobot.utils.LogUtils;
import com.yys.telecomrobot.utils.MediaUtil;
import com.yys.telecomrobot.utils.ThreadPool;
import com.yys.telecomrobot.utils.UiUtils;
import com.yys.telecomrobot.utils.WewinsBarUtil;
import com.yys.telecomrobot.widget.LineWaveVoiceView;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseTtsActivity implements MainView {

    @BindView(R.id.iv_tomain)
    ImageView mTomainIv;
    @BindView(R.id.horvoiceview)
    LineWaveVoiceView mLineWaveVoiceView;
    @BindView(R.id.voice_tv)
    TextView mVoiceTv;

    MainPresenter mMainPresenter;
    VoiceMethodBus mVoiceMethodBus;
    Xunfly mXunfly;

    @OnClick({R.id.iv_openaccount, R.id.iv_printinvoice, R.id.iv_servicequery, R.id.btn_exit})
    public void functions(View view) {
        switch (view.getId()) {
            case R.id.iv_openaccount:   // 开户
                LogUtils.i(G.TAG, "开始检测robot硬件能力");
                activeOpenaccount();
                break;
            case R.id.iv_printinvoice:  // 打印发票
                speak(MessageConstant.MAIN_OTHER_ICON);
                break;
            case R.id.iv_servicequery:  // 业务查询
                speak(MessageConstant.MAIN_OTHER_ICON);
                break;
            case R.id.btn_exit:
                mMainPresenter.exitMethod();
                break;
            default:
                break;
        }
    }

    /**
     * 主动点击自助补卡按钮
     */
    private void activeOpenaccount() {
        showProgress("正在检测硬件能力，请稍后...");
        mMainPresenter.checkFkj();  // 检测发卡机能力
    }

    /** 跳转至开户界面 */
    private void toOpenaccountAct(String openaccountFlag) {
        Intent intent = new Intent(this, OpenaccountActivity.class);
        intent.putExtra(G.OPENACCOUNTPACK, openaccountFlag);
        startActivity(intent);
        close();
    }

    @Override
    protected void initPresenter() {
        mMainPresenter = new MainPresenterImp(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mXunfly = new Xunfly();
        mVoiceMethodBus = VoiceMethodBus.getInstance();
        mVoiceMethodBus.addCallBack(new VoiceMethodBus.CallBack() {
            @Override
            public void start() {
                LogUtils.i(G.TAG, "start");
                sleep();
            }

            @Override
            public void stop() {
                LogUtils.i(G.TAG, "stop");
                wakeup();
            }

            @Override
            public void stopByError(String errorMsg) {
                LogUtils.i(G.TAG, "stopByError : " + errorMsg);
//                showVoiceText(errorMsg);
                showVoiceText(MessageConstant.VOICE_METHOD_BYERROR);
                ThreadPool.getInstance().getProcessorsPools().execute(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(500);
                        mHandler.sendEmptyMessage(0x101);
                    }
                });
            }

            @Override
            public void startSpeech() {
                LogUtils.i(G.TAG, "startSpeech");
                startSpeechByAnimation();
            }

            @Override
            public void stopSpeech(String speechMsg) {
                LogUtils.i(G.TAG, "stopSpeech : " + speechMsg);
                stopSpeechByAnimation(speechMsg);
            }

            @Override
            public void business(String id) {
                switch (id) {
                    case "321": // 自助开卡
                        if(isShowDialog()) {
                            voiceStartAfterTtsByContents("请选择需要开卡的套餐");
                        } else {
                            activeOpenaccount();
                            voiceStartByContents();
                        }
                        break;
                    case "322": // 超级日租卡
                        if(isShowDialog()) {
                            toOpenaccountAct(G.OPENACCOUNTSUPERDAY);
                        } else {
                            voiceStartAfterTtsByContents("请选择自助开卡业务");
                        }
                        break;
                    case "323": // 乐享99套餐
                        if(isShowDialog()) {
                            toOpenaccountAct(G.OPENACCOUNTENJOY99);
                        } else {
                            voiceStartAfterTtsByContents("请选择自助开卡业务");
                        }
                        break;
                    case "324": // 乐享199套餐
                        if(isShowDialog()) {
                            toOpenaccountAct(G.OPENACCOUNTENJOY199);
                        } else {
                            voiceStartAfterTtsByContents("请选择自助开卡业务");
                        }
                        break;
                    default:
                        voiceStartByContents();
                        break;
                }
            }

            @Override
            public void volumeChanged(int i, byte[] bytes) {
                float v = i * 1.0f / 70;
                getLineWaveVoiceView().setMaxAmp(v);
            }
        });
        mVoiceMethodBus.setNeedChat(false);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void checkSucc() {
        LogUtils.i(G.TAG, "检测robot能力成功");
        hideProgress();
        showPackageView();
    }

    @Override
    public void checkFail(String msg) {
        LogUtils.i(G.TAG, "检测robot能力失败" + msg);
        hideProgress();
        speak(msg);
    }

    @Override
    public void exitView() {
        close();
        WewinsBarUtil.openBar();
    }


    AlertDialog mDialog;
    /**
     * 展示视图
     */
    private void showPackageView() {
        View view = View.inflate(this, R.layout.dialog_package, null);
        mDialog = new AlertDialog.Builder(this).setView(view).setCancelable(true).create();
        Window window = mDialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.show();

        ImageView superdaycardIv = (ImageView) view.findViewById(R.id.iv_superdaycard);
        ImageView enjoy99cardIv = (ImageView) view.findViewById(R.id.iv_enjoy99card);
        ImageView enjoy199cardIv = (ImageView) view.findViewById(R.id.iv_enjoy199card);
        superdaycardIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toOpenaccountAct(G.OPENACCOUNTSUPERDAY);
            }
        });
        enjoy99cardIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toOpenaccountAct(G.OPENACCOUNTENJOY99);
            }
        });
        enjoy199cardIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toOpenaccountAct(G.OPENACCOUNTENJOY199);
            }
        });
    }

    /**--------------- voice相关设计 --------------*/
    /** 开启识别，包括动画、语音、文本内容 */
    public void startSpeechByAnimation() {
        mLineWaveVoiceView.startRecord();
        MediaUtil.playRaw(UiUtils.getContext(), R.raw.speech);
        mVoiceTv.setText("正在聆听");
        mVoiceTv.setVisibility(View.VISIBLE);
    }

    /** 停止识别，包括动画、文本隐藏 */
    public void stopSpeechByAnimation(String content) {
        mLineWaveVoiceView.stopRecord();
        mVoiceTv.setText(content);
        mVoiceTv.setVisibility(View.VISIBLE);
    }

    /** 展示文本文字 */
    public void showVoiceText(String content) {
        if(TextUtils.isEmpty(content)) {
            return;
        }
        mVoiceTv.setText(content);
        mVoiceTv.setVisibility(View.VISIBLE);
    }

    /** 隐藏文本内容 */
    public void hideVoiceText() {
        mVoiceTv.setVisibility(View.GONE);
    }

    public LineWaveVoiceView getLineWaveVoiceView() {
        return mLineWaveVoiceView;
    }


    private void voiceStartByContents() {
        chooseContentsByBuilder();
        mVoiceMethodBus.start();
    }

    private void voiceStartAfterTtsByContents(String content) {
        chooseContentsByBuilder();
        mVoiceMethodBus.startAfterTts(content);
    }

    /** 选择引导词 */
    private void chooseContentsByBuilder() {
        if(null != mDialog && mDialog.isShowing()) { // 显示
            if(null != mVoiceMethodBus) mVoiceMethodBus.setContents(BusinessIdConfig.PACKCONTENTS);
        } else {
            if(null != mVoiceMethodBus) mVoiceMethodBus.setContents(BusinessIdConfig.BUSINESSCONTENTS);
        }
    }

    /**
     * 是否展示弹出框
     */
    private boolean isShowDialog() {
        if (null != mDialog && mDialog.isShowing()) { // 显示
            return true;
        } else {
            return false;
        }
    }

    /**
     * 语音唤醒
     */
    private void wakeup() {
        mUltrasonicFlag = true;
        ThreadPool.getInstance().getProcessorsPools().execute(new Runnable() {
            @Override
            public void run() {
                final int fd = mXunfly.open();
                if(fd > 0) {
                    mXunfly.wakelistener(fd, new Xunfly.VoiceListener() {
                        @Override public void onAngle(int i) {
                            LogUtils.i(G.TAG, "onAngle: " + i);
                            // 关闭超声波的唤醒
                            mUltrasonicFlag = false;

                            mXunfly.zdwake(fd);
                            mHandler.sendEmptyMessage(0x102);
                        }
                    });
                } else {
                    UiUtils.showToast(MessageConstant.ERROR_BY_WUMAI);
                }
                mXunfly.close(fd);
            }
        });
        ThreadPool.getInstance().getProcessorsPools().execute(new Runnable() {
            @Override
            public void run() {
                while (mUltrasonicFlag) {
                    int distance = Ultrasonic.getDistance();
                    LogUtils.i(G.TAG, "当前距离是：" + distance);
                    if(distance > 40 && distance < 60) {
                        LogUtils.i(G.TAG, "Ultrasonic + wakeup");
                        // 关闭语音的唤醒方式
                        mXunfly.stopLoop();

                        int fd = mXunfly.open();
                        if(fd > 0) {
                            mXunfly.zdwake(fd);
                        } else {
                            UiUtils.showToast(MessageConstant.ERROR_BY_WUMAI);
                        }
                        mXunfly.close(fd);
                        mHandler.sendEmptyMessage(0x102);
                    }
                    SystemClock.sleep(1000);
                }
            }
        });
    }

    private boolean mUltrasonicFlag;

    private void sleep() {
        mXunfly.stopLoop();
        mUltrasonicFlag = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        wakeup();
    }

    @Override
    protected void onStop() {
        super.onStop();
        sleep();
        if(null != mVoiceMethodBus) mVoiceMethodBus.removeCallBack();
        if(null != mXunfly) {
            ThreadPool.getInstance().getProcessorsPools().execute(new Runnable() {
                @Override
                public void run() {
                    int fd = mXunfly.open();
                    if(fd > 0) {
                        mXunfly.reset(fd);
                    } else {
                        UiUtils.showToast(MessageConstant.ERROR_BY_WUMAI);
                    }
                    mXunfly.close(fd);
                }
            });
        }
    }

    Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x101:
                    voiceStartAfterTtsByContents(MessageConstant.VOICE_METHOD_BYERROR);
                    break;
                case 0x102:
                    voiceStartByContents();
                    break;
                default:
                    break;
            }
        }
    };
}
