package com.xh.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;


/**
 * Class管理类
 * @author xh E-mail:825378291@qq.com
 * @version 创建时间：2017-1-21 上午10:18:45
 *
 */
public class ClassManager extends XHReflect{

    /**
     * 获取指定修饰符数组的内部类
     * @param cl
     * @param name
     * @param modifiers
     * @return
     */
    public static Class[] modifiers_classs(Class cl,int[] modifiers){
        if(modifiers==null||modifiers.length==0)
            return null;
        int modifier=0;
        for (int i : modifiers) {
            modifier|=i;
        }
        return modifier_classs(cl, modifier);
    }
    /**
     * 获取指定修饰符数组的内部类
     * @param cl
     * @param name
     * @param modifiers
     * @return
     */
    public static Class[] modifiers_classs(Class cl,Type[] modifiers){
        if(modifiers==null||modifiers.length==0)
            return null;
        int modifier=0;
        for (Type type : modifiers) {
            modifier|=type.get_type();
        }
        return modifier_classs(cl, modifier);
    }
    /**
     * 获取指定修饰符的内部类
     * @param cl
     * @param name
     * @param modifier
     * @return
     */
    public static Class[] modifier_classs(Class cl,Type modifier){
        if(modifier==null)
            return null;
        return modifier_classs(cl, modifier.get_type());
    }
    /**
     * 获取指定修饰符的内部类
     * @param cl
     * @param name
     * @param modifier
     * @return
     */
    public static Class[] modifier_classs(Class cl,int modifier){
        Class[] cs=classs(cl);
        if(cs==null||cs.length==0)
            return null;
        Class[] myClass=new Class[cs.length];
        int len=0;
        for (int i = 0; i < myClass.length; i++) {
            Class c=cs[i];
            if((c.getModifiers()&modifier)!=0){
                myClass[len]=c;
                len++;
            }
        }
        Class[] rc=new Class[len];
        System.arraycopy(myClass, 0, rc, 0, len);
        return rc;
    }
    /**
     * 获取所有公共的内部类
     * @param cl
     * @return
     */
    public static Class[] public_classs(Class cl){
        if(cl==null)
            return null;
        return cl.getClasses();
    }

    /**
     * 获取所有的内部类
     * @param cl
     * @return
     */
    public static Class[] classs(Class cl){
        if(cl==null)
            return null;
        return cl.getDeclaredClasses();
    }
    /**
     * 获取内部类
     * @param cl
     * @param name
     * @return
     */
    public static Class cl(Class cl,String name){
        if(cl==null||name==null||name.isEmpty())
            return null;
        try {
            return Class.forName(cl.getName()+"$"+name);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }
    /**
     * 创建类
     * @param c
     * @return
     * @throws Exception
     */
    public static Object new_object(Class c) throws Exception {
        // TODO Auto-generated catch block
        Constructor[] cs = c.getDeclaredConstructors();
        if (cs != null && cs.length > 0) {
            Constructor constructor = cs[0];
            if (!constructor.isAccessible())
                constructor.setAccessible(true);
            Class[] classes = constructor.getParameterTypes();
            if (classes == null || classes.length == 0) {
                return constructor.newInstance();
            } else {
                Object[] objects = new Object[classes.length];
                for (int i = 0; i < classes.length; i++) {
                    Class class1 = classes[i];
                    Object object = null;
                    if (is_boolean(class1))
                        object = true;
                    else if (is_integer(class1))
                        object = -1;
                    else if (is_char(class1))
                        object = 'a';
                    else if (is_float(class1))
                        object = 1.0f;
                    objects[i] = object;
                }
                return constructor.newInstance(objects);
            }
        }
        return null;
    }
    /**
     * 获取所有的注释
     * author:xh
     * email:825378291@qq.com
     * time:2017-1-22 上午11:12:56
     * @param cl
     * @return
     */
    public static Annotation[] annotations(Class cl){
        if(cl==null)
            return null;
        return cl.getAnnotations();
    }
    /**
     * 获取指定的注释内
     * author:xh
     * email:825378291@qq.com
     * time:2017-1-22 上午11:16:00
     * @param cl
     * @param acl
     * @return
     */
    public static Annotation annotation(Class cl,Class<? extends Annotation> acl){
        if(cl==null||acl==null||!cl.isAnnotationPresent(acl))
            return null;
        return cl.getAnnotation(acl);
    }
    /**
     * 获取父类所有泛型
     * author:xh
     * email:825378291@qq.com
     * time:2017-1-22 上午11:23:01
     * @param cl
     * @return
     */
    public static java.lang.reflect.Type[] generics_super(Class cl){
        if(cl==null)
            return null;
        java.lang.reflect.Type type=cl.getGenericSuperclass();
        if(type instanceof ParameterizedType){
            return ((ParameterizedType) type).getActualTypeArguments();
        }
        return null;
    }
    /**
     * 获取父类泛型
     * author:xh
     * email:825378291@qq.com
     * time:2017-1-22 上午11:24:40
     * @param cl
     * @return
     */
    public static Class generic_super(Class cl){
        java.lang.reflect.Type[] cls= generics_super(cl);
        if(cls==null||cls.length==0)
            return null;
        if(cls[0] instanceof Class)
            return (Class) cls[0];
        return null;
    }
}