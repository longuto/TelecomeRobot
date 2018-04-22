package com.ist.nativepackage;

public class Eyes {
	
	public static native int eyeExecCmd(String dev, String cmd); 
	public static native int setScreenDim(String dev,int dim); 
	//Java_com_ist_nativepackage_eyes_eyeExecCmd
	static{
		System.loadLibrary("RR");  
	}


}



