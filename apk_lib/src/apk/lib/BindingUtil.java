package apk.lib;
import java.lang.reflect.Field;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import apk.lib.log.Logger;
/**
 * 数据绑定bean
 * 
 */
public final class BindingUtil {
	private static final Logger logger=Logger.getLogger(BindingUtil.class);
	public static final ArrayMap<String,ArrayMap<String,Integer>> R_FILE_FIELD_MAP
		=new ArrayMap<String,ArrayMap<String,Integer>>();
	/**
	 * 绑定view中的字段
	 * @param view
	 * @param RFile
	 */
	public static void viewBinding(View container,Class<?> RFile){
		ArrayMap<String,Integer> rfileNamelist=reflectResIdNames(RFile);
		Field[] fields=container.getClass().getDeclaredFields();
		if(fields!=null&&fields.length>0){
			for(Field field:fields){
				Integer findId=rfileNamelist.get(field.getName());
				if(findId!=null){
					View view=container.findViewById(findId);
					field.setAccessible(true);
					try {
						field.set(container, view);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public static View inflate(Context context,int layoutResID,ViewGroup container,boolean attachToRoot,Class<?> RFile){
		return inflate(LayoutInflater.from(context), layoutResID, container, attachToRoot, RFile);
	}
	/**
	 * inflate 方式进行字段绑定,在Fragment中使用
	 * @param reflectable 标志性接口
	 * @param inflater LayoutInflater
	 * @param layoutResID 布局资源ID
	 * @param container 父容器
	 * @param attachToRoot 是否添加到父容器
	 * @param RFile R文件
	 * @return
	 */
	public static View inflate(LayoutInflater inflater,int layoutResID,ViewGroup container,boolean attachToRoot,Class<?> RFile){
		View parent=inflater.inflate(layoutResID, container, attachToRoot);
		viewBinding(parent, RFile);
		return parent;
	}
	
	/**
	 * 设置视图并绑定ID,缺陷,不能再抽象类，父类中直接使用
	 * @param activity
	 * @param layoutResID
	 * @param RFile
	 */
	public static void setContentView(Activity activity,int layoutResID, Class<?> RFile) {
		ArrayMap<String,Integer> rfileNamelist=reflectResIdNames(RFile);
		activity.setContentView(layoutResID);
		//List<View> views=new ArrayList<View>();
		//View root=activity.getWindow().getDecorView();
		//getAllChildViews(root, views);
		bindData(activity, rfileNamelist);
	}
	
	
	private static void bindData(Activity activity,
			ArrayMap<String,Integer> rfileNamelist) {
		Class<?> clz=activity.getClass();
		Field[] fields=clz.getDeclaredFields();
		if(fields!=null&&fields.length>0){
			for(Field field:fields){
				Integer findId=rfileNamelist.get(field.getName());
				logger.d("fieldName:"+field.getName()+",findId:"+findId);
				if(findId!=null){
					View view=activity.findViewById(findId);
					logger.d("find view:"+view);
					if(view!=null){
						field.setAccessible(true);
						try {
							field.set(activity, view);
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						}						
					}
				}
			}
		}
	}
	/**
	 * 获取R文件中的ID字段名称
	 * @param r
	 * @return
	 */
	private static ArrayMap<String,Integer> reflectResIdNames(Class<?> r){
		String name=r.getName();
		synchronized (R_FILE_FIELD_MAP) {
			try{
				if(R_FILE_FIELD_MAP.get(name)==null){
					ArrayMap<String,Integer> idMap=new ArrayMap<String,Integer>();
					R_FILE_FIELD_MAP.put(name, idMap);
					Object obj=Class.forName(name);
					Field[] fields=Class.forName(name+"$id").getDeclaredFields();
					if(fields!=null&&fields.length>0){
						for(Field field:fields){
							idMap.put(field.getName(),field.getInt(obj));
						}						
					}
				}
			}catch(ClassNotFoundException e){
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
			return R_FILE_FIELD_MAP.get(name);
		}
	}
	public static final void getAllChildViews(View view,List<View> views){
		 if(view instanceof ViewGroup){
			 ViewGroup group=(ViewGroup)view;
			 if(group.getChildCount()>0){
				 for(int i=0;i<group.getChildCount();i++){
					 getAllChildViews(group.getChildAt(i), views);
				 }
			 }
		 }
		 views.add(view);
	}
}
