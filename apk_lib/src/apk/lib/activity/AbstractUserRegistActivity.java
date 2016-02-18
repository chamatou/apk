package apk.lib.activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;
import apk.lib.fragment.FragmentAction;
import apk.lib.fragment.UserRegistOneStepFragment;
import apk.lib.fragment.UserRegistSecondStepFragment;
import apk.lib.fragment.UserRegistThirdStepFragment;
import apk.lib.R;
public abstract class AbstractUserRegistActivity extends BasicActivity implements FragmentAction{
	private FragmentManager fm;
	private UserRegistOneStepFragment stepOne;
	private UserRegistSecondStepFragment stepSecond;
	private UserRegistThirdStepFragment stepThird;
	private TextView[] textRegistSteps;
	private int currPage;
	//数据传递定义
	public static final String OPER_KEY="0";
	//1.第一个页面
	/**
	 * 电话号码
	 */
	public static final String KEY_PHONE_NUMBER="0";
	/**
	 * 验证码
	 */
	public static final String KEY_VALID_CODE="1";
	/**
	 * 密码
	 */
	public static final String KEY_PASSWORD="2";
	//3.第三个页面
	//操作定义
	/**
	 * 进入下一页面
	 */
	public static final int OPER_NEXT=0;
	/**
	 * 打开浏览器
	 */
	public static final int OPER_OPEN_BROWSER=1;
	/**
	 * 发送验证码
	 */
	public static final int OPER_SEND_CODE=2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_regist);
		setFinishToolbar(R.id.toolbar,null);
		textRegistSteps=new TextView[]{findViewForId(R.id.textRegistStep1),findViewForId(R.id.textRegistStep2),findViewForId(R.id.textRegistStep3)};
		selectRegistStep(R.id.textRegistStep1);
		fm=getSupportFragmentManager();
		FragmentTransaction tran=fm.beginTransaction();
		stepOne=new UserRegistOneStepFragment();
		stepSecond=new UserRegistSecondStepFragment();
		stepThird=new UserRegistThirdStepFragment();
		tran.replace(R.id.fragmentContent, stepOne);
		tran.commit();
		currPage=1;
	}
	
	private void selectRegistStep(int id){
		for(TextView views:textRegistSteps){
			if(views.getId()==id){
				views.setTextColor(getResources().getColor(R.color.primary));
			}else{
				views.setTextColor(getResources().getColor(R.color.grey_600));
			}
		}
	}
	@Override
	public String getCurrentActivityName() {
		return "用户注册";
	}
	@Override
	public void action(Fragment fragment, View view, int type, Bundle bundle) {
		if(fragment==stepOne){
			if(bundle.getInt(OPER_KEY)==OPER_OPEN_BROWSER){
				readUserProtol();
			}else if(bundle.getInt(OPER_KEY)==OPER_NEXT){
				String phone=bundle.getString(AbstractUserRegistActivity.KEY_PHONE_NUMBER);
				phoneSuccess(phone);
			}
		}else if(fragment==stepSecond){
			if(bundle.getInt(OPER_KEY)==OPER_SEND_CODE){
				resendCode();
			}else if(bundle.getInt(OPER_KEY)==OPER_NEXT){
				String code=bundle.getString(AbstractUserRegistActivity.KEY_VALID_CODE);
				codeSuccess(code);
			}
		}else if(fragment==stepThird){
			String passwd=bundle.getString(AbstractUserRegistActivity.KEY_PASSWORD);
			passwordSuccess(passwd);
		}
	}
	protected void nextPage(){
		if(currPage==1){
			changeStep(stepSecond, R.id.textRegistStep2);
			currPage++;
		}else if(currPage==2){
			changeStep(stepThird, R.id.textRegistStep3);
			currPage++;
		}
	}
	/**
	 * 重新发送验证码
	 */
	abstract protected void resendCode();
	/**
	 * 点击阅读用户协议
	 */
	abstract protected void readUserProtol();
	/**
	 * 填写手机号码完毕
	 */
	abstract protected void phoneSuccess(String phoneNumber);
	/**
	 * 填写验证码完毕
	 */
	abstract protected void codeSuccess(String vcode);
	/**
	 * 填写密码完毕
	 * @param passwd
	 */
	abstract protected void passwordSuccess(String passwd);
	/**
	 * 改变状态
	 * @param step
	 * @param textRegistStepId
	 */
	protected void changeStep(Fragment step,int textRegistStepId){
		FragmentTransaction tran=fm.beginTransaction();
		tran.replace(R.id.fragmentContent, step);
		tran.commit();
		selectRegistStep(textRegistStepId);
	}
}
