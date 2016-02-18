package apk.lib.fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import apk.lib.activity.AbstractUserRegistActivity;
import apk.lib.listener.TextWatcherAdapter;
import apk.lib.utils.BasicVariableVerify;
import apk.lib.widget.RoundButton;
import apk.lib.R;

public class UserRegistOneStepFragment extends BaseFragment implements OnClickListener{
	private EditText registPhoneNumber;
	private RoundButton sendCode;
	private int textColor;
	private int bgColor;
	private FragmentAction fragmentAction;
	private CheckBox isReadProtol;
	private TextView protol;
	@Override
	protected void createPageView(ViewGroup container, Bundle savedInstanceState) {
		isReadProtol=findViewById(R.id.isReadProtol);
		protol=findViewById(R.id.protol);
		protol.setOnClickListener(this);
		sendCode=findViewById(R.id.sendCode);
		textColor=sendCode.getTextColor();
		bgColor=sendCode.getBgColor();
		sendCode.setOnClickListener(this);
		sendCode.disableButton(textColor, bgColor);
		FragmentActivity activity=getActivity();
		if(activity instanceof FragmentAction){
			fragmentAction=(FragmentAction)activity;
		}
		registPhoneNumber=(EditText)findViewById(R.id.registPhoneNumber);
		registPhoneNumber.addTextChangedListener(new TextWatcherAdapter() {
			@Override
			public void afterTextChanged(Editable s) {
				String phone=s.toString();
				if(BasicVariableVerify.mobileValidate(phone)&&isReadProtol.isChecked()){
					sendCode.enableButton(getResources().getColor(R.color.grey_white),getResources().getColor( R.color.primary));
				}else{
					sendCode.disableButton(textColor, bgColor);
				}
			}
		});
		isReadProtol.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked&&BasicVariableVerify.mobileValidate(registPhoneNumber.getText().toString())){
					sendCode.enableButton(getResources().getColor(R.color.grey_white),getResources().getColor( R.color.primary));
				}else{
					sendCode.disableButton(textColor, bgColor);
				}
			}
		});
	}
	@Override
	protected int getFragmentLayoutResource() {
		return R.layout.user_regist_step_1;
	}
	@Override
	public void onClick(View v) {
		if(fragmentAction!=null){
			Bundle bundle=new Bundle();
			int id=v.getId();
			if(id==R.id.sendCode){
				//发送验证码,进入下一页面
				bundle.putString(AbstractUserRegistActivity.KEY_PHONE_NUMBER, registPhoneNumber.getText().toString());
				bundle.putInt(AbstractUserRegistActivity.OPER_KEY,AbstractUserRegistActivity.OPER_NEXT);
			}else if(id==R.id.protol){
				bundle.putInt(AbstractUserRegistActivity.OPER_KEY,AbstractUserRegistActivity.OPER_OPEN_BROWSER);
			}
			fragmentAction.action(this, v, FragmentAction.TYPE_CLICK, bundle);
		}
	}
}
