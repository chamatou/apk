package apk.lib.fragment;
import apk.lib.R;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.EditText;
import apk.lib.activity.AbstractUserRegistActivity;
import apk.lib.listener.TextWatcherAdapter;
import apk.lib.widget.RoundButton;

public class UserRegistThirdStepFragment extends BaseFragment implements OnClickListener{
	private EditText passwd;
	private EditText repasswd;
	private RoundButton submitRegist;
	private int textColor;
	private int bgColor;
	private FragmentAction fragmentAction;
	private TextWatcher twatcher=new  TextWatcherAdapter(){
		@Override
		public void afterTextChanged(Editable s) {
			String p1=passwd.getText().toString();
			String p2=repasswd.getText().toString();
			if(p1.length()>0&&p2.length()>0){
				submitRegist.enableButton(getResources().getColor(R.color.grey_white),getResources().getColor( R.color.primary));
			}
		}
	};
	
	@Override
	protected void createPageView(ViewGroup container, Bundle savedInstanceState) {
		passwd=findViewById(R.id.password);
		repasswd=findViewById(R.id.repassword);
		passwd.addTextChangedListener(twatcher);
		repasswd.addTextChangedListener(twatcher);
		submitRegist=findViewById(R.id.submitRegist);
		textColor=submitRegist.getTextColor();
		bgColor=submitRegist.getBgColor();
		submitRegist.setOnClickListener(this);
		submitRegist.disableButton(textColor, bgColor);
		FragmentActivity activity=getActivity();
		if(activity instanceof FragmentAction){
			fragmentAction=(FragmentAction)activity;
		}
	}

	@Override
	protected int getFragmentLayoutResource() {
		return R.layout.user_regist_step_3;
	}

	@Override
	public void onClick(View v) {
		if(fragmentAction!=null){
			if(!passwd.getText().toString().equals(repasswd.getText().toString())){
				final AlertDialog ad=new AlertDialog.Builder(getContext()).setTitle(getResources().getString(R.string.warn))
				.setMessage(getResources().getString(R.string.user_regist_passwd_not_match))
				.setPositiveButton(getResources().getString(R.string.done),null)
				.create();
				ad.show();
			}else{
				Bundle bundle=new Bundle();
				bundle.putString(AbstractUserRegistActivity.KEY_PASSWORD,passwd.getText().toString());
				bundle.putInt(AbstractUserRegistActivity.OPER_KEY, AbstractUserRegistActivity.OPER_NEXT);
			}
		}
	}
}
