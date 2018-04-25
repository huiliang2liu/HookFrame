package com.xh.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

/**
 * 回收资源
 * 
 */
public class ResourceRecovery {
	/**
	 * 没有系统回收
	 * 
	 * @param object
	 * @throws Exception
	 */
	public static void gc(Object object) {
		if (object == null)
			return;
		gc(object.getClass(), object);
	}

	public static void gc(Class cls, Object object) {
		if (cls == null)
			return;
		try {
			Field[] fields = cls.getDeclaredFields();
			if (fields == null || fields.length <= 0)
				return;
			for (Field field : fields) {
				if (is_final(field))
					continue;
				Object object2 = field.get(object);
				if (object2 == null || is_basic(object2))
					continue;
				if (is_string(object2))
					;
				else if (object2 instanceof Bitmap)
					gc_bitmap((Bitmap) object2);
				else if (object2 instanceof View)
					gc_view((View) object2);
				field.set(object, null);
			}
			gc(cls.getSuperclass(), object);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * 是否为基本类型
	 * 
	 * @param object
	 * @return
	 */
	public static boolean is_basic(Object object) {
		return object instanceof Integer || object instanceof Long
				|| object instanceof Byte || object instanceof Short
				|| object instanceof Character || object instanceof Float
				|| object instanceof Double || object instanceof Boolean;
	}

	/**
	 * 是否为string
	 * 
	 * @param object
	 * @return
	 */
	public static boolean is_string(Object object) {
		return object instanceof String;
	}

	/**
	 * 回收试图
	 * 
	 * @param view
	 */
	public static void gc_view(View view) {
		if (view != null) {
			gc_bitmap(view.getDrawingCache());
			if (view instanceof ImageView) {
				ImageView imageView = (ImageView) view;
				Drawable drawable = imageView.getDrawable();
				if (drawable != null && drawable instanceof BitmapDrawable) {
					gc_bitmap_drawable((BitmapDrawable) drawable);
				}
			}
			view.clearAnimation();
			view.clearFocus();
		}
	}

	/**
	 * 回收bitmap
	 * 
	 * @param bitmap
	 */
	public static void gc_bitmap(Bitmap bitmap) {
		if (bitmap != null && !bitmap.isRecycled())
			bitmap.recycle();
	}

	/**
	 * 回收bitmapDrawable
	 * 
	 * @param bitmapDrawable
	 */
	public static void gc_bitmap_drawable(BitmapDrawable bitmapDrawable) {
		if (bitmapDrawable != null) {
			gc_bitmap(bitmapDrawable.getBitmap());
		}
	}

	/**
	 * 是否被abstract修饰
	 * 
	 * @param modifiers
	 * @return
	 */
	public static boolean is_abstract(int modifiers) {
		return Modifier.isAbstract(modifiers);
	}

	/**
	 * 是否被abstract修饰
	 * 
	 * @param modifiers
	 * @return
	 */
	public static boolean is_abstract(Method method) {
		return method == null ? false : Modifier.isAbstract(method
				.getModifiers());
	}

	/**
	 * 是否被abstract修饰
	 * 
	 * @param modifiers
	 * @return
	 */
	public static boolean is_abstract(Class cl) {
		return cl == null ? false : Modifier.isAbstract(cl.getModifiers());
	}

	/**
	 * 是否被final修饰
	 * 
	 * @param modifiers
	 * @return
	 */
	public static boolean is_final(int modifiers) {
		return Modifier.isFinal(modifiers);
	}

	/**
	 * 是否被final修饰
	 * 
	 * @param modifiers
	 * @return
	 */
	public static boolean is_final(Field field) {
		return field == null ? false : Modifier.isFinal(field.getModifiers());
	}

	/**
	 * 是否被final修饰
	 * 
	 * @param modifiers
	 * @return
	 */
	public static boolean is_final(Method method) {
		return method == null ? false : Modifier.isFinal(method.getModifiers());
	}

	/**
	 * 是否被final修饰
	 * 
	 * @param modifiers
	 * @return
	 */
	public static boolean is_final(Class cl) {
		return cl == null ? false : Modifier.isFinal(cl.getModifiers());
	}

	/**
	 * 是否被interface修饰
	 * 
	 * @param modifiers
	 * @return
	 */
	public static boolean is_interface(int modifiers) {
		return Modifier.isInterface(modifiers);
	}

	/**
	 * 是否被interface修饰
	 * 
	 * @param modifiers
	 * @return
	 */
	public static boolean is_interface(Class cl) {
		return cl == null ? false : Modifier.isInterface(cl.getModifiers());
	}

	/**
	 * 是否被native修饰
	 * 
	 * @param modifiers
	 * @return
	 */
	public static boolean is_native(int modifiers) {
		return Modifier.isNative(modifiers);
	}

	/**
	 * 是否被native修饰
	 * 
	 * @param modifiers
	 * @return
	 */
	public static boolean is_native(Method method) {
		return method == null ? false : Modifier
				.isNative(method.getModifiers());
	}

	/**
	 * 是否被private修饰
	 * 
	 * @param modifiers
	 * @return
	 */
	public static boolean is_private(int modifiers) {
		return Modifier.isPrivate(modifiers);
	}

	/**
	 * 是否被private修饰
	 * 
	 * @param modifiers
	 * @return
	 */
	public static boolean is_private(Field field) {
		return field == null ? false : Modifier.isPrivate(field.getModifiers());
	}

	/**
	 * 是否被private修饰
	 * 
	 * @param modifiers
	 * @return
	 */
	public static boolean is_private(Method method) {
		return method == null ? false : Modifier.isPrivate(method
				.getModifiers());
	}

	/**
	 * 是否被private修饰
	 * 
	 * @param modifiers
	 * @return
	 */
	public static boolean is_private(Class cl) {
		return cl == null ? false : Modifier.isPrivate(cl.getModifiers());
	}

	/**
	 * 是否被protected修饰
	 * 
	 * @param modifiers
	 * @return
	 */
	public static boolean is_protected(int modifiers) {
		return Modifier.isProtected(modifiers);
	}

	/**
	 * 是否被protected修饰
	 * 
	 * @param modifiers
	 * @return
	 */
	public static boolean is_protected(Field field) {
		return field == null ? false : Modifier.isProtected(field
				.getModifiers());
	}

	/**
	 * 是否被protected修饰
	 * 
	 * @param modifiers
	 * @return
	 */
	public static boolean is_protected(Method method) {
		return method == null ? false : Modifier.isProtected(method
				.getModifiers());
	}

	/**
	 * 是否被protected修饰
	 * 
	 * @param modifiers
	 * @return
	 */
	public static boolean is_protected(Class cl) {
		return cl == null ? false : Modifier.isProtected(cl.getModifiers());
	}

	/**
	 * 是否被public修饰
	 * 
	 * @param modifiers
	 * @return
	 */
	public static boolean is_public(int modifiers) {
		return Modifier.isPublic(modifiers);
	}

	/**
	 * 是否被public修饰
	 * 
	 * @param modifiers
	 * @return
	 */
	public static boolean is_public(Field field) {
		return field == null ? false : Modifier.isPublic(field.getModifiers());
	}

	/**
	 * 是否被public修饰
	 * 
	 * @param modifiers
	 * @return
	 */
	public static boolean is_public(Method method) {
		return method == null ? false : Modifier
				.isPublic(method.getModifiers());
	}

	/**
	 * 是否被public修饰
	 * 
	 * @param modifiers
	 * @return
	 */
	public static boolean is_public(Class cl) {
		return cl == null ? false : Modifier.isPublic(cl.getModifiers());
	}

	/**
	 * 是否被static修饰
	 * 
	 * @param modifiers
	 * @return
	 */
	public static boolean is_static(int modifiers) {
		return Modifier.isStatic(modifiers);
	}

	/**
	 * 是否被static修饰
	 * 
	 * @param modifiers
	 * @return
	 */
	public static boolean is_static(Field field) {
		return field == null ? false : Modifier.isStatic(field.getModifiers());
	}

	/**
	 * 是否被static修饰
	 * 
	 * @param modifiers
	 * @return
	 */
	public static boolean is_static(Method method) {
		return method == null ? false : Modifier
				.isStatic(method.getModifiers());
	}

	/**
	 * 是否被static修饰
	 * 
	 * @param modifiers
	 * @return
	 */
	public static boolean is_static(Class cl) {
		return cl == null ? false : Modifier.isStatic(cl.getModifiers());
	}

	/**
	 * 是否被strictfp修饰
	 * 
	 * @param modifiers
	 * @return
	 */
	public static boolean is_strictfp(int modifiers) {
		return Modifier.isStrict(modifiers);
	}

	/**
	 * 是否被synchronized修饰
	 * 
	 * @param modifiers
	 * @return
	 */
	public static boolean is_synchronized(int modifiers) {
		return Modifier.isSynchronized(modifiers);
	}

	/**
	 * 是否被synchronized修饰
	 * 
	 * @param modifiers
	 * @return
	 */
	public static boolean is_synchronized(Method method) {
		return method == null ? false : Modifier.isSynchronized(method
				.getModifiers());
	}

	/**
	 * 是否被transient修饰
	 * 
	 * @param modifiers
	 *            暂时的
	 * @return
	 */
	public static boolean is_transient(int modifiers) {
		return Modifier.isTransient(modifiers);
	}

	/**
	 * 是否被transient修饰
	 * 
	 * @param modifiers
	 *            暂时的
	 * @return
	 */
	public static boolean is_transient(Field field) {
		return field == null ? false : Modifier.isTransient(field
				.getModifiers());
	}

	/**
	 * 是否被volatile修饰
	 * 
	 * @param modifiers
	 *            易失的
	 * @return
	 */
	public static boolean is_volatile(int modifiers) {
		return Modifier.isVolatile(modifiers);
	}

	/**
	 * 是否被volatile修饰
	 * 
	 * @param modifiers
	 *            易失的
	 * @return
	 */
	public static boolean is_volatile(Field field) {
		return field == null ? false : Modifier
				.isVolatile(field.getModifiers());
	}

	/**
	 * 是否modifiers修饰
	 * 
	 * @param modifiers
	 * @return
	 */
	public static String modifiers(int modifiers) {
		return Modifier.toString(modifiers);
	}
}
