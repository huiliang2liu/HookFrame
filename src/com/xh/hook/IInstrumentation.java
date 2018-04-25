package com.xh.hook;

import android.app.Activity;
import android.app.Application;
import android.app.Instrumentation;
import android.app.UiAutomation;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.view.KeyEvent;
import android.view.MotionEvent;

/**
 * 2018/4/16 11:41
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/

public interface IInstrumentation {

    public void onCreate(Bundle arguments);

    public void start() ;

    public void onStart();

    public boolean onException(Object obj, Throwable e);

    public void sendStatus(int resultCode, Bundle results);

    public void finish(int resultCode, Bundle results);

    public void setAutomaticPerformanceSnapshots() ;

    public void startPerformanceSnapshot() ;

    public void endPerformanceSnapshot();

    public void onDestroy() ;

    public Context getContext();

    public ComponentName getComponentName() ;

    public Context getTargetContext() ;

    public boolean isProfiling() ;

    public void startProfiling() ;

    public void stopProfiling();

    public void setInTouchMode(boolean inTouch);

    public void waitForIdle(Runnable recipient);

    public void waitForIdleSync() ;

    public void runOnMainSync(Runnable runner) ;

    public Activity startActivitySync(Intent intent);

    public void addMonitor(Instrumentation.ActivityMonitor monitor);

    public Instrumentation.ActivityMonitor addMonitor(IntentFilter filter,
                                                      Instrumentation.ActivityResult result, boolean block);

    public Instrumentation.ActivityMonitor addMonitor(String cls, Instrumentation.ActivityResult result,
                                                      boolean block) ;

    public boolean checkMonitorHit(Instrumentation.ActivityMonitor monitor, int minHits);

    public Activity waitForMonitor(Instrumentation.ActivityMonitor monitor);

    public Activity waitForMonitorWithTimeout(Instrumentation.ActivityMonitor monitor,
                                              long timeOut);

    public void removeMonitor(Instrumentation.ActivityMonitor monitor) ;

    public boolean invokeMenuActionSync(Activity targetActivity, int id,
                                        int flag);

    public boolean invokeContextMenuAction(Activity targetActivity, int id,
                                           int flag) ;

    public void sendStringSync(String text);

    public void sendKeySync(KeyEvent event) ;

    public void sendKeyDownUpSync(int key);

    public void sendCharacterSync(int keyCode) ;

    public void sendPointerSync(MotionEvent event);

    public void sendTrackballEventSync(MotionEvent event);

    public Application newApplication(ClassLoader cl, String className,
                                      Context context) throws InstantiationException,
            IllegalAccessException, ClassNotFoundException ;

    public void callApplicationOnCreate(Application app);

    public Activity newActivity(Class<?> clazz, Context context, IBinder token,
                                Application application, Intent intent, ActivityInfo info,
                                CharSequence title, Activity parent, String id,
                                Object lastNonConfigurationInstance) throws InstantiationException,
            IllegalAccessException ;

    public Activity newActivity(ClassLoader cl, String className, Intent intent)
            throws InstantiationException, IllegalAccessException,
            ClassNotFoundException ;

    public void callActivityOnCreate(Activity activity, Bundle icicle) ;

    public void callActivityOnCreate(Activity activity, Bundle icicle,
                                     PersistableBundle persistentState) ;

    public void callActivityOnDestroy(Activity activity) ;

    public void callActivityOnRestoreInstanceState(Activity activity,
                                                   Bundle savedInstanceState) ;

    public void callActivityOnRestoreInstanceState(Activity activity,
                                                   Bundle savedInstanceState, PersistableBundle persistentState) ;

    public void callActivityOnPostCreate(Activity activity, Bundle icicle) ;

    public void callActivityOnPostCreate(Activity activity, Bundle icicle,
                                         PersistableBundle persistentState) ;

    public void callActivityOnNewIntent(Activity activity, Intent intent);

    public void callActivityOnStart(Activity activity);

    public void callActivityOnRestart(Activity activity);

    public void callActivityOnResume(Activity activity) ;

    public void callActivityOnStop(Activity activity) ;

    public void callActivityOnSaveInstanceState(Activity activity,
                                                Bundle outState) ;
       public void callActivityOnSaveInstanceState(Activity activity,
                                                Bundle outState, PersistableBundle outPersistentState);
    public void callActivityOnPause(Activity activity);

    public void callActivityOnUserLeaving(Activity activity) ;

    public void startAllocCounting();

    public void stopAllocCounting() ;

    public Bundle getAllocCounts() ;

    public Bundle getBinderCounts() ;

    public UiAutomation getUiAutomation();
}
