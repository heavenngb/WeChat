package com.example.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.util.InitWeChatPublicNumMenu;

@RestController
@RequestMapping("/menu")
public class WeChatMenuInitController {

	@RequestMapping("/init")
	public String createMenu(HttpServletRequest req, HttpServletResponse rep) {
		String result = InitWeChatPublicNumMenu.createMenu();
		System.out.println(result);
		return result;
	}

}