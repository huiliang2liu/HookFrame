package com.xh.util;

import android.util.Log;

import java.lang.reflect.Method;


/**
 * XhApplication com.xh.util
 * 2018/4/13 10:24
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/

public class XhLog  {
    private static final String FORMAT = "[TAG] : %s , [MSG] : %s";
    private  static  boolean isDebug=false;
    private static String TAG="XhLog";

    public static void setIsDebug(boolean isDebug) {
        XhLog.isDebug = isDebug;
    }

    /**
     * 2018/4/13 10:35
     * annotation：将tag和msg格式化
     * author：liuhuiliang
     * email ：825378291@qq.com
     *
     *
     */
    private  static  String buildMsg(String tag,String msg){
        return  String.format(FORMAT,tag,msg);
    }
    /**
     * 2018/4/13 10:36
     * annotation：设置tag标志
     * author：liuhuiliang
     * email ：825378291@qq.com
     *
     *
     */
    public static void setTAG(String TAG) {
        XhLog.TAG = TAG;
    }
    /**
     * 2018/4/13 10:48
     * annotation：蓝色日志
     * author：liuhuiliang
     * email ：825378291@qq.com
     *
     *
     */
    public  static  void d(String msg){
    d(null,msg);
    }
    /**
     * 2018/4/13 10:49
     * annotation：蓝色日志
     * author：liuhuiliang
     * email ：825378291@qq.com
     *
     *
     */
    public  static void d(String tag,String msg){
    d(tag,msg,null);
    }
    /**
     * 2018/4/13 10:49
     * annotation：蓝色日志
     * author：liuhuiliang
     * email ：825378291@qq.com
     *
     *
     */
    public  static  void d(String tag,String msg,Throwable er){
        log("d",tag,msg,er);
    }
    /**
     * 2018/4/13 10:53
     * annotation：红色日志
     * author：liuhuiliang
     * email ：825378291@qq.com
     *
     *
     */
    public  static  void e(String msg){
    e(null,msg);
    }
    /**
     * 2018/4/13 10:53
     * annotation：红色日志
     * author：liuhuiliang
     * email ：825378291@qq.com
     *
     *
     */
    public  static  void e(String tag,String msg){
   e(tag,msg,null);
    }
    /**
     * 2018/4/13 10:53
     * annotation：红色日志
     * author：liuhuiliang
     * email ：825378291@qq.com
     *
     *
     */
    public  static  void e(String tag,String msg,Throwable tr){
        log("e",tag,msg,tr);
    }
    /**
     * 2018/4/13 10:56
     * annotation：绿色日志
     * author：liuhuiliang
     * email ：825378291@qq.com
     *
     *
     */
    public  static  void i(String msg){
i(null,msg);
    }
    /**
     * 2018/4/13 10:56
     * annotation：绿色日志
     * author：liuhuiliang
     * email ：825378291@qq.com
     *
     *
     */
    public  static  void i(String tag,String  msg){
i(tag,msg,null);
    }
    /**
     * 2018/4/13 10:56
     * annotation：绿色日志
     * author：liuhuiliang
     * email ：825378291@qq.com
     *
     *
     */
    public  static  void i(String tag,String  msg,Throwable tr){
        log("i",tag,msg,tr);
    }
    /**
     * 2018/4/13 10:58
     * annotation：黑色日志
     * author：liuhuiliang
     * email ：825378291@qq.com
     *
     *
     */
    public  static  void v(String msg){
        v(null,msg);
    }
    /**
     * 2018/4/13 10:58
     * annotation：黑色日志
     * author：liuhuiliang
     * email ：825378291@qq.com
     *
     *
     */
    public  static  void v(String tag,String msg){
        v(tag,msg,null);
    }
    /**
     * 2018/4/13 10:58
     * annotation：黑色日志
     * author：liuhuiliang
     * email ：825378291@qq.com
     *
     *
     */
    public  static  void v(String tag,String msg,Throwable tr){
        log("v",tag,msg,tr);
    }
    /**
     * 2018/4/13 11:00
     * annotation：橙色日志
     * author：liuhuiliang
     * email ：825378291@qq.com
     *
     *
     */
    public  static  void w(String msg){
        w(null,msg);
    }
    /**
     * 2018/4/13 11:00
     * annotation：橙色日志
     * author：liuhuiliang
     * email ：825378291@qq.com
     *
     *
     */
    public  static  void w(String tag,String msg){
        w(tag,msg,null);
    }
    /**
     * 2018/4/13 11:00
     * annotation：橙色日志
     * author：liuhuiliang
     * email ：825378291@qq.com
     *
     *
     */
    public  static  void w(String tag,String msg,Throwable tr){
        log("w",tag,msg,tr);
    }
    /**
     * 2018/4/13 10:53
     * annotation：输出日志
     * author：liuhuiliang
     * email ：825378291@qq.com
     *
     *
     */
   private  static  void log(String methodName,String tag,String msg,Throwable er){
       if(!isDebug)
           return;
       if(msg==null||msg.isEmpty())
           return;
       if(tag==null||msg.isEmpty())
           tag="null";
       msg=buildMsg(tag,msg);
     Class cls=Log.class;
       Class [] types;
       Object[] values;
       if(er==null){
           types=new Class[]{String.class,String.class};
           values=new Object[]{TAG,msg};
       }else{
           types=new Class[]{String.class,String.class,Throwable.class};
           values=new Object[]{TAG,msg,er};
       }
      try {
          Method method=cls.getDeclaredMethod(methodName,types);
          if(!method.isAccessible())
              method.setAccessible(true);
          method.invoke(null, values);
      }catch (Exception ex){
          ex.printStackTrace();
      }
   }
}
