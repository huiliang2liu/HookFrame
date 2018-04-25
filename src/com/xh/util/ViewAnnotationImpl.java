package com.xh.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.View.OnClickListener;

import com.xh.interf.IViewAnnotation;

/**
 * @version 创建时间：2017-12-15 下午4:03:15 项目：repair 包名：com.xh.util
 *          文件名：ViewAnnotationImpl.java 作者：lhl 说明:
 */

public final class ViewAnnotationImpl implements IViewAnnotation {
    private final static String TAG=ViewAnnotationImpl.class.getName();
    private View view;
    private List<ViewMethod> viewMethods;
    private OnClickListener onClickListener;

    public ViewAnnotationImpl(View view, OnClickListener onClickListener) {
        // TODO Auto-generated constructor stub
        this.view = view;
        this.onClickListener = onClickListener;
        viewMethods = new ArrayList<ViewAnnotationImpl.ViewMethod>();
    }

    @Override
    public View id2View(int viewId) {
        // TODO Auto-generated method stub
        return view == null ? null : view.findViewById(viewId);
    }

    @Override
    public void bindClickMethod(View view, Method method) {
        // TODO Auto-generated method stub
        viewMethods.add(new ViewMethod(view, method));
    }

    @Override
    public Object invoke(View view, Object receiver) {
        int dex = viewMethods.indexOf(new ViewMethod(view, null));
        if (dex < 0)
            return null;
        try {
            return viewMethods.get(dex).method.invoke(receiver);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return null;
        }
    }

    private static class ViewMethod {
        View view;
        Method method;

        public ViewMethod(View view, Method method) {
            // TODO Auto-generated constructor stub
            this.view = view;
            this.method = method;
        }

        @Override
        public boolean equals(Object o) {
            // TODO Auto-generated method stub
            if (o == null
                    || !o.getClass().getName().equals(getClass().getName()))
                return false;
            ViewMethod viewMethod = (ViewMethod) o;
            return viewMethod.view == view;
        }
    }

    @Override
    public OnClickListener getOnClickListener() {
        // TODO Auto-generated method stub
        return onClickListener;
    }

}
