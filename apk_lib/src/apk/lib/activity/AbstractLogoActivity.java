package apk.lib.activity;

import android.os.Bundle;
import android.view.WindowManager;

public class AbstractLogoActivity extends BasicActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//设置全屏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,   
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		
	}
	@Override
	public String getCurrentActivityName() {
		return "登陆logo";
	}
	/**
	 * 是否第一次使用
	 * @return
	 */
	protected boolean isFirstUsage(){
		return true;
	}
}
