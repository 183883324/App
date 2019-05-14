package com.example.android.tool;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.Map;
/**
 * JSON相互转换
 * */
public final class JSONUtils {
	public static <T> T parseJSON(String jsonStr, Class<T> t) {
		Gson gson = new Gson();
		T bean = gson.fromJson(jsonStr, t);
		return bean;
	}

	/**
	 * 
	 * @param response
	 * @param type
	 *           Type type = new TypeToken<ltArrayList<ltAnimeInfo>>() {
	//							 *            }.getType();
	 * @return
	 */
	public static <T> T parseJSONArray(String response, Type type) {
		Gson gson = new Gson();
		T data = gson.fromJson(response, type);
		return data;
	}
	public static <T> String mapToJson(Map<String, T> map) {
		Gson gson = new Gson();
		String jsonStr = gson.toJson(map);
		return jsonStr;
	}

}
