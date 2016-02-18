package apk.lib.utils;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.view.WindowManager;

/**
 * 系统工具
 * 使用网路，开启权限
 * android.permission.INTERNET
 * android.permission.ACCESS_NETWORK_STATE
 * android.permission.CHANGE_NETWORK_STATE
 * android.permission.ACCESS_WIFI_STATE
 * android.permission.CHANGE_WIFI_STATE
 *
 */
public class OSUtils {
	/**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
	/**
	 * 获取手机屏幕尺寸
	 */
	@SuppressWarnings("deprecation")
	public static Point getScreenSize(Context ctx){
		WindowManager wm=(WindowManager)ctx.getSystemService(Context.WINDOW_SERVICE);
		Point point=new Point();
		point.set(wm.getDefaultDisplay().getWidth(), wm.getDefaultDisplay().getHeight());
		return point;
	}
	/**
	 * 判断网络是否可用
	 * @param ctx
	 * @return
	 */
	public static boolean isNetworkConnected(Context ctx) {
		ConnectivityManager connMgr = (ConnectivityManager)
	            ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
	    return (networkInfo != null && networkInfo.isConnected());
	}
	/**
	 * 网络链接类型 wifi
	 */
	public static final int CONNECT_TYPE_WIFI=0;
	/**
	 * 网络链接类型mobile
	 */
	public static final int CONNECT_TYPE_MOBILE=1;
	
	/**
	 * 网络链接类型,无连接
	 */
	public static final int CONNECT_TYPE_NONE=2;
	/**
	 * 获取网络链接类型
	 * @param context
	 * @return
	 */
	public static int connectType(Context context){
		if(isNetworkConnected(context)){
			ConnectivityManager connMgr = (ConnectivityManager)
					context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if(networkInfo.isConnected()){
				return CONNECT_TYPE_WIFI;
			}
			networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			if(networkInfo.isConnected()){
				return CONNECT_TYPE_MOBILE;
			}			 
		}
		return CONNECT_TYPE_NONE;
	}
	/**
	 * 获取mac地址
	 * @param context
	 * @return
	 */
	public String getMacAddress(Context context){
		WifiManager wm = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wm.getConnectionInfo();
		return info.getMacAddress();
	}
	/**
	 * 获取本应用目录
	 * 
	 * @param context
	 * @return
	 */
	public static File getApplicationPath(Context context) {
		return context.getFilesDir();
	}
	/**
	 * 获取应用最大可用内存
	 * @return
	 */
	public long getApplicationMaxMemory(){
		return Runtime.getRuntime().maxMemory();
	}
	/**
	 * 获取本应用缓存目录
	 * 
	 * @param context
	 * @return
	 */
	public static File getCachePath(Context context) {
		return context.getCacheDir();
	}
	/**
	 * 获取应用版本号
	 * @param context
	 * @return
	 */
	public static int getAppVersion(Context context) {  
	    try {  
	        PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);  
	        return info.versionCode;  
	    } catch (NameNotFoundException e) {  
	        e.printStackTrace();  
	    }  
	    return 1;  
	}  
	/**
	 * 获取磁盘缓存路径,如果sdcard不存在,则在cache目录下创建, level>8
	 * @param context 
	 * @param uniqueName 唯一标示,表示目录
	 * @return
	 */
	@SuppressLint("NewApi")
	public static File getDiskCacheDir(Context context, String uniqueName) {  
	    String cachePath;  
	    if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())  
	            || !Environment.isExternalStorageRemovable()) {  
	        cachePath = context.getExternalCacheDir().getPath();  
	    } else {  
	        cachePath = context.getCacheDir().getPath();  
	    }  
	    return new File(cachePath + File.separator + uniqueName);  
	}  
	/**
	 * 获取SDCard访问路径
	 * 
	 * @return
	 * @throws IOException
	 */
	public static File getSdcardPath() throws IOException {
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			File dir = Environment.getExternalStorageDirectory();
			return dir;
		} else {
			throw new IOException("未挂载SDCard");
		}
	}
	/**
	 * 获取手机最大堆内存
	 * @param context
	 * @return
	 */
	public static int getHeapMemorySize(Context context){
		ActivityManager manager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);  
		return manager.getMemoryClass(); 
	}
	public static void close(Closeable c){
		if(c!=null){
			try {
				c.close();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				c=null;
			}
		}
	}
	/**
	 * 执行软件安装
	 */
	public static void startInstallApk(Context ctx,File apkFile){
		if(!apkFile.exists()){
			return;
		}
		// 通过Intent安装APK文件
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + apkFile.toString()), "application/vnd.android.package-archive");
        ctx.startActivity(i);
	}
	
}
