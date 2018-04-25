package com.xh.test;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.TextView;

import com.xh.annotation.ViewAnnotation;
import com.xh.base.BaseActivity;
import com.xh.circular.CircularAnim;
import com.xh.xhapplication.R;

/**
 * 2018/4/16 17:21 instructions： author:liuhuiliang email:825378291@qq.com
 **/

public class CircularAnimaActivity extends BaseActivity {
	@ViewAnnotation(id = R.id.test_activity_text_view, clickMethodName = "hide")
	private TextView text_view;
	@ViewAnnotation(id = R.id.test_activity_text_view1, clickMethodName = "show")
	private View test_activity_text_view1;
	@ViewAnnotation(id = R.id.test_activity_text_viewend, clickMethodName = "end")
	private View test_activity_text_viewend;

	private void show() {
		CircularAnim.show(text_view).triggerView(test_activity_text_view1).go();
	}

	private void end() {
		CircularAnim.hide(text_view).endRadius(text_view.getHeight() / 2)
				.deployAnimator(new CircularAnim.OnAnimatorDeployListener() {
					@SuppressLint("NewApi")
					@Override
					public void deployAnimator(Animator animator) {
						animator.setDuration(1200L);
						animator.setInterpolator(new AccelerateInterpolator());
					}
				}).go();
	}

	private void hide() {
		text_view.setText("未注册应用点击测试");
		CircularAnim.hide(text_view).triggerView(test_activity_text_view1).go();
		// CircularAnim.fullActivity(CircularAnimaActivity.this, text_view)
		// .colorOrImageRes(R.mipmap.img_huoer_black)
		// .go(new CircularAnim.OnAnimationEndListener() {
		// @Override
		// public void onAnimationEnd() {
		// startActivity(new Intent(CircularAnimaActivity.this,
		// MainActivity.class));
		// }
		// });
	}

	@Override
	public int layoutId() {
		// return 0x7f030008;
		return R.layout.test_activity;
	}
}
