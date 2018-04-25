package com.xh.hook;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import android.app.Application;
import android.app.Instrumentation;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ServiceInfo;
import android.os.Build;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;

import com.xh.reflect.FieldManager;
import com.xh.reflect.MethodManager;
import com.xh.util.ContentManager;
import com.xh.util.XhLog;

/**
 * Hook com.xh.hook 2018 2018-4-4 下午3:13:13 instructions：activityManageService钩子
 * author:liuhuiliang email:825378291@qq.com
 **/

public class AMSHook implements Callback {
	public String package_name;
	public String hook_activity;
	public String hook_service;
	public List<String> register_activities;
	public List<String> register_services;
	public List<Intent> start_service = new Vector<Intent>();
	public Application application;
	private static AMSHook mHook;
	private static int CREATE_SERVICE;
	private static int LAUNCH_ACTIVITY;
	static {
		try {
			Class<?> clazz = Class.forName("android.app.ActivityThread$H");
			Field launch_activity_field = clazz.getField("LAUNCH_ACTIVITY");
			Field create_service_field = clazz.getField("CREATE_SERVICE");
			Field bind_service_field = clazz.getField("CREATE_SERVICE");
			LAUNCH_ACTIVITY = launch_activity_field.getInt(null);
			CREATE_SERVICE = create_service_field.getInt(null);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * 2018/4/16 10:48 annotation：初始化钩子 author：liuhuiliang email
	 * ：825378291@qq.com
	 * 
	 * 
	 */
	public static void init() {
		if (mHook == null) {
			synchronized (AMSHook.class) {
				if (mHook == null)
					mHook = new AMSHook();
			}
		}
	}

	private AMSHook() {
		// TODO Auto-generated constructor stub
		application = ContentManager.getManager().getApplication();
		PackageManager packageManager = application.getPackageManager();
		register_activities = new ArrayList<>();
		register_services = new ArrayList<>();
		package_name = application.getPackageName();
		try {
			PackageInfo info = packageManager
					.getPackageInfo(package_name, PackageManager.GET_ACTIVITIES
							| PackageManager.GET_SERVICES);
			ActivityInfo[] activityInfos = info.activities;
			if (activityInfos != null && activityInfos.length > 0) {
				for (int i = 0; i < activityInfos.length; i++) {
					String activity_name = activityInfos[i].name;
					if (i == 0)
						hook_activity = activity_name;
					register_activities.add(activity_name);
				}
			}
			ServiceInfo[] serviceInfos = info.services;
			if (serviceInfos != null && serviceInfos.length > 0) {
				for (int i = 0; i < serviceInfos.length; i++) {
					String service_name = serviceInfos[i].name;
					if (i == 0)
						hook_service = service_name;
					register_services.add(service_name);
				}

			}
			register_hook();
			hookLaunchActivity();
			hookInstrumentation();
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void hookInstrumentation() {
		// TODO Auto-generated method stub
		try {
			Class<?> activityThread = Class
					.forName("android.app.ActivityThread");
			Method currentActivityThread = activityThread
					.getDeclaredMethod("currentActivityThread");
			currentActivityThread.setAccessible(true);
			// 获取主线程对象
			Object activityThreadObject = currentActivityThread.invoke(null);
			// 获取Instrumentation字段
			Field mInstrumentation = activityThread
					.getDeclaredField("mInstrumentation");
			mInstrumentation.setAccessible(true);
			Instrumentation instrumentation = (Instrumentation) mInstrumentation
					.get(activityThreadObject);
			IInstrumentation iInstrumentation = (IInstrumentation) Proxy
					.newProxyInstance(application.getClassLoader(),
							CustomInstrumentation.class.getInterfaces(),
							new IInstrumentationHandle(instrumentation));
			CustomInstrumentation customInstrumentation = new CustomInstrumentation(
					iInstrumentation);
			// 替换掉原来的,就是把系统的instrumentation替换为自己的Instrumentation对象
			mInstrumentation.set(activityThreadObject, customInstrumentation);
			XhLog.d("[app]", "Hook Instrumentation成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 2018 2018-4-4 下午4:13:32 annotation：注册钩子,拦截ams处理事项 author：liuhuiliang
	 * email ：825378291@qq.com void
	 */
	private void register_hook() {
		// TODO Auto-generated method stub
		try {
			if (Build.VERSION.SDK_INT < 26.) {
				Class clz = Class.forName("android.app.ActivityManagerNative");
				Object gDefault = getFiledValue(clz, "gDefault", null);
				Class singleton = Class.forName("android.util.Singleton");
				Field mInstance = getField(singleton, "mInstance");
				Object ams = mInstance.get(gDefault);
				ams = Proxy.newProxyInstance(application.getClassLoader(), ams
						.getClass().getInterfaces(), new HookInvocationHandler(
						this, ams));
				mInstance.set(gDefault, ams);
			} else {
				Class clz = Class.forName("android.app.ActivityThread");
				Method getServiceMethod = MethodManager.method(clz,
						"getService");
				Object iActivityManager = getServiceMethod.invoke(null);
				Object iActivityManagerSingleton = FieldManager.get_field(null,
						FieldManager.field(clz, "IActivityManagerSingleton"));
				iActivityManager = Proxy.newProxyInstance(application
						.getClassLoader(), new Class[] { Class
						.forName("android.app.IActivityManager") },
						new HookInvocationHandler(this, iActivityManager));
				FieldManager.set_field(iActivityManagerSingleton, FieldManager
						.field(Class.forName("android.util.Singleton"),
								"mInstance"), iActivityManager);
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * 
	 * 2018 2018-4-4 下午4:15:42 annotation：替换handler回调 author：liuhuiliang email
	 * ：825378291@qq.com void
	 */
	private void hookLaunchActivity() {
		// TODO Auto-generated method stub
		try {
			Class activity_thread_class = Class
					.forName("android.app.ActivityThread");
			Object activity_thread = getFiledValue(activity_thread_class,
					"sCurrentActivityThread", null);
			Handler mH = (Handler) getFiledValue(activity_thread_class, "mH",
					activity_thread);
			Field call_back = getField(Handler.class, "mCallback");
			call_back.set(mH, this);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private Object getFiledValue(Class clz, String field_name, Object object)
			throws NoSuchFieldException, IllegalAccessException,
			IllegalArgumentException {
		Field field = getField(clz, field_name);
		return field.get(object);
	}

	private Field getField(Class clz, String field_name)
			throws NoSuchFieldException {
		Field field = clz.getDeclaredField(field_name);
		if (!field.isAccessible())
			field.setAccessible(true);
		return field;
	}

	private Object invoke(Class clz, String method_name, Class[] type,
			Object receiver, Object[] args) throws NoSuchMethodException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Method method = clz.getDeclaredMethod(method_name, type);
		if (!method.isAccessible())
			method.setAccessible(true);
		return method.invoke(receiver, args);
	}

	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		if (msg.what == LAUNCH_ACTIVITY || msg.what == CREATE_SERVICE) {
			handleLaunchActivity(msg);
		}
		return false;
	}

	/**
	 * 
	 * 2018 2018-4-4 下午4:23:05 annotation：启动activity author：liuhuiliang email
	 * ：825378291@qq.com
	 * 
	 * @param msg
	 *            void
	 */
	private void handleLaunchActivity(Message msg) {
		// TODO Auto-generated method stub
		try {
			Object obj = msg.obj;
			Class obj_class = obj.getClass();
			String obj_class_name = obj_class.getName();
			if ("android.app.ActivityThread$CreateServiceData"
					.equals(obj_class_name)) {// 服务
				Field service_info_field = getField(obj_class, "info");
				ServiceInfo info = (ServiceInfo) service_info_field.get(obj);
				if (start_service.size() > 0) {
					info.name = start_service.get(0).getComponent()
							.getClassName();
					start_service.remove(0);
					service_info_field.set(obj, info);
				}
			} else if ("android.app.ActivityThread$ActivityClientRecord"
					.equals(obj_class_name)) {// activity
				Intent proxyIntent = (Intent) getFiledValue(obj.getClass(),
						"intent", obj);
				// 拿到之前真实要被启动的Intent 然后把Intent换掉
				Intent originallyIntent = proxyIntent
						.getParcelableExtra("originallyIntent");
				if (originallyIntent == null) {
					return;
				}

				proxyIntent.setComponent(originallyIntent.getComponent());
			}
			// android.app.ActivityThread$CreateServiceData
			// android.app.ActivityThread$ActivityClientRecord

			// todo:兼容AppCompatActivity
			Class<?> forName = Class.forName("android.app.ActivityThread");
			Object activityThread = getFiledValue(forName,
					"sCurrentActivityThread", null);
			Class activity_thread_class = activityThread.getClass();
			Object iPackageManager = invoke(activity_thread_class,
					"getPackageManager", null, activityThread, null);
			PackageManagerHandler handler = new PackageManagerHandler(
					iPackageManager);
			Class<?> iPackageManagerIntercept = Class
					.forName("android.content.pm.IPackageManager");
			Object proxy = Proxy.newProxyInstance(Thread.currentThread()
					.getContextClassLoader(),
					new Class<?>[] { iPackageManagerIntercept }, handler);
			// 获取 sPackageManager 属性
			Field iPackageManagerField = getField(activity_thread_class,
					"sPackageManager");
			iPackageManagerField.set(activityThread, proxy);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private class PackageManagerHandler implements InvocationHandler {
		private Object mActivityManagerObject;

		PackageManagerHandler(Object mActivityManagerObject) {
			this.mActivityManagerObject = mActivityManagerObject;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable {
			String method_name = method.getName();
			if ("getActivityInfo".equals(method_name)) {
				ComponentName componentName = new ComponentName(package_name,
						hook_activity);
				args[0] = componentName;
			} else if ("getServiceInfo".equals(method_name)) {
				ComponentName componentName = new ComponentName(package_name,
						hook_service);
				args[0] = componentName;
			}
			return method.invoke(mActivityManagerObject, args);
		}
	}
}
