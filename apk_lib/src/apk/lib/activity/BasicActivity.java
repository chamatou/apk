package apk.lib.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import apk.lib.log.Logger;
import apk.lib.R;

/**
 * 基础Activity,完成最基本的activity功能
 *
 */
@SuppressLint("NewApi")
public abstract class BasicActivity extends AppCompatActivity {
	private static final Logger logger = Logger.getLogger(BasicActivity.class);
	/**
	 * 键、上一个Activity
	 */
	public static final String KEY_PREVIOUS_ACTIVITY = "key_previous_activity";

	public static final String PREVIOUS_UNKOWN = "unkown";

	protected String previousActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置始终横屏
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		if (getIntent() != null) {
			previousActivity = getIntent()
					.getStringExtra(KEY_PREVIOUS_ACTIVITY);
		}
		logger.d("Get Previous Activity:" + previousActivity);
	}

	/**
	 * 获取上一个Activity
	 */
	public String getPreviousActivity() {
		return previousActivity == null ? PREVIOUS_UNKOWN : previousActivity;
	}

	/**
	 * Activity跳转动画
	 *
	 */
	public class ActivityJumpAnim {
		public int source;
		public int target;
	}

	@Override
	public void startActivity(Intent intent) {
		setPreviousActivity(intent);
		super.startActivity(intent);
		tryJumpAnim();
	}

	@Override
	public void startActivityForResult(Intent intent, int requestCode) {
		setPreviousActivity(intent);
		super.startActivityForResult(intent, requestCode);
		tryJumpAnim();
	}

	private void setPreviousActivity(Intent intent) {
		logger.d("Begin startActivity,Set previous Activity:"
				+ getCurrentActivityName());
		intent.putExtra(KEY_PREVIOUS_ACTIVITY, getCurrentActivityName());
	}

	private void tryJumpAnim() {
		ActivityJumpAnim jump = getJumpAnim();
		if (jump != null) {
			overridePendingTransition(jump.source, jump.target);
		}
	}

	/**
	 * 返回跳转动画
	 */
	public ActivityJumpAnim getJumpAnim() {
		ActivityJumpAnim anim = new ActivityJumpAnim();
		anim.source = R.anim.slide_left_in;
		anim.target = R.anim.slide_left_out;
		return anim;
	}

	/**
	 * 获取资源中的字符串
	 * 
	 * @param resourceid
	 * @return
	 */
	protected String getResourceString(int resourceid) {
		return getResources().getString(resourceid);
	}

	private View.OnClickListener finishActivityListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			finish();
		}
	};

	@SuppressWarnings("unchecked")
	protected <T> T findViewForId(int id) {
		return (T) findViewById(id);
	}

	protected Toolbar setFinishToolbar(int barId, String title)
			throws NullPointerException {
		Toolbar toolbar = (Toolbar) findViewById(barId);
		if(toolbar==null)throw new NullPointerException("Can'not found Toolbar,id:"+barId);
		toolbar.setTitle(title == null ? "" : title);
		ViewCompat.setElevation(toolbar, 20);
		setSupportActionBar(toolbar);
		toolbar.setNavigationIcon(R.mipmap.ic_keyboard_arrow_left_white_36dp);
		toolbar.setNavigationOnClickListener(finishActivityListener);
		return toolbar;
	}
	/**
	 * 获取当前Activity名称
	 * 
	 * @return
	 */
	abstract public String getCurrentActivityName();
}
