package apk.lib;

import java.io.Serializable;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;

import android.content.Context;
import android.support.v4.util.ArrayMap;

public class ApplicationContext implements Serializable{
	/**
	 * 请求URL定义
	 */
	public static ArrayMap<String,String> urlMap;
	
	private RequestQueue requestQueue;
	
	private ImageLoader imageLoader;
	
	private ApplicationContext(){
		urlMap=new ArrayMap<String,String>();
	}
	/**
	 * 域名获取IP
	 */
	public String domain;
	/**
	 * 访问端口
	 */
	public Integer port;
	
	public Context context;
	/**
	 * 拼接访问地址
	 * @param domain
	 * @param port
	 * @param url
	 * @return
	 */
	public String paserAccess(String domain,int port,String url){
		return domain+"://"+url+(port==80?"":":port/");
	}
	
	private static final long serialVersionUID = 4823053526281040989L;
	static class ApplicationContextHolder{
		private static final ApplicationContext _instance=new ApplicationContext();
	}
	
	public static final ApplicationContext getInstance(){
		return ApplicationContextHolder._instance;
	}

	public RequestQueue getRequestQueue() {
		return requestQueue;
	}

	public void setRequestQueue(RequestQueue requestQueue) {
		this.requestQueue = requestQueue;
	}

	public ImageLoader getImageLoader() {
		return imageLoader;
	}

	public void setImageLoader(ImageLoader imageLoader) {
		this.imageLoader = imageLoader;
	}
	
}

