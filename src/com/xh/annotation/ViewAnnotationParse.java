package com.xh.annotation;

import android.annotation.SuppressLint;
import android.view.View;

import com.xh.interf.IViewAnnotation;
import com.xh.reflect.MethodManager;
import com.xh.util.XhLog;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

//import com.xh.interf.IViewAnnotation;

/**
 * @version 创建时间：2017-12-15 下午3:45:05 项目：repair 包名：com.xh.annotation
 *          文件名：ViewAnnotationPasar.java 作者：lhl 说明:
 */

public class ViewAnnotationParse {

    @SuppressLint("NewApi")
    public static void parse(Object clickMethod, IViewAnnotation iViewAnnotation,Object activitys) {
        try {
            Class c = activitys.getClass();
            Field fields[] = c.getDeclaredFields();
            if (fields == null || fields.length <= 0)
                return;
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                if (!View.class.isAssignableFrom(field.getType())
                        || !field.isAnnotationPresent(ViewAnnotation.class))
                    continue;
                ViewAnnotation annotation = field
                        .getAnnotation(ViewAnnotation.class);
                int viewId = annotation.id();
                if (viewId <= 0)
                    continue;
                View view = iViewAnnotation.id2View(viewId);
                if (view == null)
                    continue;
                if (!field.isAccessible())
                    field.setAccessible(true);
                field.set(activitys, view);
                String methedName = annotation.clickMethodName();
                if (methedName == null || methedName.isEmpty())
                    continue;
                Method method;
                try {
                    if (clickMethod != null) {
                        method = MethodManager.method(clickMethod.getClass(),methedName);
//                                clickMethod.getClass().getDeclaredMethod(
//                                methedName);
                    } else {
                        method =MethodManager.method(c,methedName);
                    }
                    if (method == null)
                        continue;
                    if (!method.isAccessible())
                        method.setAccessible(true);
                    view.setOnClickListener(iViewAnnotation
                            .getOnClickListener());
                    iViewAnnotation.bindClickMethod(view, method);
                } catch (Exception e) {
                    // TODO: handle exception
                    System.out.println(field.getName());
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
}