package com.example.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Maps;

import net.sf.json.JSONObject;

public class CommonUtil {

	/**
	 * application.properties 中的键值对
	 */
	public static Map<String, String> APPLICATIONMAP = Maps.newHashMap();
	static {
		Properties p = new Properties();
		try {
			p.load(CommonUtil.class.getClass().getResourceAsStream("/application.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (Map.Entry<Object, Object> tmp : p.entrySet()) {
			APPLICATIONMAP.put(tmp.getKey().toString(), tmp.getValue().toString());
		}
	}

	/**
	 * 获取application.properties 中的值
	 * 
	 * @param key
	 * @return
	 */
	public static String getPropertiesValue(String key) {
		return APPLICATIONMAP.get(key);
	}

	/**
	 * 存储公众号的全局唯一接口调用凭据和有效时间
	 */
	private static Map<String, Object> ACCESSTOKENMAP = Maps.newHashMap();
	/** 公众号的全局唯一接口调用凭据 */
	private static final String ACCESS_TOKEN = "access_token";
	
	/** 公众号的全局唯一接口调用凭据的有效时间 */
	private static final String EXPIRES_IN = "expires_in";
	
	
	/**
	 * 获取公众号的全局唯一接口调用凭据
	 * 启动时初始化凭证
	 * 凭证过期时重新请求新的凭证
	 * 所有请求都需要先获取凭证
	 * @return
	 */
	public static String getAccessToken() {
		Object access_token = ACCESSTOKENMAP.get(ACCESS_TOKEN);
		Object expires_in = ACCESSTOKENMAP.get(EXPIRES_IN);
		
		if(access_token == null || expires_in == null) {
			initAccessToken();
		}
		if(StringUtils.isEmpty((String)access_token) 
				|| Calendar.getInstance().getTimeInMillis() >= (long)expires_in) {
			initAccessToken();
		}
		
		access_token = ACCESSTOKENMAP.get(ACCESS_TOKEN);
		return (String) access_token;
	}
	
	/**
	 * 初始化 公众号的全局唯一接口调用凭据和有效时间
	 */
	private static void initAccessToken() {
		String accessTokenURL = getPropertiesValue("ACCESS_TOKEN_URL");
		String grant_type = getPropertiesValue("GRANT_TYPE");
		String appid = getPropertiesValue("APPID");
		String appSecret = getPropertiesValue("APPSECRET");
		
		String url = accessTokenURL + "?grant_type=" + grant_type + "&appid=" + appid + "&secret=" + appSecret;
		
		JSONObject httpGet = HttpRequestUtils.httpGet(url);
		String access_token = (String) httpGet.get("access_token");
		Integer expires_in = (Integer) httpGet.get("expires_in");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, expires_in);
		
		ACCESSTOKENMAP.put(ACCESS_TOKEN, access_token);
		ACCESSTOKENMAP.put(EXPIRES_IN, cal.getTimeInMillis());
	}
	
	public static void main(String[] args) {
		getAccessToken();
	}

}
