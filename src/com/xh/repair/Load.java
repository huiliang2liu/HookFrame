package com.xh.repair;

import java.io.File;
import java.util.List;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.xh.string.StringUtil;
import com.xh.util.XhLog;

/**
 * @version 创建时间：2017-12-5 下午6:07:50 项目：repair 包名：com.xh.util 文件名：Load.java
 *          作者：lhl 说明:
 */

public class Load implements ILoad {
	private static Load mLoad;
	private LoadApk loadApk;
	private LoadDex loadDex;
	public Resources appResources;
	public Theme appTheme;
	public AssetManager appAssetManager;
	public PackageInfo appPackageInfo;

	public Resources mResources;
	public Theme mTheme;
	public AssetManager mAssetManager;
	public PackageInfo mPackageInfo;

	public static Load init(Context context, File file) {
		if (mLoad == null)
			synchronized (Load.class) {
				if (mLoad == null)
					mLoad = new Load(context, file);
			}
		return mLoad;
	}

	public static Load getLoad() {
		return mLoad;
	}

	private Load(Context context, File file) {
		// TODO Auto-generated constructor stub
		loadDex = new LoadDex(context, file);
		loadApk = new LoadApk(context, file);
		appResources = context.getResources();
		appTheme = context.getTheme();
		appAssetManager = context.getAssets();
		try {
			appPackageInfo = context.getPackageManager().getPackageInfo(
					context.getPackageName(),
					PackageManager.GET_ACTIVITIES | PackageManager.GET_SERVICES
							| PackageManager.GET_META_DATA
							| PackageManager.GET_PERMISSIONS
							| PackageManager.GET_SIGNATURES);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		loadApk.aList.add(new AMRP(appPackageInfo, appAssetManager,
				appResources));
	}

	public View getView(String name, Context context) {
		return getView(context.getResources(), context.getPackageName(), name,
				context);
	}

	public View getView(Resources resources, String packageName, String name,
			Context context) {
		return getView(layout(name, resources, packageName), context);
	}

	public View getView(int layoutId, Context context) {
		return LayoutInflater.from(context).inflate(layoutId, null);
	}

	public Animation getAnimation(String name, Context context) {
		return getAnimation(context.getResources(), context.getPackageName(),
				name, context);
	}

	public Animation getAnimation(Resources resources, String packageName,
			String name, Context context) {
		return getAnimation(anim(name, resources, packageName), context);
	}

	public Animation getAnimation(int animId, Context context) {
		return AnimationUtils.loadAnimation(context, animId);
	}

	public int style(String name, Resources resources, String packageName) {
		return name2id(resources, packageName, "style", packageName);
	}

	public int dimen(String name, Resources resources, String packageName) {
		return name2id(resources, packageName, "dimen", packageName);
	}

	public int colorValue(int colorId) {
		return mResources.getColor(colorId);
	}

	public ColorStateList colorList(int colorId) {
		return mResources.getColorStateList(colorId);
	}

	public int color(String name, Resources resources, String packageName) {
		return name2id(resources, packageName, "color", packageName);
	}

	public int colorValue(String name, Resources resources, String packageName) {
		return resources.getColor(color(name, resources, packageName));
	}

	public ColorStateList colorList(String name, Resources resources,
			String packageName) {
		return resources.getColorStateList(color(name, resources, packageName));
	}

	public int colorValue(int colorId, Resources resources) {
		return resources.getColor(colorId);
	}

	public ColorStateList colorList(int colorId, Resources resources) {
		return resources.getColorStateList(colorId);
	}

	public int anim(String name, Resources resources, String packageName) {
		return name2id(resources, name, "anim", packageName);
	}

	public int raw(String name, Resources resources, String packageName) {
		return name2id(resources, packageName, "raw", packageName);
	}

	public int attr(String name, Resources resources, String packageName) {
		return name2id(resources, packageName, "attr", packageName);
	}

	public String stringValue(int stringId) {
		return mResources.getString(stringId);
	}

	public String[] stringValues(int stringId) {
		return mResources.getStringArray(stringId);
	}

	public int string(String name, Resources resources, String packageName) {
		return name2id(resources, packageName, "string", packageName);
	}

	public String stringValue(String name, Resources resources,
			String packageName) {
		return resources.getString(name2id(resources, packageName, "string",
				packageName));
	}

	public String[] stringValues(String name, Resources resources,
			String packageName) {
		return resources.getStringArray(name2id(resources, packageName,
				"string", packageName));
	}

	public String stringValue(int stringId, Resources resources) {
		return resources.getString(stringId);
	}

	public String[] stringValues(int stringId, Resources resources) {
		return resources.getStringArray(stringId);
	}

	public int id(String name, Resources resources, String packageName) {
		return name2id(resources, packageName, "id", packageName);
	}

	public int drawable(String name, Resources resources, String packageName) {
		return name2id(resources, packageName, "drawable", packageName);
	}

	public Drawable drawableValue(int drawableId, Resources resources) {
		return resources.getDrawable(drawableId);
	}

	public Drawable drawableValue(String name, Resources resources,
			String packageName) {
		return resources.getDrawable(name2id(resources, packageName,
				"drawable", packageName));
	}

	public int layout(String name, Resources resources, String packageName) {
		return name2id(resources, name, "layout", packageName);
	}

	public int name2id(Resources resources, String name, String type,
			String packageName) {
		return resources.getIdentifier(name, type, packageName);
	}

	public AMRP layoutId2amrp(int layoutId) {
		List<AMRP> amrps = loadApk.aList;
		for (int i = 0; i < amrps.size(); i++) {
			try {
				AMRP amrp = amrps.get(i);
				String name = amrp.resources.getResourceName(layoutId);
				if (!StringUtil.isEmpty(name))
					return amrp;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return null;
	}

	public AMRP layoutName2amrp(String layoutName) {
		List<AMRP> amrps = loadApk.aList;
		for (int i = 0; i < amrps.size(); i++) {
			try {
				AMRP amrp = amrps.get(i);
				int id = name2id(amrp.resources, layoutName, "layout",
						amrp.packageName);
				if (id > 0)
					return amrp;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return null;
	}

	public AMRP package2amrp(String packageName) {
		List<AMRP> amrps = loadApk.aList;
		AMRP p = new AMRP();
		p.packageName = packageName;
		int index = amrps.indexOf(p);
		if (index >= 0)
			return amrps.get(index);
		return null;
	}

	public AMRP layout2amrp(int id) {
		List<AMRP> amrps = loadApk.aList;
		int size = amrps.size();
		for (int i = 0; i < size; i++) {
			try {
				AMRP amrp = amrps.get(i);
				String name = amrp.resources.getResourceName(id);
				if (StringUtil.isEmpty(name))
					continue;
				return amrp;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return null;
	}

	public AMRP layout2amrp(String name) {
		List<AMRP> amrps = loadApk.aList;
		int size = amrps.size();
		XhLog.e("size="+size);
		for (int i = 0; i < size; i++) {
			try {
				AMRP amrp = amrps.get(i);
				int id = layout(name, amrp.resources, amrp.packageName);
				if (id <= 0)
					continue;
				return amrp;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return null;
	}

	public Resources package2resources(String packageName) {
		AMRP p = package2amrp(packageName);
		return p == null ? null : p.resources;
	}

	public AssetManager package2assetManager(String packageName) {
		AMRP p = package2amrp(packageName);
		return p == null ? null : p.assetManager;
	}

	public PackageInfo package2packageInfo(String packageName) {
		AMRP p = package2amrp(packageName);
		return p == null ? null : p.packageInfo;
	}

	public Theme package2theme(String packageName) {
		AMRP p = package2amrp(packageName);
		return p == null ? null : p.mTheme;
	}

}