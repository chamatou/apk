package apk.lib.activity;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import apk.lib.R;
import apk.lib.listener.TextWatcherAdapter;
import apk.lib.widget.RoundButton;
/**
 * 用户登陆页
 */
@SuppressLint("NewApi")
public abstract class AbstractUserLoginActivity extends BasicActivity implements View.OnClickListener{
	private RoundButton loginButton;
	private EditText username;
	private EditText password;
	private TextView forgetPassword;
	private TextView userRegist;
	private int textColor;
	private int bgColor;
	
	private TextWatcher twatcher=new  TextWatcherAdapter(){
		@Override
		public void afterTextChanged(Editable s) {
			if(username.getText().length()>0&&password.getText().length()>0){
				loginButton.enableButton(getResources().getColor(R.color.grey_white),getResources().getColor( R.color.primary));
			}else{
				loginButton.disableButton(textColor, bgColor);
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_login);
		loginButton=(RoundButton)findViewById(R.id.loginButton);
		username=findViewForId(R.id.username);
		username.addTextChangedListener(twatcher);
		password=findViewForId(R.id.password);
		password.addTextChangedListener(twatcher);
		forgetPassword=findViewForId(R.id.forgetPassword);
		userRegist=findViewForId(R.id.userRegist);
		setFinishToolbar(R.id.toolbar,null);
		loginButton.setOnClickListener(this);
		textColor=loginButton.getTextColor();
		bgColor=loginButton.getBgColor();
		loginButton.disableButton(textColor, bgColor);
		forgetPassword.setOnClickListener(this);
		userRegist.setOnClickListener(this);
	}
	@Override
	public String getCurrentActivityName() {
		return "用户登陆";
	}
	/**
	 * 注册按钮被按下
	 * @param v
	 */
	abstract protected void userRegistAction(View v);
	/**
	 * 忘记密码按钮被按下
	 * @param v
	 */
	abstract protected void forgetAction(View v);
	/**
	 * 登陆按钮被按下
	 * @param activity
	 * @param view
	 * @param name
	 * @param password
	 */
	abstract protected  void loginButtonAction(View view,String name,String password);
	@Override
	public void onClick(View v) {
		int id=v.getId();
		if(id==R.id.loginButton){
			String name=username.getText().toString();
			String pwd=password.getText().toString();
			loginButtonAction(v, name, pwd);
		}else if(id==R.id.forgetPassword){
			forgetAction(v);
		}else if(id==R.id.userRegist){
			userRegistAction(v);
		}
	}
}
