package apk.lib.json;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.support.v4.util.ArrayMap;

/**
 * 解析JSON字符串，
 *  简单JSON:{"json-key":"json-vaue"}
 *  复杂JSON:{'testJsonBean':[{'name':'name_1','age':'33'}],'JS':[{'name':'name_1','age':'33'}]}
 */
public class JsonParser {
	/**
	 * 解析简单JSON字符串
	 * @param jsonText
	 * @return
	 * @throws JSONException
	 */
	public static final ArrayMap<String, String> simpleParse(String jsonText) throws JSONException{
		JSONObject jo=new JSONObject(jsonText);
		ArrayMap<String, String>  am=new ArrayMap<String, String>();
		Iterator<String> keys=jo.keys();
		for(;keys.hasNext();){
			String key=keys.next();
			am.put(key,jo.get(key).toString());
		}
		return am;
	}
	/**
	 * 解析复杂的JSON到ArrayMap中
	 * @param jsonText
	 * @return 返回的Object中只能是String或ArrayMap<String,Object>
	 * @throws JSONException
	 */
	public static final ArrayMap<String,Object> compositeParse(String jsonText) throws JSONException{
		JSONObject jo=new JSONObject(jsonText);
		ArrayMap<String,Object>  am=new ArrayMap<String,Object>();
		Iterator<String> keys=jo.keys();
		for(;keys.hasNext();){
			String key=keys.next();
			Object obj=jo.get(key);
			if(obj instanceof JSONArray){
				am.put(key, parse((JSONArray) obj));
			}else{
				am.put(key, obj);
			}
		}
		return am;
	}
	
	private static final ArrayMap<String, Object> parse(JSONArray jr) throws JSONException{
		ArrayMap<String, Object> map=new ArrayMap<String, Object>();
		for(int i=0;i<jr.length();i++){
			JSONObject jo=(JSONObject)jr.get(i);
			Iterator<String> keys=jo.keys();
			for(;keys.hasNext();){
				String key=keys.next();
				Object obj=jo.get(key);
				if(obj instanceof JSONArray){
					map.put(key, parse((JSONArray) obj));
				}else{
					map.put(key, obj.toString());
				}
			}
		}
		return map;
	}
	
}
