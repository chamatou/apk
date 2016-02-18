package apk.lib.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import apk.lib.log.Logger;

public abstract class BaseFragment extends Fragment{
	protected static  Logger logger;
	protected View fragment;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		logger=Logger.getLogger(this.getClass());
		fragment=inflater.inflate(getFragmentLayoutResource(),container,false);
		createPageView(container,savedInstanceState);
		return fragment;
	}
	/**
	 * 构建Fragment视图
	 * @param container
	 * @param savedInstanceState
	 */
	protected abstract void createPageView(ViewGroup container,
			Bundle savedInstanceState);
	/**
	 * 获取Fragment视图ID 
	 */
	protected abstract int getFragmentLayoutResource();
	
	@SuppressWarnings("unchecked")
	protected <T> T findViewById(int viewId){
		return (T) fragment.findViewById(viewId);
	}
}
