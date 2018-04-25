package com.xh.repair;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

public final class SoLibManager {
	public static final String CPU_ARMEABI = "armeabi";
	public static final String CPU_X86 = "x86";
	public static final String CPU_MIPS = "mips";
	private static final String TAG = SoLibManager.class.getSimpleName();

	/**
	 * So File executor
	 */
	private ExecutorService mSoExecutor = Executors.newCachedThreadPool();
	/**
	 * single instance of the SoLoader
	 */
	private static SoLibManager sInstance = new SoLibManager();
	/**
	 * app's lib dir
	 */
	private static String sNativeLibDir = "";

	private SoLibManager() {
	}

	/**
	 * @return
	 */
	public static SoLibManager getSoLoader() {
		return sInstance;
	}

	private String getCpuName() {
		try {
			FileReader fr = new FileReader("/proc/cpuinfo");
			BufferedReader br = new BufferedReader(fr);
			String text = br.readLine();
			br.close();
			String[] array = text.split(":\\s+", 2);
			if (array.length >= 2) {
				return array[1];
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@SuppressLint("DefaultLocale")
	private String getCpuArch(String cpuName) {
		String cpuArchitect = CPU_ARMEABI;
		if (cpuName.toLowerCase().contains("arm")) {
			cpuArchitect = CPU_ARMEABI;
		} else if (cpuName.toLowerCase().contains("x86")) {
			cpuArchitect = CPU_X86;
		} else if (cpuName.toLowerCase().contains("mips")) {
			cpuArchitect = CPU_MIPS;
		}

		return cpuArchitect;
	}

	/**
	 * copy so lib to specify directory(/data/data/host_pack_name/pluginlib)
	 * 
	 * @param dexPath
	 *            plugin path
	 * @param nativeLibDir
	 *            nativeLibDir
	 */
	public void copyPluginSoLib(Context context, String dexPath,
			String nativeLibDir) {
		String cpuName = getCpuName();
		String cpuArchitect = getCpuArch(cpuName);
		sNativeLibDir = nativeLibDir;
		Log.d(TAG, "cpuArchitect: " + cpuArchitect);
		long start = System.currentTimeMillis();
		try {
			ZipInputStream zipInputStream = new ZipInputStream(
					new FileInputStream(dexPath));
			ZipEntry zipEntry = null;
			while ((zipEntry = zipInputStream.getNextEntry()) != null) {
				String zipEntryName = zipEntry.getName();
				if (zipEntryName.endsWith(".so")
						&& zipEntryName.contains(cpuArchitect)) {
					final long lastModify = zipEntry.getTime();
					if (lastModify == getSoLastModifiedTime(context,
							zipEntryName)) {
						continue;
					}
					FileOutputStream fos = new FileOutputStream(new File(
							sNativeLibDir, zipEntryName.substring(zipEntryName
									.lastIndexOf("/") + 1)));
					byte[] buff = new byte[1024];
					int len = -1;
					while ((len = zipInputStream.read(buff)) > 0) {
						fos.write(buff, 0, len);
					}
					fos.flush();
					fos.close();
					setSoLastModifiedTime(context, zipEntryName, lastModify);
				}
				zipInputStream.closeEntry();
			}
			zipInputStream.close();
		} catch (IOException e) {
		}

		long end = System.currentTimeMillis();
		Log.d(TAG, "### copy so time : " + (end - start) + " ms");
	}

	public static final String PREFERENCE_NAME = "dynamic_load_configs";

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static void setSoLastModifiedTime(Context cxt, String soName,
			long time) {
		SharedPreferences prefs = cxt.getSharedPreferences(PREFERENCE_NAME,
				Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS);
		prefs.edit().putLong(soName, time).apply();
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static long getSoLastModifiedTime(Context cxt, String soName) {
		SharedPreferences prefs = cxt.getSharedPreferences(PREFERENCE_NAME,
				Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS);
		return prefs.getLong(soName, 0);
	}
}