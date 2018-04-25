package com.xh.base;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.xh.annotation.ViewAnnotation;
import com.xh.hook.ParasActivityXml;
import com.xh.repair.AMRP;
import com.xh.repair.Load;
import com.xh.test.CircularAnimaActivity;
import com.xh.util.Manager;
import com.xh.util.XhLog;
import com.xh.xhapplication.R;

public class MainActivity extends BaseActivity {
	private final static String TAG = "MainActivity";

	@ViewAnnotation(id = R.id.text_view, clickMethodName = "text")
	private TextView text_view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(layoutId());
		text_view.setText("测试");

		XhLog.e(getResources().getResourceName(R.id.text_view));
		new Thread() {
			public void run() {
				BaseApplication app = (BaseApplication) MainActivity.this
						.getApplication();
				File file = app.sdkOrApkSavePath();
				try {
					FileOutputStream fos = new FileOutputStream(new File(file,
							"demo.apk"));
					XhLog.e("获取文件");
					InputStream is = MainActivity.this.getAssets().open(
							"demo.apk");
					XhLog.e("得到数据流");
					byte[] buff = new byte[1024 * 1024];
					int len = -1;
					while ((len = is.read(buff)) >= 0) {
						fos.write(buff, 0, len);
						XhLog.e("加载中");
					}
					fos.flush();
					fos.close();
					is.close();
					app.load();
					XhLog.e("加载activity.xml");
					Manager.getManager().merge(
							new ParasActivityXml(app.getAssets().open(
									"activity.xml")));
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						// startActivity(new Intent(MainActivity.this,
						// CircularAnimaActivity.class));
						Load load = Load.getLoad();
						AMRP amrp = load.layout2amrp(0x7f030008);
						if (amrp == null)
							XhLog.e("加载挂件失败");
						else
							XhLog.e("加载挂件成功");

					}
				});
			};
		}.start();
	}

	private void text() {
		try {
			startActivity(new Intent(this,
					Class.forName("com.xh.test.MainActivity")));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public int layoutId() {
		return R.layout.activity_main;
	}
	// @Override
	// protected void onCreate(Bundle savedInstanceState) {
	// super.onCreate(savedInstanceState);
	// setContentView(R.layout.activity_main);
	// XhLog.e(TAG,""+(ContentManager.getManager().getActivity()==this));
	// // XhLog.w(TAG,MSG);
	// // XhLog.d(TAG,MSG);
	// XhLog.e(TAG,MSG);
	// // XhLog.i(TAG,MSG);
	// // XhLog.v(TAG,MSG);
	// }
	// //
	// @Override
	// protected void onResume() {
	// super.onResume();
	// XhLog.e(TAG,"onResume");
	// }
}
