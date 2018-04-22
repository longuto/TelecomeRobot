package com.ist.nativepackage;

public class RR {

	static{
		System.loadLibrary("RR");
	}
	
	//初始化线程锁
	public static native int InitLock();
	
	/**
	 * 	鑾峰彇绾㈠鐘舵�
	 * 		杩斿洖鍊硷細   1锛�妫�祴鍒版湁浜�
	 * 			   2锛�娌℃湁妫�祴鍒颁汉
	 */
	public static native int GetIrStatus();
	
	/**
	 * 	璁剧疆浜屼唬璇佹ā鍧楀紑鍏�    1锛�鎵撳紑鐢垫簮   (鍋ュ悍浣撴鍏呯數)
	 * 			      0锛�鍏抽棴鐢垫簮   (鍋ュ悍浣撴涓嶅厖鐢�
	 *  杩斿洖鍊�                             0 鎴愬姛
	 * 		                            鍏朵粬澶辫触
	 */
	
	public static native int SwitchIdcPower(int i );
	
	/**
	 * 	璁剧疆浣撴璁惧寮�叧        1锛�鎵撳紑鐢垫簮
	 * 			      0锛�鍏抽棴鐢垫簮
	 * 杩斿洖鍊�                            0 鎴愬姛
	 * 		                           鍏朵粬澶辫触
	 */
	
	public static native int SwitchHelthCheakPower();
	
	/**
	 * 	璁剧疆LED璁惧寮�叧       led_id  1-4
	 * 			      power   0 鍏抽棴  1寮�
	 * 杩斿洖鍊�                            0 鎴愬姛
	 * 		                           鍏朵粬澶辫触
	 */
	public static native int  SetLedPower(int led_id,int power);
	
	/**
	 * 	鏌ヨLED寮�叧       led_id  1-4
	 * 			     
	 * 杩斿洖鍊�                            1 鎵撳紑  0鍏抽棴  -1 澶辫触
	 * 		                           鍏朵粬澶辫触
	 */ 
	public static native int  GetLedPower(int led_id);

	/*
	 * 		眼睛屏控制接口
	 * @param:		dev 串口设备， cmd 切换图片动画
	 * @return:	    -2：串口打开失败	 
	 *				-1：写入数据失败 
	 *				0：成功 
	 *				1：出错/图片不存在
	 * */
	public static native int eyeExecCmd(String dev, String cmd); 
	
}
