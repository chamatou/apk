package apk.lib.json;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.support.v4.util.ArrayMap;
import apk.lib.utils.CoderUtil;
import apk.lib.utils.StringUtils;
/**
 * JSON解析工具类 
 */
public class JsonUtils {
	/**
	 * 从Map中解析出String
	 * @param map
	 * @param key
	 * @return
	 */
	public static final String getString(ArrayMap<String, String> map,String key){
		return map.get(key);
	}
	/**
	 * 从Map中解析出double
	 * @param map
	 * @param key
	 * @return
	 * @throws NumberFormatException
	 */
	public static final Double getDouble(ArrayMap<String, String> map,String key)throws NumberFormatException{
		return Double.parseDouble(getString(map, key));
	}
	/**
	 * 从Map中解析出Integer
	 * @param map
	 * @param key
	 * @return
	 * @throws NumberFormatException
	 */
	public static final Integer getInteger(ArrayMap<String, String> map,String key)throws NumberFormatException{
		return Integer.parseInt(getString(map, key));
	}
	/**
	 * 从Map中解析出Boolean
	 * @param map
	 * @param key
	 * @return
	 * @throws NumberFormatException
	 */
	public static final Boolean getBoolean(ArrayMap<String, String> map,String key)throws NumberFormatException{
		return Boolean.parseBoolean(getString(map, key));
	}
	/**
	 * 将JSON解析为ArrayMap
	 * @param json
	 * @return
	 * @throws JSONException 解析失败
	 */
	public static final ArrayMap<String, String> parse(String json) throws JSONException{
		ArrayMap<String,String> am=new ArrayMap<String,String>();
		JSONObject jo=new JSONObject(json);
		Iterator<String> keys=jo.keys();
		while(keys.hasNext()){
			String key=keys.next();
			am.put(key, jo.getString(key));
		}
		return am;
	}
	private static final <T> T fillClass(Class<T> clz,JSONObject jsonObject,boolean isDecodeKey,boolean isDecodeValue,CompositeHandler handler) throws InstantiationException, IllegalAccessException, JSONException, IllegalArgumentException, InvocationTargetException{
		T t=clz.newInstance();
		Iterator<String> fieldNames=jsonObject.keys();
		Method[] methods=clz.getMethods();
		while(fieldNames.hasNext()){
			String key=isDecodeKey?CoderUtil.base64Decode(fieldNames.next()):fieldNames.next();
			Method method=getMatchMethod(methods, "set"+StringUtils.upperFirst(key));
			if(method!=null){
				Object value=jsonObject.get(key);
				//value必须是String类型
				if(handler==null){
					if(isDecodeValue){
						value=CoderUtil.base64Decode(value.toString()).toString();
					}
					method.invoke(t,value);						
				}else{
					handler.composite(t, value);
				}
			}
		}
		return t;
	}
	/**
	 * 从json中解析获取类列表
	 * @param clz 
	 * @param json 
	 * @param isDecodeKey key是否采取base64编码
	 * @param isDecodeValue value是否采取base64编码
	 * @param handler 复合属性回调
	 * @return
	 */
	public static final <T> List<T> parseJsonToClassList(Class<T> clz,String json,boolean isDecodeKey,boolean isDecodeValue,CompositeHandler handler){
		try{
			JSONObject jo=new JSONObject(json);
			String clzName=StringUtils.lowerFirst(clz.getSimpleName());
			JSONArray memberArray=(JSONArray) jo.get(clzName);
			List<T> list=new ArrayList<T>();
			for(int i=0;i<memberArray.length();i++){
				list.add(fillClass(clz,(JSONObject)memberArray.get(i), isDecodeKey, isDecodeValue, handler));
			}
			return list;
		}catch(JSONException e){
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 将JSON解析为指定的类,格式如下,users,mer表示类名,包含的为mer值
	 * {'users':[{'name':'kaiyi'},{'age':'33'}],'mer':[{'name':'kaiyi'},{'age':'33'}]}
	 * @param clz
	 * @param json 
	 * @param isDecodeKey key是否采取base64编码
	 * @param isDecodeValue value是否采取base64编码
	 * @param handler 复合属性回调
	 */
	public static final <T> T parseToClass(Class<T> clz,String json,boolean isDecodeKey,boolean isDecodeValue,CompositeHandler handler){
		try{
			JSONObject jo=new JSONObject(json);
			String clzName=StringUtils.lowerFirst(clz.getSimpleName());
			JSONArray memberArray=(JSONArray) jo.get(clzName);
			JSONObject matchObject=(JSONObject) memberArray.get(0);
			return fillClass(clz, matchObject, isDecodeKey, isDecodeValue, handler);
		}catch(JSONException e){
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 处理JSON转Object时的复合对象
	 *
	 */
	public interface CompositeHandler{
		/**
		 * 处理复合对象
		 * @param obj 已经初始化好的类
		 * @param value JSONValue
		 */
		void composite(Object obj,Object value);
	};
	//从Methods中获取匹配的方法
	private static Method getMatchMethod(Method[] methods,String methodName){
		for(Method m:methods){
			if(m.getName().equals(methodName)){
				return m;
			}
		}
		return null;
	}
}
