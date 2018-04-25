package com.xh.repair;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.Animation;

/**
 * @version 创建时间：2017-12-13 下午6:58:25 项目：repair 包名：com.xh.ifaces 文件名：ILoad.java
 *          作者：lhl 说明:
 */

public interface ILoad {
	public View getView(String name, Context context);

	public View getView(Resources resources, String packageName, String name,
			Context context);

	public View getView(int layoutId, Context context);


	public Animation getAnimation(String name, Context context);

	public Animation getAnimation(int animId, Context context);

	public Animation getAnimation(Resources resources, String packageName,
			String name, Context context);


	public int style(String name, Resources resources, String packageName);


	public int dimen(String name, Resources resources, String packageName);

	public int color(String name, Resources resources, String packageName);

	public int colorValue(String name, Resources resources, String packageName);

	public ColorStateList colorList(String name, Resources resources,
			String packageName);

	public int colorValue(int colorId, Resources resources);

	public ColorStateList colorList(int colorId, Resources resources);


	public int anim(String name, Resources resources, String packageName);

	public int raw(String name, Resources resources, String packageName);

	public int attr(String name, Resources resources, String packageName);



	public String stringValue(int stringId);

	public String[] stringValues(int stringId);

	public int string(String name, Resources resources, String packageName);

	public String stringValue(String name, Resources resources,
			String packageName);

	public String[] stringValues(String name, Resources resources,
			String packageName);

	public String stringValue(int stringId, Resources resources);

	public String[] stringValues(int stringId, Resources resources);


	public int id(String name, Resources resources, String packageName);

	public int drawable(String name, Resources resources, String packageName);

	public Drawable drawableValue(int drawableId, Resources resources);

	public Drawable drawableValue(String name, Resources resources,
			String packageName);


	public int layout(String name, Resources resources, String packageName);

	public int name2id(Resources resources, String name, String type,
			String packageName);

	public Resources package2resources(String packageName);

	public AssetManager package2assetManager(String packageName);

	public PackageInfo package2packageInfo(String packageName);

	public Theme package2theme(String packageName);
}