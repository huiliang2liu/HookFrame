package com.xh.interf;

import android.view.View;

import static android.R.attr.onClick;

/**
 * 2018/4/16 12:40
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/

public interface IActivity extends View.OnClickListener{
    /**
     * 2018/4/16 12:42
     * annotation：布局id
     * author：liuhuiliang
     * email ：825378291@qq.com
     *
     *
     */
    int layoutId();
    /**
     * 2018/4/16 16:36
     * annotation：布局管理
     * author：liuhuiliang
     * email ：825378291@qq.com
     *
     *
     */
    void setViewAnnotation(IViewAnnotation viewAnnotation);

    /**
     * 2018/4/16 16:46
     * annotation：获取点击执行者
     * author：liuhuiliang
     * email ：825378291@qq.com
     *
     *
     */
    Object getClickReceiver();
}
