package com.xh.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;

/**
 * 手机信息收集 添加权限 <uses-permission
 * android:name="android.permission.READ_PHONE_STATE" /> <!-- 在SDCard中创建与删除文件权限
 * --> <uses-permission
 * android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"/> <!--
 * 往SDCard写入数据权限 --> <uses-permission
 * android:name="android.permission.WRITE_EXTERNAL_STORAGE"/> <uses-permission
 * android:name="android.permission.READ_PHONE_STATE" /> <uses-permission
 * android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"/>
 * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
 * <uses-permission
 * android:name="android.permission.INTERNET"></uses-permission> <!-- wifi权限 -->
 * <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
 * <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" /> <!--
 * 查询网络状态权限 --> <uses-permission
 * android:name="android.permission.ACCESS_NETWORK_STATE" />
 */
public class XhPhoneInformation {
	/**
	 * 手机屏幕高度
	 */
	public static int height = 0;
	/**
	 * 手机屏幕宽度
	 */
	public static int width = 0;
	/**
	 * 手机宽度上的像素
	 */
	public static int widthPixels = 0;
	/**
	 * 手机高度上的像素
	 */
	public static int heightPixels = 0;
	/**
	 * 手机IMEI 设备ID
	 */
	public static String IMEI = "";
	/**
	 * 手机 IESI 订阅者ID
	 */
	public static String IESI = "";
	/**
	 * 手机型号 MODEL
	 */
	public static String MODEL = "";
	/**
	 * 手机号码 有的能拿到，有的不可以，这个要看生产商是否注册了
	 */
	public static String numer = "";
	/**
	 * sdk版本
	 */
	public static String sdk = "";
	/**
	 * 系统版本号
	 */
	public static String VERSION_RELEASE = "";
	/**
	 * The name of the underlying board, like "goldfish".
	 */
	public static String BOARD = "";
	/**
	 * The system bootloader version number.
	 */
	public static String BOOTLOADER = "";
	/**
	 * The brand (e.g., carrier) the software is customized for, if any.
	 */
	public static String BRAND = "";
	/**
	 * The name of the instruction set (CPU type + ABI convention) of native
	 * code.
	 */
	public static String CPU_ABI = "";
	/**
	 * The name of the second instruction set (CPU type + ABI convention) of
	 * native code.
	 */
	public static String CPU_ABI2 = "";
	/**
	 * The name of the industrial design.
	 */
	public static String DEVICE = "";
	/**
	 * A build ID string meant for displaying to the user
	 */
	public static String DISPLAY = "";
	/**
	 * A string that uniquely identifies this build.
	 */
	public static String FINGERPRINT = "";

	/**
	 * The name of the hardware (from the kernel command line or /proc).
	 */
	public static String HARDWARE = "";
	public static String HOST = "";
	/**
	 * Either a changelist number, or a label like "M4-rc20".
	 */
	public static String ID = "";
	/**
	 * The manufacturer of the product/hardware.
	 */
	public static String MANUFACTURER = "";
	/**
	 * The name of the overall product.
	 */
	public static String PRODUCT = "";
	/**
	 * The radio firmware version number.
	 */
	public static String RADIO = "";
	/**
	 * A hardware serial number, if available.
	 */
	public static String SERIAL = "";
	/**
	 * Comma-separated tags describing the build, like "unsigned,debug".
	 */
	public static String TAGS = "";
	public static long TIME = 0l;
	/**
	 * The type of build, like "user" or "eng".
	 */
	public static String TYPE = "";
	/**
	 * Value used for when a build property is unknow
	 */
	public static String UNKNOWN = "";
	public static String USER = "";
	/**
	 * 手机MAC地址
	 */
	public static String MAC = "";
	/**
	 * cpu 型号
	 */
	public static String CPU_MODE = "";
	/**
	 * cpu频率
	 */
	public static String CPU_FREQUENCY = "";
	/**
	 * 系统总内存 单位为kb
	 */
	public static long TOTAL_RAM = 0l;
	/**
	 * 系统剩余内存 单位为kb
	 */
	public static long AVAIL_RAM = 0l;
	/**
	 * SDCARD总内存 单位为kb
	 */
	public static long TOTAL_SDCARD = 0l;
	/**
	 * SDCARD剩余内存单位为kb
	 */
	public static long AVAIL_SDCARD = 0l;

	/**
	 * 总内存
	 */
	public static long TOTAL_MEMORY = 0l;

	/**
	 * 总剩余内存
	 */
	public static long TOTAL_REMAIN_MEMORY = 0l;
	/**
	 * 像素密度
	 */
	public float density;
	/**
	 * 密度dpi
	 */
	public float densityDpi;

	public float scaledDensity;

	public static XhPhoneInformation xhPhoneInformation;
	private final static String TAG = XhPhoneInformation.class.getName();

	/**
	 * 构造方法私有化，不需创造对象
	 */
	private XhPhoneInformation(Activity activity) {
		// TODO Auto-generated constructor stub
		getPhonWidthAndHeigth(activity);
		getPixel(activity);
		getInfo(activity);
		getPhoneInformation();
		getMacAddress(activity);
		getCpuInfo();
		getTotalMemory(activity);
		getSDCARD();
		getRAM();
	}

	/**
	 * app初始化时候调用
	 * 
	 * @param activity
	 */
	public static XhPhoneInformation initialize(Activity activity) {
		if (xhPhoneInformation != null)
			return xhPhoneInformation;
		synchronized (TAG) {
			if (xhPhoneInformation != null)
				return xhPhoneInformation;
			xhPhoneInformation = new XhPhoneInformation(activity);
		}
		return xhPhoneInformation;
	}

	/**
	 * 获取总内存
	 */
	public void getRAM() {
		// TODO Auto-generated method stub
		File path2 = Environment.getDataDirectory();
		StatFs stat2 = new StatFs(path2.getPath());
		@SuppressWarnings("deprecation")
		long blockSize2 = stat2.getBlockSize();
		@SuppressWarnings("deprecation")
		long totalBlocks2 = stat2.getBlockCount();
		@SuppressWarnings("deprecation")
		long availableBlocks2 = stat2.getAvailableBlocks();

		long totalSize2 = totalBlocks2 * blockSize2;
		long availSize2 = availableBlocks2 * blockSize2;

		TOTAL_MEMORY = totalSize2;
		TOTAL_REMAIN_MEMORY = availSize2;
	}

	/**
	 * 获sdc内存
	 */
	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	public void getSDCARD() {
		// 得到文件系统的信息：存储块大小，总的存储块数量，可用存储块数量
		// 获取sd卡空间
		// 存储设备会被分为若干个区块
		// 每个区块的大小 * 区块总数 = 存储设备的总大小
		// 每个区块的大小 * 可用区块的数量 = 存储设备可用大小
		File path = Environment.getExternalStorageDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize;
		long totalBlocks;
		long availableBlocks;
		// 由于API18（Android4.3）以后getBlockSize过时并且改为了getBlockSizeLong
		// 因此这里需要根据版本号来使用那一套API
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
			blockSize = stat.getBlockSizeLong();
			totalBlocks = stat.getBlockCountLong();
			availableBlocks = stat.getAvailableBlocksLong();
		} else {
			blockSize = stat.getBlockSize();
			totalBlocks = stat.getBlockCount();
			availableBlocks = stat.getAvailableBlocks();
		}
		/**
		 * 内存总大小
		 * 
		 */
		TOTAL_SDCARD = blockSize * totalBlocks;
		/**
		 * 内存可用大小
		 */
		AVAIL_SDCARD = blockSize * availableBlocks;
	}

	/**
	 * 获取系统内存
	 */
	public void getTotalMemory(Activity activity) {
		ActivityManager mActivityManager = (ActivityManager) activity
				.getSystemService(Context.ACTIVITY_SERVICE);
		ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
		mActivityManager.getMemoryInfo(mi);
		long mTotalMem = 0;
		long mAvailMem = mi.availMem;
		String str1 = "/proc/meminfo";
		String str2;
		String[] arrayOfString;
		try {
			FileReader localFileReader = new FileReader(str1);
			BufferedReader localBufferedReader = new BufferedReader(
					localFileReader, 8192);
			str2 = localBufferedReader.readLine();
			arrayOfString = str2.split("\\s+");
			mTotalMem = Integer.valueOf(arrayOfString[1]).intValue() * 1024;
			localBufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		TOTAL_RAM = mTotalMem;
		AVAIL_RAM = mAvailMem;
	}

	/**
	 * 手机CPU信息
	 */
	private void getCpuInfo() {
		String str1 = "/proc/cpuinfo";
		String str2 = "";
		String[] arrayOfString;
		try {
			FileReader fr = new FileReader(str1);
			BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
			str2 = localBufferedReader.readLine();
			arrayOfString = str2.split("\\s+");
			for (int i = 2; i < arrayOfString.length; i++) {
				CPU_MODE = CPU_MODE + arrayOfString[i] + " ";
			}
			str2 = localBufferedReader.readLine();
			arrayOfString = str2.split("\\s+");
			CPU_FREQUENCY += arrayOfString[2];
			localBufferedReader.close();
		} catch (IOException e) {
		}
	}

	/**
	 * 获取手机MAC地址
	 */
	public void getMacAddress(Context activity) {
		WifiManager wifiManager = (WifiManager) activity
				.getSystemService(Context.WIFI_SERVICE);
		if (wifiManager == null)
			return;
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		if (wifiInfo == null)
			return;
		MAC = wifiInfo.getMacAddress();
	}

	/**
	 * 获取手机设备信息
	 */
	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	private void getPhoneInformation() {
		MODEL = Build.MODEL; // 手机型号
		sdk = android.os.Build.VERSION.SDK;
		VERSION_RELEASE = Build.VERSION.RELEASE;
		BOARD = Build.BOARD;
		BOOTLOADER = Build.BOOTLOADER;
		BRAND = Build.BRAND;
		CPU_ABI = Build.CPU_ABI;
		CPU_ABI2 = Build.CPU_ABI2;
		DEVICE = Build.DEVICE;
		DISPLAY = Build.DISPLAY;
		FINGERPRINT = Build.FINGERPRINT;
		HARDWARE = Build.HARDWARE;
		HOST = Build.HOST;
		ID = Build.ID;
		MANUFACTURER = Build.MANUFACTURER;
		PRODUCT = Build.PRODUCT;
		RADIO = Build.RADIO;
		SERIAL = Build.SERIAL;
		TAGS = Build.TAGS;
		TIME = Build.TIME;
		TYPE = Build.TYPE;
		UNKNOWN = Build.UNKNOWN;
		USER = Build.USER;
	}

	/**
	 * 获取手机宽度和高度
	 * 
	 * @param activity
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private void getPhonWidthAndHeigth(Activity activity) {
		Display display = activity.getWindowManager().getDefaultDisplay();
		height = display.getHeight();
		width = display.getWidth();
	}

	/**
	 * 获取手机像素
	 * 
	 * @param activity
	 * @return
	 */
	private void getPixel(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		widthPixels = dm.widthPixels;
		heightPixels = dm.heightPixels;
		density = dm.density;
		scaledDensity = dm.scaledDensity;
		densityDpi = dm.densityDpi;
	}

	/**
	 * 获取手机标示
	 * 
	 * @param activity
	 */
	private void getInfo(Activity activity) {
		TelephonyManager mTm = (TelephonyManager) activity
				.getSystemService(Activity.TELEPHONY_SERVICE);
		if (mTm == null)
			return;
		IMEI = mTm.getDeviceId();
		IESI = mTm.getSubscriberId();
		numer = mTm.getLine1Number();
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素) xh 2017-2-15 下午6:02:54
	 * 
	 * @param context
	 * @param dpValue
	 * @return
	 */
	public int dip2px(float dpValue) {
		return (int) (dpValue * density + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp xh 2017-2-15 下午6:02:59
	 * 
	 * @param context
	 * @param pxValue
	 * @return
	 */
	public int px2dip(float pxValue) {
		return (int) (pxValue / density + 0.5f);
	}

	/**
	 * 
	 * lhl 2017-12-19 下午5:02:11 说明：像素转为sp
	 * 
	 * @param pxValue
	 * @return int
	 */
	public int px2sp(float pxValue) {
		return (int) (pxValue / scaledDensity + 0.5f);
	}

	/**
	 * 
	 * lhl 2017-12-19 下午5:02:28 说明： sp转为像素
	 * 
	 * @param spValue
	 * @return int
	 */
	public int sp2px(float spValue) {
		return (int) (spValue * scaledDensity + 0.5f);
	}
}