package com.example.util;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

public class WXCheck {

	public static boolean checkSignature(String signature, String token, String timestamp, String nonce) {

		// 1）将token、timestamp、nonce三个参数进行字典序排序
		List<String> parameter = Lists.newArrayList();
		parameter.add(token);
		parameter.add(timestamp);
		parameter.add(nonce);

		Collections.sort(parameter);

		for (String tmp : parameter) {
			System.out.println(tmp);
		}

		// 2）将三个参数字符串拼接成一个字符串进行sha1加密
		String sha1 = DecriptUtil.SHA1(parameter.get(0) + parameter.get(1) + parameter.get(2));
		System.out.println(sha1);
		// 3）开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
		System.out.println(signature);

		if (sha1.equals(signature)) {
			return true;
		} else {
			return false;
		}
	}

}
