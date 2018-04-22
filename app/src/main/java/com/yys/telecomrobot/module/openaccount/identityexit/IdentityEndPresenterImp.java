package com.yys.telecomrobot.module.openaccount.identityexit;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;

import com.cmcc.nativepackage.IDCard;
import com.ist.nativepackage.Fkj;
import com.ist.nativepackage.RR;
import com.yys.telecomrobot.app.G;
import com.yys.telecomrobot.app.MessageConstant;
import com.yys.telecomrobot.utils.LogUtils;
import com.yys.telecomrobot.utils.ThreadPool;
import com.yys.telecomrobot.utils.UiUtils;

import static com.yys.telecomrobot.app.G.IDCARDINFO;
import static com.yys.telecomrobot.app.G.IMGS;

/**
 * Created by yltang3 on 2017/11/22.
 */

public class IdentityEndPresenterImp implements IdentityEndPresenter {

    IdentityEndView mIdentityEndView;
    Context mContext;
    String mCertcode;

    CountDownTimer mCountDownTimer;

    public IdentityEndPresenterImp(IdentityEndView identityEndView, Context context, String certcode) {
        this.mIdentityEndView = identityEndView;
        this.mContext = context;
        this.mCertcode = certcode;

        mCountDownTimer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mIdentityEndView.onTick(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                UiUtils.showToast("刷身份证时间到了，请在回收箱取卡");
                moveCardTo3ByChildThread();
                mIdentityEndView.onFinish();
            }
        };
    }

    @Override
    public void brushIdcard() {
        mStop = false;
        RR.SetLedPower(3, 1);   // 开启身份证灯
        ThreadPool.getInstance().getProcessorsPools().execute(mBrushRunable);
        mCountDownTimer.start();
    }

    @Override
    public void stopBrushCard() {
        mStop = true;   // 结束线程标志
        RR.SetLedPower(3, 0);   // 关闭身份证灯
        mCountDownTimer.cancel();   // 结束计时
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {
        stopBrushCard();
        IDCard.closeIDCard();   // 身份证串口解锁，防止异常崩溃
    }

    private boolean mStop = false;  //停止

    private Runnable mBrushRunable = new Runnable() {
        @Override
        public void run() {
            String[] idCards = new String[10];
            byte[] imgs = new byte[54000];
            int ret = IDCard.openIDCard(0, "", "");
            if (ret != 0) { //开启IdCard
                LogUtils.i(G.TAG, "刷身份能力打开失败");
                IDCard.closeIDCard();
                mCountDownTimer.cancel();
                mIdentityEndView.onFail();
                return;
            }
            ret = IDCard.initialIDCard();
            if(ret != 0) {
                LogUtils.i(G.TAG, "初始化刷身份证能力失败");
                IDCard.closeIDCard();
                mCountDownTimer.cancel();
                mIdentityEndView.onFail();
                return;
            }
            LogUtils.i(G.TAG, "刷身份能力打开成功");
            while (!mStop) {
                ret = IDCard.getIdCardInfoNew(idCards, imgs);
                if (ret != 0) {
                    SystemClock.sleep(50);
                } else {
                    IDCard.closeIDCard();      //关闭IdCard
                    LogUtils.i(G.TAG, "获取到身份证信息");

                    // 调取移动接口判断身份证是否有手机卡
                    Message msgI = Message.obtain();
                    msgI.what = 0x101;
                    Bundle data = new Bundle();
                    data.putStringArray(IDCARDINFO, idCards);
                    data.putByteArray(IMGS, imgs);
                    msgI.setData(data);
                    handler.sendMessage(msgI);
                    return;
                }
            }
            IDCard.closeIDCard();
        }
    };

    Handler handler= new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x101:
                    stopBrushCard();
                    Bundle data = msg.getData();
                    String[] idcards = data.getStringArray(IDCARDINFO);
                    byte[] imgs = data.getByteArray(IMGS);
                    StringBuffer sb = new StringBuffer();
                    sb.append(idcards[0]).append(",").append(idcards[1]).append(",").append(idcards[2]).append(",").append(idcards[3]).append(",").append(idcards[4]).append(",")
                            .append(idcards[5]).append(",").append(idcards[6]).append(",").append(idcards[7]).append(",").append(idcards[8]);
                    LogUtils.i(G.TAG, "当前身份证信息为：" + sb.toString());

                    comparisonIdcard(idcards[5]);
                    break;
                default:
                    break;
            }
        }
    };

    int mLimitNum = 3;  // 倒计时次数

    /**
     * 进行身份证校验
     */
    private void comparisonIdcard(String idcard) {
        if(mCertcode.equals(idcard)) {  // 校验成功
            moveCardTo4ByChildThread();
            mIdentityEndView.brushcardSucc();
        } else {
            if(--mLimitNum <= 0) {
                LogUtils.i(G.TAG, "刷身份证次数用完，退回首页");
                UiUtils.showToast("刷身份证次数用完，请从回收箱取卡");
                moveCardTo3ByChildThread();
                mIdentityEndView.brushcardFail();
            } else {
                UiUtils.showToast("请刷本人身份证，进行取卡");
                brushIdcard();
            }
        }
    }

    /** 将卡移动至回收箱 */
    private void moveCardTo3ByChildThread() {
        ThreadPool.getInstance().getProcessorsPools().execute(new Runnable() {
            @Override
            public void run() {
                int ret = Fkj.init(0);
                if (ret != 0) {
                    LogUtils.i(G.TAG, "SIM卡成功移至回收箱失败");
                    UiUtils.showToast(MessageConstant.FKJ_ERROR);
                    return;
                }
                ret = Fkj.moveCard(0, 3);     // 移动SIM卡到回收舱
                if(ret != 0) {
                    LogUtils.i(G.TAG, "SIM卡成功移至回收箱失败");
                    UiUtils.showToast(MessageConstant.FKJ_ERROR);
                    return;
                }
                LogUtils.i(G.TAG, "SIM卡成功移至回收箱");
                ret = Fkj.uninit();
            }
        });
    }

    /** 将卡移动至出卡口 */
    private void moveCardTo4ByChildThread() {
        ThreadPool.getInstance().getProcessorsPools().execute(new Runnable() {
            @Override
            public void run() {
                int ret = Fkj.init(0);
                if (ret != 0) {
                    LogUtils.i(G.TAG, "SIM卡成功移至出卡口失败");
                    UiUtils.showToast(MessageConstant.FKJ_ERROR);
                    return;
                }
                ret = Fkj.moveCard(0, 4);     // 移动SIM卡到回收舱
                if(ret != 0) {
                    LogUtils.i(G.TAG, "SIM卡成功移至出卡口失败");
                    UiUtils.showToast(MessageConstant.FKJ_ERROR);
                    return;
                }
                LogUtils.i(G.TAG, "SIM卡成功移至出卡口");
                ret = Fkj.uninit();
            }
        });
    }
}
