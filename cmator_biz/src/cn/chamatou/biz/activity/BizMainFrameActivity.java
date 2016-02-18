package cn.chamatou.biz.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import cn.chamatou.biz.fragment.HomePageFragment;
import cn.chamatou.biz.fragment.MainPageFragment;
import cn.chamatou.biz.fragment.MessagePageFragment;
import apk.lib.BindingUtil;
import apk.lib.activity.BasicActivity;

/**
 * 商家主体框架
 */
public class BizMainFrameActivity extends BasicActivity {
	private Toolbar toolbar;
	private ImageView toolbarMenu;
	private TextView toolbarTitle;
	//private FrameLayout fragmentContent;
	private FragmentTabHost tabHost;
	private Class<?>[] tabFragments;
	private String[] tabNames;
	private String[] titleNames;
	private int[] tabIcons;
	private LayoutInflater inflate;
	private View[] tabViews;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		BindingUtil.setContentView(this, R.layout.biz_main_frame_activity,
				R.class);
		toolbar.setTitle("");
		ViewCompat.setElevation(toolbar, 20);
		setSupportActionBar(toolbar);
		//toolbar.setNavigationIcon(cn.chamatou.lib.R.mipmap.ic_keyboard_arrow_left_white_36dp);
		initTabs();
	}

	private void initTabs() {
		tabFragments = new Class<?>[] { MainPageFragment.class,
				MessagePageFragment.class, HomePageFragment.class };
		tabNames = new String[] { getResourceString(R.string.module_main),
				getResourceString(R.string.module_message),
				getResourceString(R.string.module_home)};
		titleNames= new String[] { getResourceString(R.string.module_main_title),
				getResourceString(R.string.module_message_title),
				getResourceString(R.string.module_home_title)};
		toolbarTitle.setText(titleNames[0]);
		tabIcons = new int[] { R.drawable.ic_main_page_btn,
				R.drawable.ic_message_page_btn, R.drawable.ic_home_page_btn };
		tabViews = new View[tabFragments.length];
		inflate = LayoutInflater.from(this);
		tabHost.setup(this, getSupportFragmentManager(), R.id.tabContent);
		for (int i = 0; i < tabFragments.length; i++) {
			TabSpec tabSpec = tabHost.newTabSpec(tabNames[i]).setIndicator(
					getTabItemView(i));
			tabHost.addTab(tabSpec, tabFragments[i], null);
			tabHost.getTabWidget().getChildAt(i)
					.setBackgroundResource(R.color.grey_white);
		}
		tabHost.getTabWidget().setDividerDrawable(null);
		tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String tabId) {
				toolbarTitle.setText(titleNames[tabHost.getCurrentTab()]);
			}
		});
	}

	/**
	 *
	 * 给每个Tab按钮设置图标和文字
	 */
	private View getTabItemView(int index) {
		View view = inflate.inflate(apk.lib.R.layout.tab_host_item,null);
		ImageView imageView = (ImageView) view
				.findViewById(apk.lib.R.id.tab_item_img);
		imageView.setImageResource(tabIcons[index]);
		TextView textView = (TextView) view
				.findViewById(apk.lib.R.id.tab_item_text);
		textView.setText(tabNames[index]);
		if (index == 0) {
			imageView.setSelected(true);
			textView.setSelected(true);
		} else {
			imageView.setSelected(false);
			textView.setSelected(false);
		}
		tabViews[index] = view;
		return view;
	}

	@Override
	public String getCurrentActivityName() {
		return "商家版框架";
	}
	/*
	 * toolbarMenu.setBackgroundDrawable(getResources().getDrawable(cn.chamatou.lib
	 * .R.mipmap.ic_add_white_36dp)); toolbarMenu.setOnClickListener(new
	 * View.OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { View
	 * content=LayoutInflater.from(getApplicationContext
	 * ()).inflate(cn.chamatou.lib.R.layout.popup_menu,null); final PopupWindow
	 * pw=new
	 * PopupWindow(content,LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT
	 * ,true); pw.setTouchable(true); pw.setTouchInterceptor(new
	 * View.OnTouchListener() {
	 * 
	 * @Override public boolean onTouch(View v, MotionEvent event) { return
	 * false; } });
	 * pw.setBackgroundDrawable(getResources().getDrawable(R.drawable
	 * .border_radius_white_4)); pw.showAsDropDown(toolbarMenu); } });
	 */
}
