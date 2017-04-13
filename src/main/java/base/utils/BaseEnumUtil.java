package base.utils;

import java.util.HashMap;
import java.util.Map;

/***
 * Enum工具类
 * @author 丁万水
 *
 */
public class BaseEnumUtil {
	
	/***
	 * 转换Enum类
	 * @param t
	 * @return
	 */
	public static Map<String,Enum<?>> get(Class<? extends Enum<?> > t){
		Map<String,Enum<?>> map = new HashMap<String, Enum<?>>();
		
		Enum<?>[] ea = t.getEnumConstants();
		for(Enum<?> e: ea){
			map.put(e.name(), (Enum<?>)e);
		}
		
		return map;
	}
	
}
