package com.xh.interf;

import java.lang.reflect.Method;

import android.view.View;
import android.view.View.OnClickListener;

/**
 * @version 创建时间：2017-12-15 下午3:46:07 项目：repair 包名：com.xh.ifaces
 *          文件名：IViewAnnotation.java 作者：lhl 说明:
 */

public interface IViewAnnotation {
    View id2View(int viewId);

    void bindClickMethod(View view, Method method);

    Object invoke(View view, Object receiver);

    OnClickListener getOnClickListener();
}