package cn.chamatou.biz.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import apk.lib.BindingUtil;
/**
 * 从GIT提取
 * @author kaiyi
 *
 */
@SuppressLint("NewApi")
public class MainActivity extends AppCompatActivity{
	private Button openLogin;
	private Button openRegist;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*Browser browser=(Browser)findViewById(R.id.browser);
		browser.loadUrl("http://www.chamatou.cn");*/
		BindingUtil.setContentView(this, R.layout.main_activity, R.class);
		openLogin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				openActivity(UserLoginActivity.class);
			}
		});
		openRegist.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//openActivity(UserRegistActivity.class);
			}
		});
	}
	private void openActivity(Class<?> clz){
		Intent intent=new Intent(MainActivity.this,clz);
		startActivity(intent);
	}
}
