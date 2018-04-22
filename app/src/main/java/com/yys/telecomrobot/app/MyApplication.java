package com.yys.telecomrobot.app;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.os.Process;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.crashcollect.CrashCollector;
import com.ist.nativepackage.Fkj;
import com.ist.nativepackage.RR;
import com.yys.telecomrobot.utils.LogUtils;
import com.yys.telecomrobot.utils.ThreadPool;
import com.yys.telecomrobot.utils.UiUtils;
import com.yys.telecomrobot.utils.WewinsBarUtil;

import java.io.File;
import java.util.List;

/**
 * Created by yltang3 on 2017/11/16.
 *
 * 自定义应用入口
 */

public class MyApplication extends Application {

    private static Context context;  //上下文
    private static int mainThreadId; //主线程id

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
        mainThreadId = Process.myTid();

        String processName = getProcessName(context, mainThreadId);
        if(null != processName) {
            LogUtils.i(G.TAG, "当前进程名称：" + processName + ", 当前应用包名：" + getPackageName());
            boolean defaultProcess = processName.equals(getPackageName());
            if(defaultProcess) {
                initRobot();    // 初始化机器人相关操作
            }
        }
    }

    /**
     * 初始化机器人操作
     */
    private void initRobot() {
        initMsc();  // 初始化Tts
        initAipsdk();
        initPicFile();
        initCrashCollect(); // 初始化崩溃日志
        initRenying();  // 初始化仁盈机器人
    }

    /**
     * 初始化文件夹
     */
    private void initPicFile() {
        File file = new File(Environment.getExternalStorageDirectory(), G.LIVENESSPICDIR);
        if(!file.exists()) {
            if(!file.mkdirs()) {
                UiUtils.showToast("创建保存活体照片路径失败！");
            }
        }
    }

    /**
     * 初始化私有云sdk
     */
    private void initAipsdk() {
        //ca_path不传则走默认证书
        String params = null;
        params = "ca_path=ca.jet,res=0";
//        params = "ca_path=/sdcard/ca.crt,res=1";
        com.iflytek.aipsdk.common.SpeechUtility.createUtility(this, params);
    }

    /**
     * 初始化任盈机器人
     */
    private void initRenying() {
        WewinsBarUtil.closeBar();

        int initLockErrorCode = RR.InitLock();
        if (initLockErrorCode != 0) {
            UiUtils.showToast("Init robot InitLocak is failed!");
        }
        // 正式环境的代码需打开
        ThreadPool.getInstance().getProcessorsPools().execute(new Runnable() {
            @Override
            public void run() {
                LogUtils.i(G.TAG, "fkj + reset(0) + " + "");
                Fkj.reset(0);
            }
        });
    }

    /**
     * 初始化崩溃日志
     */
    private void initCrashCollect() {
        //日志开关
        CrashCollector.setDebugable(true);
        //初始化崩溃sdk
        CrashCollector.init(this, "58788e2f");
        File file = new File(Environment.getExternalStorageDirectory(), G.CRASHCOLLECTDIR);
        if (!file.exists()) {
            file.mkdirs();
        }
        CrashCollector.setWorkDir(file.getAbsolutePath() + "/");
    }

    /**
     * 初始化tts
     */
    private void initMsc() {
        StringBuffer param = new StringBuffer();
        param.append("appid=55ee4f8b");
        param.append(",");
        param.append("lib_name=robotmsc");
        param.append(",");
        param.append(SpeechConstant.ENGINE_MODE + "=" + SpeechConstant.MODE_MSC);
        SpeechUtility.createUtility(context, param.toString());
    }

    /** 获取当前进程名称 */
    public String getProcessName(Context cxt, int pid) {
        ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo appProgress : runningApps) {
            if (appProgress.pid == pid) {
                return appProgress.processName;
            }
        }
        return null;
    }

    /**
     * 返回上下文
     */
    public static Context getContext() {
        return context;
    }

    /**
     * 获取主线程id
     */
    public static int getUiTid() {
        return mainThreadId;
    }
}
