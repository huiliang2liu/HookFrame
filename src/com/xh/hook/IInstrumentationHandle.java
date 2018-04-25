package com.xh.hook;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.view.ContextThemeWrapper;

import com.xh.annotation.ViewAnnotationParse;
import com.xh.interf.IActivity;
import com.xh.interf.IViewAnnotation;
import com.xh.reflect.FieldManager;
import com.xh.reflect.MethodManager;
import com.xh.repair.AMRP;
import com.xh.repair.Load;
import com.xh.string.StringUtil;
import com.xh.util.ContentManager;
import com.xh.util.Manager;
import com.xh.util.ResourceRecovery;
import com.xh.util.ViewAnnotationImpl;
import com.xh.util.XhLog;

/**
 * 2018/4/16 11:52 instructions： author:liuhuiliang email:825378291@qq.com
 **/

public class IInstrumentationHandle implements InvocationHandler {
	private final static String TAG = "IInstrumentationHandle";
	private Instrumentation mInstrumentation;
	private static Field mResourcesField;
	private static Field mThemeField;
	private static Field mResourcesField1;
	private static Field mThemeField1;
	static {
		try {
			Class contextImplClass = Class.forName("android.app.ContextImpl");
			mResourcesField = FieldManager
					.field(contextImplClass, "mResources");
			mThemeField = FieldManager.field(contextImplClass, "mTheme");
			mResourcesField1 = FieldManager.field(ContextThemeWrapper.class,
					"mResources");
			mThemeField1 = FieldManager.field(ContextThemeWrapper.class,
					"mTheme");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public IInstrumentationHandle(Instrumentation instrumentation) {
		mInstrumentation = instrumentation;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Class[] types = method.getParameterTypes();
		String methodName = method.getName();
		Method mMethod = MethodManager.method(Instrumentation.class,
				methodName, types);
		if (methodName.equals("newActivity")) {
			XhLog.e("newActivity");
			Activity mActivity = (Activity) mMethod.invoke(mInstrumentation,
					args);
			ContentManager contentManager = ContentManager.getManager();
			Field mActivityField = FieldManager.field(ContentManager.class,
					"mActivity");
			mActivityField.set(contentManager, mActivity);
			ParasActivityXml xml = Manager.getManager().getXml();
			if (Manager.getManager().isLocalActivity(mActivity.getClass())) {
				XhLog.e("不需要替换资源加载的activty");
			} else if (mActivity instanceof IActivity) {
				IActivity iActicity = (IActivity) mActivity;
				Load load = Load.getLoad();
				if (load != null) {
					setResources(mActivity,
							load.layout2amrp(iActicity.layoutId()));
				}
			} else {
				XhLog.e("匹配清单文件中的Activity="+mActivity.getClass());
				String packageName = xml
						.class2packageName(mActivity.getClass());
				XhLog.e("获取到包名="+packageName);
				Load load = Load.getLoad();
				if (!StringUtil.isEmpty(packageName) && load != null) {
					setResources(mActivity, load.package2amrp(packageName));
				}
			}
			return mActivity;
		} else if (methodName.equals("callActivityOnCreate")) {
			Activity mActivity = (Activity) args[0];
			if (mActivity instanceof IActivity) {
				IActivity iActicity = (IActivity) mActivity;
				mActivity.setContentView(iActicity.layoutId());
				IViewAnnotation viewAnnotation = new ViewAnnotationImpl(
						mActivity.findViewById(android.R.id.content), iActicity);
				ViewAnnotationParse.parse(iActicity.getClickReceiver(),
						viewAnnotation, iActicity);
				iActicity.setViewAnnotation(viewAnnotation);
			}
		} else if (methodName.equals("callActivityOnPause")) {
			ContentManager contentManager = ContentManager.getManager();
			Field mActivityField = FieldManager.field(ContentManager.class,
					"mActivity");
			mActivityField.set(contentManager, null);
		} else if (methodName.equals("callActivityOnDestroy")) {
			setNull(args[0]);
		}
		return mMethod.invoke(mInstrumentation, args);
	}

	private void setResources(Object object, AMRP amrp) {
		if (amrp == null){
			XhLog.e("没有对应的资源");
			return;
		}
		XhLog.e("设置资源加载");
		FieldManager.set_field(object, mResourcesField, amrp.resources);
		FieldManager.set_field(object, mThemeField, amrp.mTheme);
		FieldManager.set_field(object, mResourcesField1, amrp.resources);
		FieldManager.set_field(object, mThemeField1, amrp.mTheme);
		XhLog.e("设置资源加载成功");
	}

	/**
	 * 2018/4/16 12:24 annotation：将变量中的字段制空 author：liuhuiliang email
	 * ：825378291@qq.com
	 * 
	 * 
	 */
	private void setNull(Object object) {
		ResourceRecovery.gc(object);
	}
}
