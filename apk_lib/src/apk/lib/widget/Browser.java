package apk.lib.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import apk.lib.R;

@SuppressLint("SetJavaScriptEnabled")
public class Browser extends FrameLayout{
	static String TAG="browser";
	//顶部导航条
	private ProgressBar topProgress;
	private WebView webView;	
	private int errorLayoutId;
	private View errorView;
	private boolean isLayout;
	private String loadUrl;
	private SwipeRefreshLayout refresh;
	private CallBack callBack;
	public Browser(Context context){
		this(context,null);
	}
	public interface CallBack{
		/**
		 * 是否在本浏览器中加载指定的url
		 * @param url
		 * @return
		 */
		boolean isLoad(String url);
		/**
		 * 页面加载错误
		 * @param view
		 * @param errorCode
		 * @param description
		 * @param failingUrl
		 */
		public void onReceivedError(WebView view, int errorCode,String description, String failingUrl);
		/**
		 * 页面加载完毕
		 */
		public void onPageFinished(WebView view, String url);
		
	}
	public Browser(Context context, AttributeSet attrs) {
		super(context, attrs);
		FrameLayout.LayoutParams lp=new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
		topProgress= new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
		topProgress.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,8));
		topProgress.setProgressDrawable(getResources().getDrawable(R.drawable.browser_progress_bar));
		webView=new WebView(context);
		webView.setLayoutParams(lp);
		configWebView();
		refresh=new SwipeRefreshLayout(context);
		refresh.setLayoutParams(lp);
		refresh.addView(webView);
		refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				webView.reload();
			}
		});
		isLayout=false;
	}
	
	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		if(!isLayout){
			if(errorLayoutId>0&&errorView==null){
				errorView=LayoutInflater.from(getContext()).inflate(errorLayoutId,this, false);
			}else{
				FrameLayout.LayoutParams lp=new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
				errorView=new TextView(getContext());
				errorView.setLayoutParams(lp);
				TextView tv=(TextView)errorView;
				tv.setText("网络链接失败,点击重新加载");
				tv.setTextSize(16);
				tv.setTextColor(0xff727272);
				tv.setGravity(Gravity.CENTER);
				tv.setBackgroundColor(Color.WHITE);
				tv.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						reload();
					}
				});
			}
			addView(refresh);
			addView(errorView);
			addView(topProgress);
			errorView.setVisibility(GONE);
			isLayout=true;
		}
	}
	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
	}
	
	private void configWebView(){
		//先不用加载图片
		if(Build.VERSION.SDK_INT >= 19) {
	        webView.getSettings().setLoadsImagesAutomatically(true);
	    } else {
	        webView.getSettings().setLoadsImagesAutomatically(false);
	    }
		webView.setWebChromeClient(new TheWebChromeClient());
		webView.setWebViewClient(new TheWebClient());
		webView.getSettings().setJavaScriptEnabled(true);
	}
	
	class TheWebChromeClient extends WebChromeClient {
		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			if(newProgress==100){
				//errorView.setVisibility(GONE);
				Log.d(TAG, "100");
				topProgress.setVisibility(GONE);
			}else{
				if(topProgress.getVisibility()==GONE){
					topProgress.setVisibility(VISIBLE);
				}
				topProgress.setProgress(newProgress);
			}
		}
	}
	
	class TheWebClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url){
			Log.d(TAG, "shouldOverrideUrlLoading");
			boolean isLocalLoad=true;
			if(callBack!=null){
				isLocalLoad=callBack.isLoad(url);
			}
			if(isLocalLoad){
				webView.loadUrl(url);				
				errorView.setVisibility(GONE);
			}
			return isLocalLoad;
		}
		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			Log.d(TAG, "onReceivedError");
			//禁用掉默认错误页面
			webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
			errorView.setVisibility(VISIBLE);
			loadUrl=failingUrl;
			if(callBack!=null){
				callBack.onReceivedError(view, errorCode, description, failingUrl);
			}
		}
		
		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			Log.d(TAG, "onPageFinished");
			//先不用加载图片
			if(!webView.getSettings().getLoadsImagesAutomatically()) {
		        webView.getSettings().setLoadsImagesAutomatically(true);
		    }
			refresh.setRefreshing(false);
			if(callBack!=null){
				callBack.onPageFinished(view, url);
			}
		}
	}
	/**
	 * 设置顶部滑动条
	 * @param topProgress
	 */
	public void setTopProgress(ProgressBar topProgress){
		this.topProgress=topProgress;
	}
	
	public WebSettings getSettings() {
		return webView.getSettings();
	}

	public void loadUrl(String url) {
		webView.loadUrl(url);
	}

	public void reload() {
		if(errorView.getVisibility()==VISIBLE){
			webView.loadUrl(loadUrl);
		}else{
			webView.reload();			
		}
	}

	public boolean canGoBack() {
		return webView.canGoBack();
	}

	public void goBack() {
		webView.goBack();
	}
	/**
	 * 添加出错页面的布局
	 * @param errorLayoutId
	 */
	public void setErrorLayoutId(int errorLayoutId){
		this.errorLayoutId=errorLayoutId;
	}
	/**
	 * 设置下拉刷新加载按钮颜色
	 * @param colors
	 */
	public void setRefreshColorSchemeColors(int... colors) {
		refresh.setColorSchemeColors(colors);
	}
	/**
	 * 添加javascript调用文件
	 * @param object
	 * @param name
	 */
	public void addJavascriptInterface(Object object,String name){
		webView.addJavascriptInterface(object, name);
	}
	/**
	 * 获取title
	 * @return
	 */
	public String getTitle(){
		return webView.getTitle();
	}
}
