package apk.lib.fragment;

import apk.lib.R;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import apk.lib.activity.AbstractUserRegistActivity;
import apk.lib.listener.TextWatcherAdapter;
import apk.lib.widget.RoundButton;

public class UserRegistSecondStepFragment extends BaseFragment implements View.OnClickListener{
	private static final int MSG_SUBTRACT=1;
	private TextView reSend;
	private int time;
	private FragmentAction fragmentAction;
	private RoundButton submitCode;
	private EditText vcode;
	private static final int BASE_TIME=60;
	private int textColor;
	private int bgColor;
	private Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			if(msg.what==MSG_SUBTRACT){
				if(time!=0){
					countdown();
				}else{
					unCountdown();
				}
			}
		}
	};
	public void setcountdownTime(int time){
		this.time=time;
	}
	
	public void unCountdown(){
		reSend.setText(getResources().getString(R.string.user_regist_send_code));
		reSend.setBackgroundColor(getResources().getColor(R.color.primary));
		reSend.setTextColor(getResources().getColor(R.color.grey_white));
		handler.removeMessages(MSG_SUBTRACT);
		reSend.setEnabled(true);
	}
	public void countdown(){
		reSend.setEnabled(false);
		String str=getResources().getString(R.string.user_regist_reget_vcode);
		str=String.format(str, time);
		reSend.setText(str);
		reSend.setBackgroundColor(getResources().getColor(R.color.grey_400));
		reSend.setTextColor(getResources().getColor(R.color.grey_700));
		time--;
		handler.sendEmptyMessageDelayed(MSG_SUBTRACT, 1000);
	}
	
	@Override
	protected void createPageView(ViewGroup container, Bundle savedInstanceState) {
		reSend=findViewById(R.id.btn_resend);
		reSend.setOnClickListener(this);
		time=BASE_TIME;
		countdown();
		FragmentActivity activity=getActivity();
		if(activity instanceof FragmentAction){
			fragmentAction=(FragmentAction)activity;
		}
		submitCode=findViewById(R.id.submitCode);
		textColor=submitCode.getTextColor();
		bgColor=submitCode.getBgColor();
		submitCode.setOnClickListener(this);
		submitCode.disableButton(textColor, bgColor);
		vcode=findViewById(R.id.vcode);
		vcode.addTextChangedListener(new TextWatcherAdapter() {

			@Override
			public void afterTextChanged(Editable s) {
				if(s.toString().length()>0){
					submitCode.enableButton(getResources().getColor(R.color.grey_white),getResources().getColor( R.color.primary));
				}else{
					submitCode.disableButton(textColor, bgColor);
				}
			}
		});
	}
	
	@Override
	public void onDestroyView() {
		handler.removeCallbacksAndMessages(null);
		super.onDestroyView();
	}
	@Override
	protected int getFragmentLayoutResource() {
		return R.layout.user_regist_step_2;
	}

	@Override
	public void onClick(View v) {
		if(fragmentAction!=null){
			int id=v.getId();
			Bundle bundle=new Bundle();
			if(id==R.id.btn_resend&&reSend.isEnabled()){
				//发送验证码
				time=BASE_TIME;
				countdown();
				bundle.putInt(AbstractUserRegistActivity.OPER_KEY,AbstractUserRegistActivity.OPER_SEND_CODE);
			}else if(id==R.id.submitCode){
				bundle.putString(AbstractUserRegistActivity.KEY_VALID_CODE,vcode.getText().toString());
				bundle.putInt(AbstractUserRegistActivity.OPER_KEY, AbstractUserRegistActivity.OPER_NEXT);
			}
			fragmentAction.action(this, v, FragmentAction.TYPE_CLICK, bundle);
		}
	}
}
