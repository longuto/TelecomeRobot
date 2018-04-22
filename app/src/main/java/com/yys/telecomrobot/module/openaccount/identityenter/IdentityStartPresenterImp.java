package com.yys.telecomrobot.module.openaccount.identityenter;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;

import com.cmcc.nativepackage.IDCard;
import com.ist.nativepackage.RR;
import com.megvii.licensemanager.Manager;
import com.megvii.livenessdetection.LivenessLicenseManager;
import com.yys.telecomrobot.livenessLib.util.ConUtil;
import com.yys.telecomrobot.utils.LogUtils;
import com.yys.telecomrobot.utils.ThreadPool;

import static com.yys.telecomrobot.app.G.IDCARDINFO;
import static com.yys.telecomrobot.app.G.IMGS;
import static com.yys.telecomrobot.app.G.TAG;

/**
 * Created by yltang3 on 2017/11/20.
 */

public class IdentityStartPresenterImp implements IdentityStartPresenter {

    IdentityStartView mIdentityStartView;
    Context mContext;

    CountDownTimer mCountDownTimer;

    public IdentityStartPresenterImp(IdentityStartView view, Context context) {
        mIdentityStartView = view;
        this.mContext = context;

        mCountDownTimer = new CountDownTimer(20 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mIdentityStartView.onTick(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                mIdentityStartView.onFinish();
            }
        };
    }

    @Override
    public void brushIdcard() {
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

    String uuid;

    private void netWorkWarranty() {
        uuid = ConUtil.getUUIDString(mContext);
        LogUtils.i(TAG, "uuid==" + uuid);
        ThreadPool.getInstance().getProcessorsPools().execute(new Runnable() {
            @Override
            public void run() {
                Manager manager = new Manager(mContext);
                LivenessLicenseManager licenseManager = new LivenessLicenseManager(mContext);
                manager.registerLicenseManager(licenseManager);
                manager.takeLicenseFromNetwork(uuid);
                if (licenseManager.checkCachedLicense() > 0) {
                    LogUtils.i(TAG, "活体监测联网授权成功，uuid=" + uuid);
                    handler.sendEmptyMessage(0x102);
                } else {
                    LogUtils.i(TAG, "活体监测联网授权失败");
                    handler.sendEmptyMessage(0x103);
                }
            }
        });
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

    /** 刷身份证的线程方法 */
    Runnable mBrushRunable = new Runnable() {
        @Override
        public void run() {
            String[] idCards = new String[10];
            byte[] imgs = new byte[54000];
            int ret = IDCard.openIDCard(0, "", "");
            if (ret != 0) { //开启IdCard
                LogUtils.i(TAG, "刷身份能力打开失败");
                IDCard.closeIDCard();
                mCountDownTimer.cancel();
                mIdentityStartView.onFail();
                return;
            }
            ret = IDCard.initialIDCard();
            if(ret != 0) {
                LogUtils.i(TAG, "初始化刷身份证能力失败");
                IDCard.closeIDCard();
                mCountDownTimer.cancel();
                mIdentityStartView.onFail();
                return;
            }
            LogUtils.i(TAG, "刷身份能力打开成功");
            while (!mStop) {
                ret = IDCard.getIdCardInfoNew(idCards, imgs);
                if (ret != 0) {
                    SystemClock.sleep(50);
                } else {
                    IDCard.closeIDCard();      //关闭IdCard
                    LogUtils.i(TAG, "获取到身份证信息");

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
                    LogUtils.i(TAG, "当前身份证信息为：" + sb.toString());

                    mIdentityStartView.brushcardSucc(idcards, imgs);
                    netWorkWarranty();
                    break;
                case 0x102:
                    mIdentityStartView.netWorkSucc(uuid);
                    break;
                case 0x103:
                    mIdentityStartView.netWorkFail();
                    break;
                default:
                    break;
            }
        }
    };
}
