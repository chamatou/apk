package apk.lib;

import java.io.File;

import org.litepal.LitePalApplication;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.EntypoModule;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.IoniconsModule;
import com.joanzapata.iconify.fonts.MaterialCommunityModule;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.joanzapata.iconify.fonts.MeteoconsModule;
import com.joanzapata.iconify.fonts.SimpleLineIconsModule;
import com.joanzapata.iconify.fonts.TypiconsModule;
import com.joanzapata.iconify.fonts.WeathericonsModule;

import android.content.Context;
import apk.lib.image.MemoryImageCache;
import apk.lib.utils.OSUtils;

public class DefaultApplication extends LitePalApplication/* 继承LitePalApplication实现数据库的快速访问 */ {
	@Override
	public void onCreate() {
		super.onCreate();
		initIconify();
		initVolley();
		ApplicationContext.getInstance().context = getApplicationContext();
	}
	/**
	 * 初始化initVolley
	 */
	private void initVolley() {
		Context context = getApplicationContext();
		String vollyImageCache = "VollyImageCache";
		File cache = new File(OSUtils.getCachePath(context).getAbsolutePath(), vollyImageCache);
		int cpuSize = Runtime.getRuntime().availableProcessors();
		RequestQueue reqQueue = new RequestQueue(new DiskBasedCache(cache),
				new BasicNetwork(Volley.getDefaultHttpStack(context)),cpuSize);
		MemoryImageCache mCache = new MemoryImageCache();
		// 初始化ImageLoader
		ImageLoader imageLoader = new ImageLoader(reqQueue, mCache);
		// 如果调用Volley.newRequestQueue,那么下面这句可以不用调用
		reqQueue.start();
		ApplicationContext.getInstance().setRequestQueue(reqQueue);
		//TODO 注意在滚动列表这种时候应当停止ImageLoader的加载,并注意列表中图片的错位
		ApplicationContext.getInstance().setImageLoader(imageLoader);
	}

	/**
	 * 加载Iconify支付图标
	 */
	private void initIconify() {
		Iconify.with(new FontAwesomeModule()).with(new EntypoModule()).with(new TypiconsModule())
				.with(new MaterialModule()).with(new MaterialCommunityModule()).with(new MeteoconsModule())
				.with(new WeathericonsModule()).with(new SimpleLineIconsModule()).with(new IoniconsModule());
	}

}
