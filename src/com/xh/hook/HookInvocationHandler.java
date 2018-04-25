package com.xh.hook;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;

import com.xh.util.XhLog;

/**
 * Hook com.xh.hook 2018 2018-4-4 下午3:52:08 instructions： author:liuhuiliang
 * email:825378291@qq.com
 **/

public class HookInvocationHandler implements InvocationHandler {
	private final static String TAG = "HookInvocationHandler";
	private AMSHook ams_hook;
	private Object ams;

	public HookInvocationHandler(AMSHook ams_hook, Object ams) {
		// TODO Auto-generated constructor stub
		this.ams_hook = ams_hook;
		this.ams = ams;
	}

	@SuppressLint("NewApi")
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		// TODO Auto-generated method stub
		XhLog.e("HookInvocationHandler", method.getName());
		String method_name = method.getName();
		int index = intent_index(args);
		if (index != -1) {
			Intent intent = (Intent) args[index];
			ComponentName component_name = intent.getComponent();
			if (component_name != null) {
				String class_name = component_name.getClassName();
				if (class_name != null && !class_name.isEmpty()) {
					if ("startActivity".equals(method_name)) {
						int activity_index = ams_hook.register_activities
								.indexOf(class_name);
						if (activity_index == -1) {
							args[index] = get_proxy(ams_hook.hook_activity,
									intent);
						}
					} else if ("startService".equals(method_name)) {
						int service_index = ams_hook.register_services
								.indexOf(class_name);
						if (service_index == -1) {
							XhLog.e(TAG, "替换服务的intent");
							ams_hook.start_service.add(intent);
							args[index] = get_proxy(ams_hook.hook_service,
									intent);
						}
					} else if ("bindService".equals(method_name)) {
						int service_index = ams_hook.register_services
								.indexOf(class_name);
						if (service_index == -1) {
							XhLog.e(TAG, "替换服务的intent");
							ams_hook.start_service.add(intent);
							args[index] = get_proxy(ams_hook.hook_service,
									intent);
						}
					} else if ("stopService".equals(method_name)) {
						int service_index = ams_hook.register_services
								.indexOf(class_name);
						if (service_index == -1) {
							XhLog.e(TAG, "替换服务的intent");
							args[index] = get_proxy(ams_hook.hook_service,
									intent);
						}
					}
				}
			}
		} else if (method_name.equals("activityResumed")) {
			// IBinder iBinder = (IBinder) args[0];
			// XhLog.d("[app]", "执行activityResumed方法了,参数toke为" + iBinder);
			// Class<?> clazz = Class.forName("android.app.ActivityThread");
			// Method method1 =
			// clazz.getDeclaredMethod("currentActivityThread");
			// Object object = method1.invoke(null);
			// Method getActivity = clazz.getDeclaredMethod("getActivity",
			// IBinder.class);
			// Activity mActivity = (Activity) getActivity.invoke(object,
			// iBinder);
			// XhLog.e("[app]", "Hook AMS以后:当前的Activity为:" + mActivity);
		} else if (method_name.equals("activityPaused")) {
			// ContentManager contentManager=ContentManager.getManager();
			// Field mActivityField=
			// FieldManager.field(ContentManager.class,"mActivity");
			// mActivityField.set(contentManager,null);
			// XhLog.e("[app]","设置不可见");
		}
		return method.invoke(ams, args);
	}

	private Intent get_proxy(String hook_class, Intent plugin) {
		Intent proxyIntent = new Intent();
		// TODO: 2017/6/20 因为我们调用的Activity没有注册，所以这里我们先偷偷换成已注册。使用一个假的Intent
		XhLog.e("startActivityName=" + plugin.getComponent().getClassName());
		ComponentName componentName = new ComponentName(ams_hook.package_name,
				hook_class);
		proxyIntent.setComponent(componentName);
		// TODO: 2017/6/20 在这里把未注册的Intent先存起来 一会儿我们需要在Handle里取出来用
		proxyIntent.putExtra("originallyIntent", plugin);
		return proxyIntent;
	}

	private int intent_index(Object[] args) {
		if (args != null && args.length > 0)
			for (int i = 0; i < args.length; i++) {
				if (args[i] instanceof Intent)
					return i;
			}
		return -1;
	}
}
