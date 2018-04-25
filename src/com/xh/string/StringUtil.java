package com.xh.string;

import java.util.ArrayList;

import com.xh.util.XhPhoneInformation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Path;
import android.util.SparseArray;

/**
 * @version 创建时间：2017-12-20 下午3:41:02 项目：repair 包名：com.xh.string
 *          文件名：StringUtil.java 作者：lhl 说明:
 */

public class StringUtil {
	public static final int F = Integer.valueOf("f", 16);
	public static final int FF = Integer.valueOf("ff", 16);
	private final static String PX = "px";
	private final static String DIP = "dip";
	private final static String DP = "dp";
	private final static String SP = "sp";

	@SuppressLint("NewApi")
	public static boolean isEmpty(String string) {
		return string == null || string.isEmpty();
	}

	public static Integer color(String string) {
		if (isEmpty(string))
			return null;
		if (string.startsWith("#"))
			string = string.substring(1);
		else if (string.startsWith("0x"))
			string = string.substring(2);
		int len = string.length();
		if (len != 3 && len != 4 && len != 6 && len != 8)
			return null;
		switch (len) {
		case 3: {
			int r = string2int16(string.substring(0, 1));
			r = r * FF / F;
			int g = string2int16(string.substring(1, 2));
			g = g * FF / F;
			int b = string2int16(string.substring(2, 3));
			b = b * FF / F;
			return Color.rgb(r, g, b);
		}
		case 4: {
			int a = string2int16(string.substring(0, 1));
			a = a * FF / F;
			int r = string2int16(string.substring(1, 2));
			r = r * FF / F;
			int g = string2int16(string.substring(2, 3));
			g = g * FF / F;
			int b = string2int16(string.substring(3, 4));
			b = b * FF / F;
			return Color.argb(a, r, g, b);
		}
		case 6: {
			int r = string2int16(string.substring(0, 2));
			int g = string2int16(string.substring(2, 4));
			int b = string2int16(string.substring(4, 6));
			return Color.rgb(r, g, b);
		}
		default: {
			int a = string2int16(string.substring(0, 2));
			int r = string2int16(string.substring(2, 4));
			int g = string2int16(string.substring(4, 6));
			int b = string2int16(string.substring(6, 8));
			return Color.argb(a, r, g, b);
		}
		}
	}

	public static int width(String string, XhPhoneInformation information) {
		if (string.endsWith(PX))
			return Integer.valueOf(string.substring(0, string.length() - 2));
		if (string.endsWith(DP)) {
			int w = Integer.valueOf(string.substring(0, string.length() - 2));
			return information.dip2px(w);
		}
		if (string.endsWith(DIP)) {
			int w = Integer.valueOf(string.substring(0, string.length() - 3));
			return information.dip2px(w);
		}
		if (string.endsWith(SP)) {
			int w = Integer.valueOf(string.substring(0, string.length() - 2));
			return information.sp2px(w);
		}
		return Integer.valueOf(string);
	}

	public static int string2int16(String string) {
		return Integer.valueOf(string, 16);
	}

	private static final SparseArray<float[]> sPointList;

	static {
		sPointList = new SparseArray<float[]>();
		float[][] LETTERS = new float[][] {
				new float[] {
						// A
						24, 0, 1, 22, 1, 22, 1, 72, 24, 0, 47, 22, 47, 22, 47,
						72, 1, 48, 47, 48 },

				new float[] {
						// B
						0, 0, 0, 72, 0, 0, 37, 0, 37, 0, 47, 11, 47, 11, 47,
						26, 47, 26, 38, 36, 38, 36, 0, 36, 38, 36, 47, 46, 47,
						46, 47, 61, 47, 61, 38, 71, 37, 72, 0, 72, },

				new float[] {
						// C
						47, 0, 0, 0, 0, 0, 0, 72, 0, 72, 47, 72, },

				new float[] {
						// D
						0, 0, 0, 72, 0, 0, 24, 0, 24, 0, 47, 22, 47, 22, 47,
						48, 47, 48, 23, 72, 23, 72, 0, 72, },

				new float[] {
						// E
						0, 0, 0, 72, 0, 0, 47, 0, 0, 36, 37, 36, 0, 72, 47, 72, },

				new float[] {
						// F
						0, 0, 0, 72, 0, 0, 47, 0, 0, 36, 37, 36, },

				new float[] {
						// G
						47, 23, 47, 0, 47, 0, 0, 0, 0, 0, 0, 72, 0, 72, 47, 72,
						47, 72, 47, 48, 47, 48, 24, 48, },

				new float[] {
						// H
						0, 0, 0, 72, 0, 36, 47, 36, 47, 0, 47, 72, },

				new float[] {
						// I
						0, 0, 47, 0, 24, 0, 24, 72, 0, 72, 47, 72, },

				new float[] {
						// J
						47, 0, 47, 72, 47, 72, 24, 72, 24, 72, 0, 48, },

				new float[] {
						// K
						0, 0, 0, 72, 47, 0, 3, 33, 3, 38, 47, 72, },

				new float[] {
						// L
						0, 0, 0, 72, 0, 72, 47, 72, },

				new float[] {
						// M
						0, 0, 0, 72, 0, 0, 24, 23, 24, 23, 47, 0, 47, 0, 47,
						72, },

				new float[] {
						// N
						0, 0, 0, 72, 0, 0, 47, 72, 47, 72, 47, 0, },

				new float[] {
						// O
						0, 0, 0, 72, 0, 72, 47, 72, 47, 72, 47, 0, 47, 0, 0, 0, },

				new float[] {
						// P
						0, 0, 0, 72, 0, 0, 47, 0, 47, 0, 47, 36, 47, 36, 0, 36, },

				new float[] {
						// Q
						0, 0, 0, 72, 0, 72, 23, 72, 23, 72, 47, 48, 47, 48, 47,
						0, 47, 0, 0, 0, 24, 28, 47, 71, },

				new float[] {
						// R
						0, 0, 0, 72, 0, 0, 47, 0, 47, 0, 47, 36, 47, 36, 0, 36,
						0, 37, 47, 72, },

				new float[] {
						// S
						47, 0, 0, 0, 0, 0, 0, 36, 0, 36, 47, 36, 47, 36, 47,
						72, 47, 72, 0, 72, },

				new float[] {
						// T
						0, 0, 47, 0, 24, 0, 24, 72, },

				new float[] {
						// U
						0, 0, 0, 72, 0, 72, 47, 72, 47, 72, 47, 0, },

				new float[] {
						// V
						0, 0, 24, 72, 24, 72, 47, 0, },

				new float[] {
						// W
						0, 0, 0, 72, 0, 72, 24, 49, 24, 49, 47, 72, 47, 72, 47,
						0 },

				new float[] {
						// X
						0, 0, 47, 72, 47, 0, 0, 72 },

				new float[] {
						// Y
						0, 0, 24, 23, 47, 0, 24, 23, 24, 23, 24, 72 },

				new float[] {
						// Z
						0, 0, 47, 0, 47, 0, 0, 72, 0, 72, 47, 72 }, };
		final float[][] NUMBERS = new float[][] {
				new float[] {
						// 0
						0, 0, 0, 72, 0, 72, 47, 72, 47, 72, 47, 0, 47, 0, 0, 0, },
				new float[] {
						// 1
						24, 0, 24, 72, },

				new float[] {
						// 2
						0, 0, 47, 0, 47, 0, 47, 36, 47, 36, 0, 36, 0, 36, 0,
						72, 0, 72, 47, 72 },

				new float[] {
						// 3
						0, 0, 47, 0, 47, 0, 47, 36, 47, 36, 0, 36, 47, 36, 47,
						72, 47, 72, 0, 72, },

				new float[] {
						// 4
						0, 0, 0, 36, 0, 36, 47, 36, 47, 0, 47, 72, },

				new float[] {
						// 5
						0, 0, 0, 36, 0, 36, 47, 36, 47, 36, 47, 72, 47, 72, 0,
						72, 0, 0, 47, 0 },

				new float[] {
						// 6
						0, 0, 0, 72, 0, 72, 47, 72, 47, 72, 47, 36, 47, 36, 0,
						36 },

				new float[] {
						// 7
						0, 0, 47, 0, 47, 0, 47, 72 },

				new float[] {
						// 8
						0, 0, 0, 72, 0, 72, 47, 72, 47, 72, 47, 0, 47, 0, 0, 0,
						0, 36, 47, 36 },

				new float[] {
						// 9
						47, 0, 0, 0, 0, 0, 0, 36, 0, 36, 47, 36, 47, 0, 47, 72, } };
		// A - Z
		for (int i = 0; i < LETTERS.length; i++) {
			sPointList.append(i + 65, LETTERS[i]);
		}
		// a - z
		for (int i = 0; i < LETTERS.length; i++) {
			sPointList.append(i + 65 + 32, LETTERS[i]);
		}
		// 0 - 9
		for (int i = 0; i < NUMBERS.length; i++) {
			sPointList.append(i + 48, NUMBERS[i]);
		}
		// blank
		addChar(' ', new float[] {});
		// -
		addChar('-', new float[] { 0, 36, 47, 36 });
		// .
		addChar('.', new float[] { 24, 60, 24, 72 });
	}

	public static void addChar(char c, float[] points) {
		sPointList.append(c, points);
	}

	/**
	 * 
	 * lhl 2018-1-9 下午3:59:39 说明：将字母转化为路径
	 * 
	 * @param str
	 * @return ArrayList<float[]>
	 */
	private static ArrayList<float[]> getPathFloats(String str) {
		return getPathFloats(str, 1, 14);
	}

	/**
	 * 
	 * lhl 2018-1-9 下午4:00:05 说明：将字母转化为路径
	 * 
	 * @param str
	 * @param scale
	 * @param gapBetweenLetter
	 *            字母之间的距离
	 * @return ArrayList<float[]>
	 */
	private static ArrayList<float[]> getPathFloats(String str, float scale,
			int gapBetweenLetter) {
		ArrayList<float[]> list = new ArrayList<float[]>();
		float offsetForWidth = 0;
		for (int i = 0; i < str.length(); i++) {
			int pos = str.charAt(i);
			int key = sPointList.indexOfKey(pos);
			if (key == -1) {
				continue;
			}
			float[] points = sPointList.get(pos);
			int pointCount = points.length / 4;

			for (int j = 0; j < pointCount; j++) {
				float[] line = new float[4];
				for (int k = 0; k < 4; k++) {
					float l = points[j * 4 + k];
					// x
					if (k % 2 == 0) {
						line[k] = (l + offsetForWidth) * scale;
					}
					// y
					else {
						line[k] = l * scale;
					}
				}
				list.add(line);
			}
			offsetForWidth += 57 + gapBetweenLetter;
		}
		return list;
	}

	/**
	 * 从R.array.xxx里取出点阵，
	 * 
	 * @param context
	 * @param arrayId
	 * @param zoomSize
	 * @return
	 */
	public static Path getPath(Context context, int arrayId, float zoomSize) {
		Path path = new Path();
		String[] points = context.getResources().getStringArray(arrayId);
		for (int i = 0; i < points.length; i++) {
			String[] x = points[i].split(",");
			for (int j = 0; j < x.length; j = j + 2) {
				if (j == 0) {
					path.moveTo(Float.parseFloat(x[j]) * zoomSize,
							Float.parseFloat(x[j + 1]) * zoomSize);
				} else {
					path.lineTo(Float.parseFloat(x[j]) * zoomSize,
							Float.parseFloat(x[j + 1]) * zoomSize);
				}
			}
		}
		return path;
	}

	/**
	 * 根据ArrayList<float[]> path 解析
	 * 
	 * @param path
	 * @return
	 */
	private static Path getPath(ArrayList<float[]> path) {
		Path sPath = new Path();
		for (int i = 0; i < path.size(); i++) {
			float[] floats = path.get(i);
			sPath.moveTo(floats[0], floats[1]);
			sPath.lineTo(floats[2], floats[3]);
		}
		return sPath;
	}

	/**
	 * 
	 * lhl 2018-1-9 下午3:59:39 说明：将字母转化为路径
	 * 
	 * @param str
	 * @return ArrayList<float[]>
	 */
	public static Path string2path(String str) {
		return getPath(getPathFloats(str));
	}

	/**
	 * 
	 * lhl 2018-1-9 下午4:00:05 说明：将字母转化为路径
	 * 
	 * @param str
	 * @param scale
	 * @param gapBetweenLetter
	 *            字母之间的距离
	 * @return ArrayList<float[]>
	 */
	public static Path string2path(String str, float scale, int gapBetweenLetter) {
		return getPath(getPathFloats(str, scale, gapBetweenLetter));
	}

}
