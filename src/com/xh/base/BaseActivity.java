package com.xh.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.xh.interf.IActivity;
import com.xh.interf.IViewAnnotation;
import com.xh.util.DynamicPermission;
import com.xh.util.MainfiestManager;


/**
 * 2018/4/16 16:52
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/

public abstract  class BaseActivity extends Activity implements IActivity {
    private  IViewAnnotation mViewAnnotation;
    private  final  static  int REQUEST_PERMISSION=Integer.MAX_VALUE;

    @SuppressLint("NewApi")
	@Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
          String[]  permisson= MainfiestManager.mainfiest().checkPermission(needPermissions(),getPackageName());
          if(permisson!=null&&permisson.length>0){
        	  requestPermissions(permisson, REQUEST_PERMISSION);
          }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @SuppressLint("NewApi")
	@Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions,  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==REQUEST_PERMISSION){
//            shouldShowRequestPermissionRationale()
        }
    }

    @Override
    public Object getClickReceiver() {
        return this;
    }

    @Override
    public  final  void setViewAnnotation(IViewAnnotation viewAnnotation) {
        mViewAnnotation=viewAnnotation;
    }

    @Override
    public final  void onClick(View v) {
        mViewAnnotation.invoke(v,getClickReceiver());
    }
    /**
     * 2018/4/17 10:50
     * annotation：需要的权限
     * author：liuhuiliang
     * email ：825378291@qq.com
     * 
     *
     */
    public  String[] needPermissions(){
        return  null;
    }
}
