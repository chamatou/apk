package apk.lib;
/**
 * 动作 
 */
public interface WidgetAction{
	/**
	 * 打开新的activity,getActionData的返回值则为新activity的包全名
	 */
	public static final int ACTION_ACTIVITY=0;
	/**
	 * 打开调用browser打开网页,getActionData的返回值则为新网页的url
	 */
	public static final int ACTION_BROWSER=1;
	/**
	 * 其他自定义操作
	 */
	public static final int ACTION_CUSTOM=1000;
	/**
	 * 返回动作意图
	 * @return
	 */
	public int getActionIntent();
	/**
	 * 获取动作数据
	 */
	public String getActionData();
}
