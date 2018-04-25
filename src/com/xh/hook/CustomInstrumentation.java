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
 * Hook com.xh.hook
 * 2018 2018-4-12 下午3:39:15
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/

public class CustomInstrumentation extends Instrumentation implements IInstrumentation{
  //  private  final  static  String TAG="CustomInstrumentation";
    private IInstrumentation mInstrumentation;
    public CustomInstrumentation(IInstrumentation instrumentation) {
        // TODO Auto-generated constructor stub
        mInstrumentation=instrumentation;
    }
    @Override
    public void onCreate(Bundle arguments) {
        // TODO Auto-generated method stub
        mInstrumentation.onCreate(arguments);
    }
    @Override
    public void start() {
        // TODO Auto-generated method stub
        mInstrumentation.start();
    }
    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        mInstrumentation.onStart();
    }
    @Override
    public boolean onException(Object obj, Throwable e) {
        // TODO Auto-generated method stub
        return mInstrumentation.onException(obj, e);
    }
    @Override
    public void sendStatus(int resultCode, Bundle results) {
        // TODO Auto-generated method stub
        mInstrumentation.sendStatus(resultCode, results);
    }
    @Override
    public void finish(int resultCode, Bundle results) {
        // TODO Auto-generated method stub
        mInstrumentation.finish(resultCode, results);
    }
    @Override
    public void setAutomaticPerformanceSnapshots() {
        // TODO Auto-generated method stub
        mInstrumentation.setAutomaticPerformanceSnapshots();
    }
    @Override
    public void startPerformanceSnapshot() {
        // TODO Auto-generated method stub
        mInstrumentation.startPerformanceSnapshot();
    }
    @Override
    public void endPerformanceSnapshot() {
        // TODO Auto-generated method stub
        mInstrumentation.endPerformanceSnapshot();
    }
    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        mInstrumentation.onDestroy();
    }
    @Override
    public Context getContext() {
        // TODO Auto-generated method stub
        return mInstrumentation.getContext();
    }
    @Override
    public ComponentName getComponentName() {
        // TODO Auto-generated method stub
        return mInstrumentation.getComponentName();
    }
    @Override
    public Context getTargetContext() {
        // TODO Auto-generated method stub
        return mInstrumentation.getTargetContext();
    }
    @Override
    public boolean isProfiling() {
        // TODO Auto-generated method stub
        return mInstrumentation.isProfiling();
    }
    @Override
    public void startProfiling() {
        // TODO Auto-generated method stub
        mInstrumentation.startProfiling();
    }
    @Override
    public void stopProfiling() {
        // TODO Auto-generated method stub
        mInstrumentation.stopProfiling();
    }
    @Override
    public void setInTouchMode(boolean inTouch) {
        // TODO Auto-generated method stub
        mInstrumentation.setInTouchMode(inTouch);
    }
    @Override
    public void waitForIdle(Runnable recipient) {
        // TODO Auto-generated method stub
        mInstrumentation.waitForIdle(recipient);
    }
    @Override
    public void waitForIdleSync() {
        // TODO Auto-generated method stub
        mInstrumentation.waitForIdleSync();
    }
    @Override
    public void runOnMainSync(Runnable runner) {
        // TODO Auto-generated method stub
        mInstrumentation.runOnMainSync(runner);
    }
    @Override
    public Activity startActivitySync(Intent intent) {
        // TODO Auto-generated method stub
        return mInstrumentation.startActivitySync(intent);
    }
    @Override
    public void addMonitor(ActivityMonitor monitor) {
        // TODO Auto-generated method stub
        mInstrumentation.addMonitor(monitor);
    }
    @Override
    public ActivityMonitor addMonitor(IntentFilter filter,
                                      ActivityResult result, boolean block) {
        // TODO Auto-generated method stub
        return mInstrumentation.addMonitor(filter, result, block);
    }
    @Override
    public ActivityMonitor addMonitor(String cls, ActivityResult result,
                                      boolean block) {
        // TODO Auto-generated method stub
        return mInstrumentation.addMonitor(cls, result, block);
    }
    @Override
    public boolean checkMonitorHit(ActivityMonitor monitor, int minHits) {
        // TODO Auto-generated method stub
        return mInstrumentation.checkMonitorHit(monitor, minHits);
    }
    @Override
    public Activity waitForMonitor(ActivityMonitor monitor) {
        // TODO Auto-generated method stub
        return mInstrumentation.waitForMonitor(monitor);
    }
    @Override
    public Activity waitForMonitorWithTimeout(ActivityMonitor monitor,
                                              long timeOut) {
        // TODO Auto-generated method stub
        return mInstrumentation.waitForMonitorWithTimeout(monitor, timeOut);
    }
    @Override
    public void removeMonitor(ActivityMonitor monitor) {
        // TODO Auto-generated method stub
        mInstrumentation.removeMonitor(monitor);
    }
    @Override
    public boolean invokeMenuActionSync(Activity targetActivity, int id,
                                        int flag) {
        // TODO Auto-generated method stub
        return mInstrumentation.invokeMenuActionSync(targetActivity, id, flag);
    }
    @Override
    public boolean invokeContextMenuAction(Activity targetActivity, int id,
                                           int flag) {
        // TODO Auto-generated method stub
        return mInstrumentation.invokeContextMenuAction(targetActivity, id, flag);
    }
    @Override
    public void sendStringSync(String text) {
        // TODO Auto-generated method stub
        mInstrumentation.sendStringSync(text);
    }
    @Override
    public void sendKeySync(KeyEvent event) {
        // TODO Auto-generated method stub
        mInstrumentation.sendKeySync(event);
    }
    @Override
    public void sendKeyDownUpSync(int key) {
        // TODO Auto-generated method stub
        mInstrumentation.sendKeyDownUpSync(key);
    }
    @Override
    public void sendCharacterSync(int keyCode) {
        // TODO Auto-generated method stub
        mInstrumentation.sendCharacterSync(keyCode);
    }
    @Override
    public void sendPointerSync(MotionEvent event) {
        // TODO Auto-generated method stub
        mInstrumentation.sendPointerSync(event);
    }
    @Override
    public void sendTrackballEventSync(MotionEvent event) {
        // TODO Auto-generated method stub
        mInstrumentation.sendTrackballEventSync(event);
    }
    @Override
    public Application newApplication(ClassLoader cl, String className,
                                      Context context) throws InstantiationException,
            IllegalAccessException, ClassNotFoundException {
        // TODO Auto-generated method stub
        return mInstrumentation.newApplication(cl, className, context);
    }
    @Override
    public void callApplicationOnCreate(Application app) {
        // TODO Auto-generated method stub
        mInstrumentation.callApplicationOnCreate(app);
    }
    @Override
    public Activity newActivity(Class<?> clazz, Context context, IBinder token,
                                Application application, Intent intent, ActivityInfo info,
                                CharSequence title, Activity parent, String id,
                                Object lastNonConfigurationInstance) throws InstantiationException,
            IllegalAccessException {
        // TODO Auto-generated method stub
//        Log.e("newActivity", context.getClass().getName());
        return mInstrumentation.newActivity(clazz, context, token, application, intent, info,
                title, parent, id, lastNonConfigurationInstance);
    }
    @Override
    public Activity newActivity(ClassLoader cl, String className, Intent intent)
            throws InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        // TODO Auto-generated method stub
        return mInstrumentation.newActivity(cl, className, intent);
    }
    @Override
    public void callActivityOnCreate(Activity activity, Bundle icicle) {
        // TODO Auto-generated method stub
        //XhLog.e(TAG,"call activity on create");
        mInstrumentation.callActivityOnCreate(activity, icicle);
    }
    @Override
    public void callActivityOnCreate(Activity activity, Bundle icicle,
                                     PersistableBundle persistentState) {
        // TODO Auto-generated method stub
        mInstrumentation.callActivityOnCreate(activity, icicle, persistentState);
    }
    @Override
    public void callActivityOnDestroy(Activity activity) {
        // TODO Auto-generated method stub
        mInstrumentation.callActivityOnDestroy(activity);
    }
    @Override
    public void callActivityOnRestoreInstanceState(Activity activity,
                                                   Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        mInstrumentation.callActivityOnRestoreInstanceState(activity, savedInstanceState);
    }
    @Override
    public void callActivityOnRestoreInstanceState(Activity activity,
                                                   Bundle savedInstanceState, PersistableBundle persistentState) {
        // TODO Auto-generated method stub
        mInstrumentation.callActivityOnRestoreInstanceState(activity, savedInstanceState,
                persistentState);
    }
    @Override
    public void callActivityOnPostCreate(Activity activity, Bundle icicle) {
        // TODO Auto-generated method stub
        mInstrumentation.callActivityOnPostCreate(activity, icicle);
    }
    @Override
    public void callActivityOnPostCreate(Activity activity, Bundle icicle,
                                         PersistableBundle persistentState) {
        // TODO Auto-generated method stub
        mInstrumentation.callActivityOnPostCreate(activity, icicle, persistentState);
    }
    @Override
    public void callActivityOnNewIntent(Activity activity, Intent intent) {
        // TODO Auto-generated method stub
        mInstrumentation.callActivityOnNewIntent(activity, intent);
    }
    @Override
    public void callActivityOnStart(Activity activity) {
        // TODO Auto-generated method stub
        mInstrumentation.callActivityOnStart(activity);
    }
    @Override
    public void callActivityOnRestart(Activity activity) {
        // TODO Auto-generated method stub
        mInstrumentation.callActivityOnRestart(activity);
    }
    @Override
    public void callActivityOnResume(Activity activity) {
        // TODO Auto-generated method stub
        mInstrumentation.callActivityOnResume(activity);
    }
    @Override
    public void callActivityOnStop(Activity activity) {
        // TODO Auto-generated method stub
        mInstrumentation.callActivityOnStop(activity);
    }
    @Override
    public void callActivityOnSaveInstanceState(Activity activity,
                                                Bundle outState) {
        // TODO Auto-generated method stub
        mInstrumentation.callActivityOnSaveInstanceState(activity, outState);
    }
    @Override
    public void callActivityOnSaveInstanceState(Activity activity,
                                                Bundle outState, PersistableBundle outPersistentState) {
        // TODO Auto-generated method stub
        mInstrumentation.callActivityOnSaveInstanceState(activity, outState, outPersistentState);
    }
    @Override
    public void callActivityOnPause(Activity activity) {
        // TODO Auto-generated method stub
        mInstrumentation.callActivityOnPause(activity);
    }
    @Override
    public void callActivityOnUserLeaving(Activity activity) {
        // TODO Auto-generated method stub
        mInstrumentation.callActivityOnUserLeaving(activity);
    }
    @Override
    @Deprecated
    public void startAllocCounting() {
        // TODO Auto-generated method stub
        mInstrumentation.startAllocCounting();
    }
    @Override
    @Deprecated
    public void stopAllocCounting() {
        // TODO Auto-generated method stub
        mInstrumentation.stopAllocCounting();
    }
    @Override
    public Bundle getAllocCounts() {
        // TODO Auto-generated method stub
        return mInstrumentation.getAllocCounts();
    }
    @Override
    public Bundle getBinderCounts() {
        // TODO Auto-generated method stub
        return mInstrumentation.getBinderCounts();
    }
    @Override
    public UiAutomation getUiAutomation() {
        // TODO Auto-generated method stub
        return mInstrumentation.getUiAutomation();
    }

}
