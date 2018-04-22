package com.renying.tool;

public class SerialPort {
	public static native int Open(String Dev, int Speed, int Databit ,int Stopbit, int so_appid);
	public static native int Open2(String Dev, int Speed, int Databit ,int Stopbit, int lockflag, int so_appid);
	public static native int Write(byte[] Data, int WrtLen);
	public static native int Read(byte[] Buf, int ReadLen); 
	public static native void Close(); 
	
	public static native int OpenLock(String Dev, int Speed, int Databit ,int Stopbit, int so_appid, String lockname);
	public static native void CloseLock(int fd, String lockname); 
	
	
	public static native void SetLogFileFull(String logfile);
	public static native void Log(String log);
	public static native String TailLog(String logfile, int lastBytes);
	
	
	//自环方式打开特定的设备
	//dev={dc,eye,mto,fkj}
	public static native int OpenZH(String Dev);
	public static native int OpenFd(String Dev, int Speed, int Databit ,int Stopbit, int lockflag, int so_appid);
	public static native int WriteFd(int fd, byte[] Data, int WrtLen);
	public static native int ReadFd(int fd, byte[] Buf, int ReadLen); 
	public static native void CloseFd(int fd); 
	public static native int GetFd(); 
	
	//设置RRv31的串口gpio
	public static native String PrepareOpen(String gpio_flag);
	
	static 
	{
		System.loadLibrary("RR");
	}		
}