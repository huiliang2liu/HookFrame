package com.xh.base;

import java.io.File;

import android.app.Application;
import android.content.Context;

import com.xh.repair.Load;
import com.xh.util.Manager;
import com.xh.util.XhLog;

/**
 * XhApplication com.xh.base 2018/4/13 10:12 instructions： author:liuhuiliang
 * email:825378291@qq.com
 **/

public class BaseApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		XhLog.setTAG(getPackageName());
		XhLog.setIsDebug(true);
		XhLog.e("application create");
		Manager.init().addLocal(MainActivity.class);
	}

	/**
	 * 
	 * lhl 2018-1-12 下午5:55:40 说明：加载外挂包，最好在子线程，耗时操作 void
	 */
	public final void load() {
		// TODO Auto-generated method stub
		Load.init(this, sdkOrApkSavePath());
	}

	/**
	 * 
	 * lhl 2018-1-12 下午5:49:31 说明：补丁包保存位置
	 * 
	 * @return Field
	 */
	public final File sdkOrApkSavePath() {
		return getDir("third", Context.MODE_PRIVATE);
	}

}
