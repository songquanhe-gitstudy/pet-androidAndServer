package com.song.petLeague;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Environment;

import org.xutils.x;

import java.io.File;

import io.rong.imkit.RongIM;

/**
 * 
* @ClassName: MyApplication 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author yiw
* @date 2015-12-28 下午4:21:08 
*
 */
public class MyApplication extends Application {
	// 默认存放图片的路径
	public final static String DEFAULT_SAVE_IMAGE_PATH = Environment.getExternalStorageDirectory() + File.separator + "CircleDemo" + File.separator + "Images"
				+ File.separator;

	private static Context mContext;

	@Override
	public void onCreate() {
		super.onCreate();
		mContext = getApplicationContext();
		//LeakCanary.install(this);
		//初始化xutils的操作
		x.Ext.init(this);
		if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext())) ||
				"io.rong.push".equals(getCurProcessName(getApplicationContext()))) {

			/**
			 * IMKit SDK调用第一步 初始化
			 */
			RongIM.init(this);
		}
	}

	public static Context getContext(){
		return mContext;
	}

	/**
	 * 获得当前进程的名字
	 *
	 * @param context
	 * @return 进程号
	 */
	public static String getCurProcessName(Context context) {

		int pid = android.os.Process.myPid();

		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);

		for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
				.getRunningAppProcesses()) {

			if (appProcess.pid == pid) {
				return appProcess.processName;
			}
		}
		return null;
	}


}
