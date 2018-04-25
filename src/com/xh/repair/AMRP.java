package com.xh.repair;

import android.content.pm.PackageInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.Resources.Theme;

/**
 * @version 创建时间：2017-12-14 上午11:14:35 项目：repair 包名：com.xh.util 文件名：AMRP.java
 *          作者：lhl 说明:
 */

public class AMRP {
	public AMRP(PackageInfo packageInfo, AssetManager assetManager,
			Resources resources) {
		// TODO Auto-generated constructor stub
		if (packageInfo != null)
			packageName = packageInfo.packageName;
		this.assetManager = assetManager;
		this.resources = resources;
		this.packageInfo = packageInfo;
		mTheme = resources.newTheme();
		// Finals适配三星以及部分加载XML出现异常BUG
		try {
			mTheme.applyStyle(packageInfo.applicationInfo.theme, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public AMRP() {
		// TODO Auto-generated constructor stub
	}

	public Theme mTheme;
	public PackageInfo packageInfo;
	public String packageName = "";
	public AssetManager assetManager;
	public Resources resources;

	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		if (o == null || !o.getClass().getName().equals(AMRP.class.getName()))
			return false;
		AMRP a = (AMRP) o;
		return a.packageName.equals(packageName);
	}
}