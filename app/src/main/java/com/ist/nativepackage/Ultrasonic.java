package com.ist.nativepackage;

public class Ultrasonic {

	
	public static native int getDistance();

	static{
		System.loadLibrary("RR");  
	}
}
