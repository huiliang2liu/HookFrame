package com.xh.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import com.xh.reflect.MethodManager;

/**
 * 2018/4/13 18:45
 * instructions：清单文件管理
 * author:liuhuiliang  email:825378291@qq.com
 **/

public class MainfiestManager {
    private static  MainfiestManager mMainfiestManager;
    private PackageManager packageManager;
    public static enum Type {
        SERVICE, RECEIVER, ACTIVITY, APPLICATION
    }

    public  static  MainfiestManager mainfiest(){
        if(mMainfiestManager==null){
            synchronized (MainfiestManager.class){
                if(mMainfiestManager==null)
                    mMainfiestManager=new MainfiestManager();
            }
        }
        return  mMainfiestManager;
    }
    private  MainfiestManager(){
        packageManager=ContentManager.getManager().getApplication().getPackageManager();
    }
    /**
     * 获取包名
     *
     * @return
     */
    public String package_name() {
        return ContentManager.getManager().getApplication().getPackageName();
    }

    /**
     * 获取版本名称
     *
     * @return
     */
    public String version_name() {
        try {
            return packageManager.getPackageInfo(package_name(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
        }
        return null;
    }

    /**
     * 获取版本号
     *
     * @return
     */
    public int version_code() {
        try {
            return packageManager.getPackageInfo(package_name(), 0).versionCode;
        } catch (Exception e) {
            // TODO: handle exception
        }
        return -1;
    }

    /**
     * 获取图标
     *
     * @return
     */
    public Drawable getAppIcon() {
        try {
            return packageManager.getApplicationInfo(package_name(), 0)
                    .loadIcon(packageManager);
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block

        }
        return null;
    }
    /**
     * 2018/4/17 11:15
     * annotation：检查是否含有改权限
     * author：liuhuiliang
     * email ：825378291@qq.com
     *
     *
     */
   @SuppressLint("NewApi")
public boolean checkPermission(String permission,String packageName){
       if(permission==null||permission.isEmpty()||packageName==null||packageName.isEmpty())
           return  false;
       return  PackageManager.PERMISSION_GRANTED ==
               packageManager.checkPermission(permission,packageName);
   }
    /**
     * 2018/4/17 11:47
     * annotation：检查权限是否申请成功
     * author：liuhuiliang
     * email ：825378291@qq.com
     *
     *
     */
    public  boolean shouldShowRequestPermissionRationale(String permission){
        if(permission==null)
            return false;
        Method shouldShowRequestPermissionRationaleMethod= MethodManager.method(packageManager.getClass(),"shouldShowRequestPermissionRationale",new Class[]{String.class});
        if(shouldShowRequestPermissionRationaleMethod!=null)
            return (boolean) MethodManager.invoke(shouldShowRequestPermissionRationaleMethod,packageManager,new Object[]{permission});
        return  false;
    }
    /**
     * 2018/4/17 11:52
     * annotation：检测权限是否申请成功，并且把没有成功的返回
     * author：liuhuiliang
     * email ：825378291@qq.com
     *
     *
     */
    public  String[] shouldShowRequestPermissionRationale(String[] permissions){
       if(permissions==null||permissions.length<=0)
           return  permissions;
        List<String> notPermissions=new ArrayList<>(permissions.length);
        for (int i=0;i<permissions.length;i++){
            String permission=permissions[i];
            if(shouldShowRequestPermissionRationale(permission))
                continue;
            notPermissions.add(permission);
        }
        return notPermissions.toArray(new String[notPermissions.size()] );
    }
    /**
     * 2018/4/17 11:15
     * annotation：检查是否包含这组权限，并且将没有的权限返回
     * author：liuhuiliang
     * email ：825378291@qq.com
     *
     *
     */
    @SuppressLint("NewApi")
	public  String[]  checkPermission(String[] permissions,String packageName){
        if(permissions==null||permissions.length<=0||packageName==null||packageName.isEmpty())
            return  permissions;
        List<String> checkPermissions=new ArrayList<>(permissions.length);
        for(int i=0;i<permissions.length;i++){
            String permission=permissions[i];
           if(checkPermission(permission,packageName))
               continue;
            checkPermissions.add(permission);
        }
          return checkPermissions.toArray(new String[checkPermissions.size()]);
    }
    /**
     * 获取程序的权限
     */
    public String[] getAppPremission() {
        try {
            return packageManager.getPackageInfo(package_name(),
                    PackageManager.GET_PERMISSIONS).requestedPermissions;

        } catch (PackageManager.NameNotFoundException e) {

        }
        return null;
    }

    /**
     * 获取程序的签名
     */
    public String getAppSignature() {
        try {
            return packageManager.getPackageInfo(package_name(),
                    PackageManager.GET_SIGNATURES).signatures[0]
                    .toCharsString();

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        }
        return "";
    }

    /**
     * 获取application 中的meta—data的值
     *
     * @param meta_data
     *            名称
     * @return
     */
    public String meta_data(String meta_data) {
        return meta_data(Type.APPLICATION, meta_data, null);
    }

    /**
     * 获取meta-data的值
     *
     * @param type
     *            类型
     * @param meta_data
     *            名称
     * @param class_name
     *            类名
     * @return
     */
    public String meta_data(Type type, String meta_data, String class_name) {
        ComponentName cn = null;
        if (class_name != null)
            cn = new ComponentName(package_name(), class_name);
        String data = null;
        try {
            switch (type) {
                case APPLICATION:
                    data = packageManager.getApplicationInfo(package_name(),
                            PackageManager.GET_META_DATA).metaData
                            .getString(meta_data);
                    break;
                case ACTIVITY:
                    data = packageManager.getActivityInfo(cn,
                            PackageManager.GET_META_DATA).metaData
                            .getString(meta_data);
                    break;
                case RECEIVER:
                    data = packageManager.getReceiverInfo(cn,
                            PackageManager.GET_META_DATA).metaData
                            .getString(meta_data);
                    break;
                case SERVICE:
                    data = packageManager.getServiceInfo(cn,
                            PackageManager.GET_META_DATA).metaData
                            .getString(meta_data);
                    break;
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return data;
    }
}
