package com.yys.telecomrobot.utils;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

import java.lang.reflect.Field;
import java.util.Timer;
import java.util.TimerTask;


/**
 * created by Wangyehan
 *
 * @desc 媒体播放工具类
 */

public class MediaUtil {

    /**
     * 提示音播放工具类
     *
     * @param ctx       上下文
     * @param soundPath 音频路径
     */
    public static void playSound(Context ctx, String soundPath, boolean isRepeat) {
        MediaPlayer player;
        try {
            AssetManager manager = ctx.getAssets();
            AssetFileDescriptor fileDescriptor = manager.openFd(soundPath);
            player = new MediaPlayer();
            player.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength());

            player.setLooping(isRepeat);
            player.prepare();
            player.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 播放res/raw文件夹下的提示音
     *
     * @param ctx
     * @param rawSourceId
     */
    public static void playRaw(Context ctx, int rawSourceId) {

        Uri notification = Uri.parse("android.resource://" + ctx.getPackageName() + "/" + rawSourceId);
        final Ringtone ringtone = RingtoneManager.getRingtone(ctx, notification);
        ringtone.play();


    }

    public static void playRaw(Context ctx, int rawSourceId, long period) {


        Uri notification = Uri.parse("android.resource://" + ctx.getPackageName() + "/" + rawSourceId);
        final Ringtone ringtone = RingtoneManager.getRingtone(ctx, notification);

        ringtone.setStreamType(AudioManager.STREAM_RING);//因为rt.stop()使得MediaPlayer置null,所以要重新创建（具体看源码）
        setRingtoneRepeat(ringtone);//设置重复提醒

        ringtone.play();


        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                ringtone.stop();
            }
        }, period);

    }


    private static void setRingtoneRepeat(Ringtone ringtone) {
        Class<Ringtone> clazz = Ringtone.class;
        try {
            Field field = clazz.getDeclaredField("mLocalPlayer");//返回一个 Field 对象，它反映此 Class 对象所表示的类或接口的指定公共成员字段（※这里要进源码查看属性字段）
            field.setAccessible(true);
            MediaPlayer target = (MediaPlayer) field.get(ringtone);//返回指定对象上此 Field 表示的字段的值
            target.setLooping(true); // 设置循环
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }


}
