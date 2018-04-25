package com.xh.util;

import android.app.Activity;
import android.app.Application;

import com.xh.reflect.FieldManager;
import com.xh.reflect.MethodManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 2018/4/13 18:46
 * instructions：上下文管理
 * author:liuhuiliang  email:825378291@qq.com
 **/

public class ContentManager {
    private  static  ContentManager mManager;
    private Application mApplication;
    private Activity  mActivity;
/**
 * 2018/4/13 18:57
 * annotation：获取application对象
 * author：liuhuiliang
 * email ：825378291@qq.com
 *
 *
 */
    public Application getApplication() {
        return mApplication;
    }
/**
 * 2018/4/13 18:57
 * annotation：获取当前
 * author：liuhuiliang
 * email ：825378291@qq.com
 *
 *
 */
    public Activity getActivity() {
        return mActivity;
    }

    private  ContentManager(){
mApplication=field2Application();
    }
    /**
     * 2018/4/13 18:55
     * annotation：上下文管理
     * author：liuhuiliang
     * email ：825378291@qq.com
     *
     *
     */
    public  static  ContentManager getManager(){
        if(mManager==null){
             synchronized (ContentManager.class){
                  if(mManager==null)
                     mManager=new ContentManager();
                                                }
                           }
        return  mManager;
                                               }
    /**
     * 2018/4/16 10:19
     * annotation：通过mInitialApplication获取application
     * author：liuhuiliang
     * email ：825378291@qq.com
     *
     *
     */
    private Application field2Application(){
        Application application=null;
        try {
            Class actvivtyThreadClass=Class.forName("android.app.ActivityThread");
            Field mInitialApplicationField= FieldManager.field(actvivtyThreadClass,"mInitialApplication");
            Method currentActivityThreadMethod= MethodManager.method(actvivtyThreadClass,"currentActivityThread");
            Object activityThread=currentActivityThreadMethod.invoke(null);
            application= (Application) FieldManager.get_field(activityThread,mInitialApplicationField);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  application;
    }
    /**
     * 2018/4/16 10:19
     * annotation：通过getApplication放过获取application
     * author：liuhuiliang
     * email ：825378291@qq.com
     *
     *
     */
  private  Application method2Application(){
      Application application= null;
      try {
          Class activityThreadClass= Class.forName("android.app.ActivityThread");
          Method currentActivityThreadMethod=MethodManager.method(activityThreadClass,"currentActivityThread");
          Object activityThread=currentActivityThreadMethod.invoke(null);
          Method getApplicationMethod=MethodManager.method(activityThreadClass,"getApplication");
          application= (Application) getApplicationMethod.invoke(activityThread);
      } catch (Exception e) {
          e.printStackTrace();
      }
      return  application;
  }
}
