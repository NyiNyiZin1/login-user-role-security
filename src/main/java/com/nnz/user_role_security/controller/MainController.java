package com.nnz.user_role_security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	
	@GetMapping("/main_page")
	public String mainPage() {
		return "main";
	}
	
	@GetMapping("/admin_page")
	public String adminPage() {
		return "admin";
	}
}
