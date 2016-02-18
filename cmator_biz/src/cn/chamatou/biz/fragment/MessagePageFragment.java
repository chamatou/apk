package cn.chamatou.biz.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import cn.chamatou.biz.activity.PullActivity;
import cn.chamatou.biz.activity.R;
import apk.lib.fragment.BaseFragment;

public class MessagePageFragment extends BaseFragment{

	@Override
	protected void createPageView(ViewGroup container, Bundle savedInstanceState) {
		Button btn=findViewById(R.id.openPull);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it=new Intent(getActivity(),PullActivity.class);
				startActivity(it);
			}
		});
	}

	@Override
	protected int getFragmentLayoutResource() {
		return R.layout.message_page_fragment;
	}

}
