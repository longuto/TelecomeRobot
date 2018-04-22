package com.ist.nativepackage;

public class Hwpen {

	public static native int HwpenCtrl(int hw_way);
	
	static{
		System.loadLibrary("RR");  
	}
}
