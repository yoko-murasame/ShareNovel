package cn.dmdream.utils;

import java.util.HashMap;
import java.util.Map;

public class StringUtils {
	
	/**
	 * toString的Map字符串转Map<String,String>
	 * @param str
	 * @return
	 */
	public static Map<String,String> mapStringToMap(String str){  
	    str=str.substring(1, str.length()-1);  
	    String[] strs=str.split(",");  
	    Map<String,String> map = new HashMap<String, String>();  
	    for (String string : strs) {  
	        String key=string.split("=")[0];  
	        String value=string.split("=")[1];  
	        map.put(key, value);  
	    }  
	    return map;  
	}  
}
