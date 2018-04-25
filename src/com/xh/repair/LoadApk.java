package com.xh.repair;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.zip.ZipFile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Build;
import android.util.Log;

import com.xh.util.FileManagemet;
import com.xh.util.XhLog;

import dalvik.system.DexFile;

/**
 * @version 创建时间：2017-12-5 下午12:22:42 项目：TvBlackAD-eclipse
 *          包名：com.tvblack.tv.utils 文件名：TVBHotUpdate.java 作者：lhl 说明:热更新
 */

class LoadApk {
	private final static String TAG = LoadApk.class.getName();
	public Context context;
	public List<AMRP> aList;

	public LoadApk(Context context, File apkfile) {
		// TODO Auto-generated constructor stub
		try {
			aList = new ArrayList<AMRP>();
			repair(context, apkfile);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * 
	 * lhl 2017-12-5 下午12:43:36 说明：
	 * 
	 * @param context
	 *            上下文
	 * @param path
	 *            不存路径
	 * @param fileend
	 *            文件后缀 void
	 */
	@SuppressLint("NewApi")
	private void repair(final Context context, File apkfile) {
		if (context == null || apkfile == null || !apkfile.exists()
				|| !apkfile.isDirectory())
			return;
		this.context = context;
		File opt_file = FileManagemet.getDir(context, "opt_apk");
		if (!opt_file.exists())
			opt_file.mkdirs();
		File[] files = apkfile.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String filename) {
				// TODO Auto-generated method stub
				XhLog.e(filename);
				return filename.endsWith(".apk");
			}
		});
		List<File> f = Arrays.asList(files);
		if (f == null || f.size() <= 0)
			return;
		repair(context.getClassLoader(), opt_file, f);
	}

	/**
	 * 
	 * lhl 2017-12-5 下午12:43:05 说明：
	 * 
	 * @param loader
	 *            类加载器
	 * @param dexDir
	 *            包下的地方
	 * @param files
	 *            void 加载列表
	 */
	private void repair(ClassLoader loader, File dexDir, List<File> files) {
		if (loader == null || dexDir == null || !dexDir.exists()
				|| !dexDir.isDirectory() || files == null || files.size() <= 0)
			return;
		try {
			installSecondaryDexes(loader, dexDir, files);
			for (int i = 0; i < files.size(); i++) {
				File file = files.get(i);
				String path = file.getAbsolutePath();
				SoLibManager.getSoLoader().copyPluginSoLib(context, path,
						dexDir.getAbsolutePath());
				AssetManager assetManager = AssetManager.class.newInstance();
				Method addAssetPath = assetManager.getClass().getMethod(
						"addAssetPath", String.class);
				PackageInfo packageInfo = context.getPackageManager()
						.getPackageArchiveInfo(
								path,
								PackageManager.GET_ACTIVITIES
										| PackageManager.GET_SERVICES
										| PackageManager.GET_META_DATA
										| PackageManager.GET_PERMISSIONS
										| PackageManager.GET_SIGNATURES);
				addAssetPath.invoke(assetManager, path);
				Resources superRes = context.getResources();
				Resources mResources = new Resources(assetManager,
						superRes.getDisplayMetrics(),
						superRes.getConfiguration());
				aList.add(new AMRP(packageInfo, assetManager, mResources));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void installSecondaryDexes(ClassLoader loader, File dexDir,
			List<File> files) throws IllegalArgumentException,
			IllegalAccessException, NoSuchFieldException,
			InvocationTargetException, NoSuchMethodException, IOException {
		if (!files.isEmpty()) {
			if (Build.VERSION.SDK_INT >= 19) {
				V19.install(loader, files, dexDir);
			} else if (Build.VERSION.SDK_INT >= 14) {
				V14.install(loader, files, dexDir);
			} else {
				V4.install(loader, files);
			}
		}
	}

	/**
	 * Locates a given field anywhere in the class inheritance hierarchy.
	 * 
	 * @param instance
	 *            an object to search the field into.
	 * @param name
	 *            field name
	 * @return a field object
	 * @throws NoSuchFieldException
	 *             if the field cannot be located
	 */
	private static Field findField(Object instance, String name)
			throws NoSuchFieldException {
		for (Class<?> clazz = instance.getClass(); clazz != null; clazz = clazz
				.getSuperclass()) {
			try {
				Field field = clazz.getDeclaredField(name);

				if (!field.isAccessible()) {
					field.setAccessible(true);
				}

				return field;
			} catch (NoSuchFieldException e) {
				// ignore and search next
			}
		}

		throw new NoSuchFieldException("Field " + name + " not found in "
				+ instance.getClass());
	}

	/**
	 * Locates a given method anywhere in the class inheritance hierarchy.
	 * 
	 * @param instance
	 *            an object to search the method into.
	 * @param name
	 *            method name
	 * @param parameterTypes
	 *            method parameter types
	 * @return a method object
	 * @throws NoSuchMethodException
	 *             if the method cannot be located
	 */
	private static Method findMethod(Object instance, String name,
			Class<?>... parameterTypes) throws NoSuchMethodException {
		for (Class<?> clazz = instance.getClass(); clazz != null; clazz = clazz
				.getSuperclass()) {
			try {
				Method method = clazz.getDeclaredMethod(name, parameterTypes);

				if (!method.isAccessible()) {
					method.setAccessible(true);
				}

				return method;
			} catch (NoSuchMethodException e) {
				// ignore and search next
				try {
					Method[] methods = clazz.getDeclaredMethods();
					for (int i = 0; i < methods.length; i++) {
						Method method = methods[i];
						if (method.getName().equals(name)) {
							if (!method.isAccessible()) {
								method.setAccessible(true);
							}
							return method;
						}
					}
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}

		throw new NoSuchMethodException("Method " + name + " with parameters "
				+ Arrays.asList(parameterTypes) + " not found in "
				+ instance.getClass());
	}

	/**
	 * Replace the value of a field containing a non null array, by a new array
	 * containing the elements of the original array plus the elements of
	 * extraElements.
	 * 
	 * @param instance
	 *            the instance whose field is to be modified.
	 * @param fieldName
	 *            the field to modify.
	 * @param extraElements
	 *            elements to append at the end of the array.
	 */
	private static void expandFieldArray(Object instance, String fieldName,
			Object[] extraElements) throws NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException {
		Field jlrField = findField(instance, fieldName);
		Object[] original = (Object[]) jlrField.get(instance);
		Object[] combined = (Object[]) Array.newInstance(original.getClass()
				.getComponentType(), original.length + extraElements.length);
		System.arraycopy(original, 0, combined, 0, original.length);
		System.arraycopy(extraElements, 0, combined, original.length,
				extraElements.length);
		jlrField.set(instance, combined);
	}

	/**
	 * Installer for platform versions 19.
	 */
	private static final class V19 {

		private static void install(ClassLoader loader,
				List<File> additionalClassPathEntries, File optimizedDirectory)
				throws IllegalArgumentException, IllegalAccessException,
				NoSuchFieldException, InvocationTargetException,
				NoSuchMethodException {
			/*
			 * The patched class loader is expected to be a descendant of
			 * dalvik.system.BaseDexClassLoader. We modify its
			 * dalvik.system.DexPathList pathList field to append additional DEX
			 * file entries.
			 */
			Field pathListField = findField(loader, "pathList");
			Object dexPathList = pathListField.get(loader);
			ArrayList<IOException> suppressedExceptions = new ArrayList<IOException>();
			expandFieldArray(
					dexPathList,
					"dexElements",
					makeDexElements(dexPathList, new ArrayList<File>(
							additionalClassPathEntries), optimizedDirectory,
							suppressedExceptions, loader));
			if (suppressedExceptions.size() > 0) {
				for (IOException e : suppressedExceptions) {
					Log.w(TAG, "Exception in makeDexElement", e);
				}
				Field suppressedExceptionsField = findField(loader,
						"dexElementsSuppressedExceptions");
				IOException[] dexElementsSuppressedExceptions = (IOException[]) suppressedExceptionsField
						.get(loader);

				if (dexElementsSuppressedExceptions == null) {
					dexElementsSuppressedExceptions = suppressedExceptions
							.toArray(new IOException[suppressedExceptions
									.size()]);
				} else {
					IOException[] combined = new IOException[suppressedExceptions
							.size() + dexElementsSuppressedExceptions.length];
					suppressedExceptions.toArray(combined);
					System.arraycopy(dexElementsSuppressedExceptions, 0,
							combined, suppressedExceptions.size(),
							dexElementsSuppressedExceptions.length);
					dexElementsSuppressedExceptions = combined;
				}

				suppressedExceptionsField.set(loader,
						dexElementsSuppressedExceptions);
			}
		}

		/**
		 * A wrapper around
		 * {@code private static final dalvik.system.DexPathList#makeDexElements}
		 * .
		 */
		private static Object[] makeDexElements(Object dexPathList,
				ArrayList<File> files, File optimizedDirectory,
				ArrayList<IOException> suppressedExceptions, ClassLoader loader)
				throws IllegalAccessException, InvocationTargetException,
				NoSuchMethodException {
			Method makeDexElements = null;
			try {
				makeDexElements = findMethod(dexPathList, "makeDexElements",
						List.class, File.class, List.class, ClassLoader.class);
				return (Object[]) makeDexElements.invoke(dexPathList, files,
						optimizedDirectory, suppressedExceptions, loader);
			} catch (Exception e) {
				// // TODO: handle exception
				makeDexElements = findMethod(dexPathList, "makeDexElements",
						List.class, File.class, List.class);
				return (Object[]) makeDexElements.invoke(dexPathList, files,
						optimizedDirectory, suppressedExceptions);
			}

		}
	}

	/**
	 * Installer for platform versions 14, 15, 16, 17 and 18.
	 */
	private static final class V14 {

		private static void install(ClassLoader loader,
				List<File> additionalClassPathEntries, File optimizedDirectory)
				throws IllegalArgumentException, IllegalAccessException,
				NoSuchFieldException, InvocationTargetException,
				NoSuchMethodException {
			/*
			 * The patched class loader is expected to be a descendant of
			 * dalvik.system.BaseDexClassLoader. We modify its
			 * dalvik.system.DexPathList pathList field to append additional DEX
			 * file entries.
			 */
			Field pathListField = findField(loader, "pathList");
			Object dexPathList = pathListField.get(loader);
			expandFieldArray(
					dexPathList,
					"dexElements",
					makeDexElements(dexPathList, new ArrayList<File>(
							additionalClassPathEntries), optimizedDirectory));
		}

		/**
		 * A wrapper around
		 * {@code private static final dalvik.system.DexPathList#makeDexElements}
		 * .
		 */
		private static Object[] makeDexElements(Object dexPathList,
				ArrayList<File> files, File optimizedDirectory)
				throws IllegalAccessException, InvocationTargetException,
				NoSuchMethodException {
			Method makeDexElements = findMethod(dexPathList, "makeDexElements",
					ArrayList.class, File.class);

			return (Object[]) makeDexElements.invoke(dexPathList, files,
					optimizedDirectory);
		}
	}

	/**
	 * Installer for platform versions 4 to 13.
	 */
	private static final class V4 {
		private static void install(ClassLoader loader,
				List<File> additionalClassPathEntries)
				throws IllegalArgumentException, IllegalAccessException,
				NoSuchFieldException, IOException {
			/*
			 * The patched class loader is expected to be a descendant of
			 * dalvik.system.DexClassLoader. We modify its fields mPaths,
			 * mFiles, mZips and mDexs to append additional DEX file entries.
			 */
			int extraSize = additionalClassPathEntries.size();

			Field pathField = findField(loader, "path");

			StringBuilder path = new StringBuilder(
					(String) pathField.get(loader));
			String[] extraPaths = new String[extraSize];
			File[] extraFiles = new File[extraSize];
			ZipFile[] extraZips = new ZipFile[extraSize];
			DexFile[] extraDexs = new DexFile[extraSize];
			for (ListIterator<File> iterator = additionalClassPathEntries
					.listIterator(); iterator.hasNext();) {
				File additionalEntry = iterator.next();
				String entryPath = additionalEntry.getAbsolutePath();
				path.append(':').append(entryPath);
				int index = iterator.previousIndex();
				extraPaths[index] = entryPath;
				extraFiles[index] = additionalEntry;
				extraZips[index] = new ZipFile(additionalEntry);
				extraDexs[index] = DexFile.loadDex(entryPath, entryPath
						+ ".dex", 0);
			}
			pathField.set(loader, path.toString());
			expandFieldArray(loader, "mPaths", extraPaths);
			expandFieldArray(loader, "mFiles", extraFiles);
			expandFieldArray(loader, "mZips", extraZips);
			expandFieldArray(loader, "mDexs", extraDexs);
		}
	}

}