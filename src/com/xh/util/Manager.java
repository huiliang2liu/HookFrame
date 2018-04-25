package com.xh.util;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.List;

import com.xh.circular.CircularAnim;
import com.xh.hook.AMSHook;
import com.xh.hook.ParasActivityXml;
import com.xh.xhapplication.R;

/**
 * HookFrame com.xh.util 2018 2018-4-23 上午9:36:12 instructions：
 * author:liuhuiliang email:825378291@qq.com
 **/

public class Manager {
	private static Manager mManager;
	private List<Class> localResources;// 加载本地资源的activity
	private ParasActivityXml xml;

	public ParasActivityXml getXml() {
		return xml;
	}

	/**
	 * 
	 * 2018 2018-4-23 下午4:06:27 annotation：是否为本地资源 author：liuhuiliang email
	 * ：825378291@qq.com
	 * 
	 * @param localActivity
	 * @return boolean
	 */
	public boolean isLocalActivity(Class localActivity) {
		return localResources.indexOf(localActivity) != -1;
	}

	public static Manager init() {
		if (mManager == null)
			synchronized (Manager.class) {
				if (mManager == null)
					mManager = new Manager();
			}
		return mManager;
	}

	public static Manager getManager() {
		if (mManager == null)
			throw new RuntimeException("you must be init in application");
		return mManager;
	}

	/**
	 * 
	 * 2018 2018-4-23 下午3:42:31 annotation：添加本地资源activity author：liuhuiliang
	 * email ：825378291@qq.com
	 * 
	 * @return Manager
	 */
	public Manager addLocal(Class local) {
		localResources.add(local);
		return this;
	}

	/**
	 * 
	 * 2018 2018-4-23 下午3:42:31 annotation：添加本地资源activity author：liuhuiliang
	 * email ：825378291@qq.com
	 * 
	 * @return Manager
	 */
	public Manager addLocal(List<Class> locals) {
		localResources.addAll(locals);
		return this;
	}

	private Manager() {
		Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(Thread
				.getDefaultUncaughtExceptionHandler()));
		ContentManager.getManager();
		AMSHook.init();
		CircularAnim.init(700, 500, R.color.colorPrimary);
		MainfiestManager.mainfiest();
		localResources = new ArrayList<>();
		xml = new ParasActivityXml();
	}

	public Manager merge(ParasActivityXml xml) {
		this.xml.merge(xml);
		return this;
	}

	private class ExceptionHandler implements UncaughtExceptionHandler {
		private UncaughtExceptionHandler mDefaultUncaughtExceptionHandler;

		public ExceptionHandler(
				UncaughtExceptionHandler defaultUncaughtExceptionHandler) {
			// TODO Auto-generated constructor stub
			mDefaultUncaughtExceptionHandler = defaultUncaughtExceptionHandler;
		}

		@Override
		public void uncaughtException(Thread thread, Throwable ex) {
			// TODO Auto-generated method stub
			ex.printStackTrace();
			if (mDefaultUncaughtExceptionHandler != null)
				mDefaultUncaughtExceptionHandler.uncaughtException(thread, ex);
		}

	}
}
