package com.xh.util;

import android.annotation.SuppressLint;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import com.xh.string.StringUtil;

/**
 * 
 * 数据流管理
 */
public class StreamManage {
	public static String UTF8 = "UTF-8";

	/**
	 * 将输入流转换为byte数组
	 * 
	 * @param is
	 * @return
	 */
	public static byte[] inputStream2byte(InputStream is) {
		try {
			// TODO Auto-generated method stub
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int len = 0;
			byte[] buffer = new byte[1024];
			while ((len = is.read(buffer)) != -1) {
				bos.write(buffer, 0, len);
			}
			bos.flush();
			byte[] arr = bos.toByteArray();
			bos.close();
			is.close();
			return arr;
		} catch (Exception e) {
			// TODO: handle exception
			return e.getMessage().getBytes();
		}
	}

	/**
	 * 将输入流转换为 String
	 * 
	 * @param is
	 * @return
	 * @throws Exception
	 */
	@SuppressLint("NewApi")
	public static String inputStream2String(InputStream is, String charsetName) {
		try {
			if (StringUtil.isEmpty(charsetName))
				return new String(inputStream2byte(is), UTF8);
			return new String(inputStream2byte(is), charsetName);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";
	}

	/**
	 * 将字节转换为输入流
	 * 
	 * @param buff
	 * @return
	 */
	public static InputStream bytes2InputStream(byte[] buff) {
		return new ByteArrayInputStream(buff);
	}

	/**
	 * 将String 转换为输入流
	 * 
	 * @param string
	 * @return
	 * @throws Exception
	 */
	@SuppressLint("NewApi")
	public static InputStream string2InputStream(String string,
			String charsetName) {
		try {
			if (StringUtil.isEmpty(charsetName))
				return new ByteArrayInputStream(string.getBytes(UTF8));
			return new ByteArrayInputStream(string.getBytes(charsetName));
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	/**
	 * 将数据流转化为文件
	 * 
	 * @param is
	 *            数据流
	 * @param file_path
	 *            文件保存位置
	 * @param file_name
	 *            文件名
	 * @return
	 * @throws Exception
	 */
	@SuppressLint("NewApi")
	public static File inputStream2File(InputStream is, String file_path,
			String file_name) {
		if (file_path == null || file_path.isEmpty())
			throw new RuntimeException("file_path is null or empty");
		if (file_name == null || file_name.isEmpty())
			throw new RuntimeException("file_name is null or empty");
		File path = new File(file_path);
		if (!path.exists())
			path.mkdirs();
		File file = new File(file_path + "/" + file_name);
		try {
			FileOutputStream fos = new FileOutputStream(file_path + "/"
					+ file_name);
			int len = -1;
			byte b[] = new byte[1024];
			while ((len = is.read(b)) > 0) {
				fos.write(b, 0, len);
			}
			fos.flush();
			fos.close();
			is.close();
			return file;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	/**
	 * 将文件转换为数据流
	 * 
	 * @param file
	 *            文件路径（绝对路径）
	 * @return
	 * @throws Exception
	 */
	public static InputStream file2InputStream(File file) {
		if (!FileManagemet.isFile(file))
			throw new RuntimeException(
					"file is null or not exists or directory");
		try {
			return new FileInputStream(file);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

}