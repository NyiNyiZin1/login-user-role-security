package com.nnz.user_role_security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	
	@GetMapping("/login_page")
	public String AccountLogin() {
		return "login";
	}

}
