package com.example.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.util.CommonUtil;
import com.example.util.WXCheck;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class WeChatCheckController {

	@RequestMapping("/wx")
	public String checkSignature(HttpServletRequest req, HttpServletResponse rep) {
		String signature = req.getParameter("signature");
		String timestamp = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");
		String echostr = req.getParameter("echostr");
		String token = CommonUtil.getPropertiesValue("token");
		boolean checkSignature = WXCheck.checkSignature(signature, token, timestamp, nonce);
		if(checkSignature) {
			return echostr;
		}else{
			return "Error";
		}
	}

}