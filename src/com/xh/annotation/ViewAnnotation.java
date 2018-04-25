package com.xh.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * @version 创建时间：2017-12-15 下午3:42:38 项目：repair 包名：com.xh.annotation
 *          文件名：ViewAnnotation.java 作者：lhl 说明:
 */
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.FIELD,
        ElementType.CONSTRUCTOR })
public @interface ViewAnnotation {
    int id() default 0;

    String clickMethodName() default "";
}
