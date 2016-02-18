package apk.lib.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Fragment通讯接口 
 *
 */
public interface FragmentAction {
	/**
	 * 单击事件
	 */
	public static final int TYPE_CLICK=0;
	/**
	 * 长按事件
	 */
	public static final int TYPE_LONG_CLICK=1;
	/**
	 * fragment中传递的动作
	 * @param fragment 触发事件的framgent
	 * @param view 触发事件的View
	 * @param type 事件类型
	 * @param bundle 传递数据
	 */
	public void action(Fragment fragment,View view,int type,Bundle bundle);
}
