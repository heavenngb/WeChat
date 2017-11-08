package com.example.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import com.google.common.collect.Maps;

import net.sf.json.JSONObject;

/**
 * 初始化菜单
 * @author Administrator
 *
 */
public class InitWeChatPublicNumMenu {
	
	public static String createMenu() {
		String APP_MENU_CREATE_URL = CommonUtil.getPropertiesValue("APP_MENU_CREATE_URL");
		String accessToken = CommonUtil.getAccessToken();
		
		String url = APP_MENU_CREATE_URL + accessToken;
		
		try {
			InputStream is = InitWeChatPublicNumMenu.class.getClass().getResourceAsStream("/menu.json");
			byte[] b= new byte[is.available()]; 
			is.read(b);
			String menu = new String(b, "UTF-8");
			Map<String, String> mapParam = Maps.newHashMap();
			mapParam.put("body", menu);
			JSONObject jsonParam = JSONObject.fromObject(mapParam);
			
			JSONObject httpPost = HttpRequestUtils.httpPost(url, jsonParam);
			
			String errcode = httpPost.getString("errcode");
			String errmsg = httpPost.getString("errmsg");
			
			System.out.println("httpPost : "  + httpPost);
			System.out.println("errcode : "  + errcode);
			System.out.println("errmsg : "  + errmsg);
			
			return httpPost.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "error";
	}

}
