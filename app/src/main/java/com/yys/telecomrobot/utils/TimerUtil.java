package com.yys.telecomrobot.utils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/8/22.
 */

public class TimerUtil {

    public interface CallBack {
        void count(long countTime);
    }

    private static TimerUtil mInstance;

    public static TimerUtil getInstance() {
        if (null == mInstance) {
            synchronized (TimerUtil.class) {
                if (null == mInstance) {
                    mInstance = new TimerUtil();
                }
            }
        }
        return mInstance;
    }

    /** 私有构造方法 */
    private TimerUtil() {}


    private long mCountTime;    // 开始计时持续时间计数

    /** 开始从零计时 */
    public void start(CallBack callBack) {
        stop(); // 先调用一下结束
        startTimer(callBack);   // 开始计时
    }

    /** 再次计时 */
    public void resume() {
        mIsExecute = true;
    }

    /** 暂停计时 */
    public void pause() {
        mIsExecute = false;
    }

    /** 结束计时 */
    public void stop() {
        stopTimer();
    }


    // ************************** 计时策略 **************************

    private boolean mIsExecute; // 是否执行标志
    Timer mTimer;
    TimerTask mTimerTask;

    /** 开始执行计时
     * @param callBack*/
    private void startTimer(CallBack callBack) {
        mCountTime = 0;      // 计数器归零
        mIsExecute = true;
        mTimer = new Timer();
        mTimerTask = new MyTimerTask(callBack);
        // 每过1s检查一次
        mTimer.schedule(mTimerTask, 1000, 1000);
    }

    private class MyTimerTask extends TimerTask {
        CallBack mCallBack;

        public MyTimerTask(CallBack callBack) {
            mCallBack = callBack;
        }

        @Override
        public void run() {
            if(mIsExecute) {
                mCountTime++;
            }
            mCallBack.count(mCountTime);
        }
    }

    private void stopTimer() {
        mCountTime = 0;     // 计数器归零
        mIsExecute = false;
        if(null != mTimer) {
            mTimer.cancel();
            mTimer = null;
            mTimerTask = null;
        }
    }
}
