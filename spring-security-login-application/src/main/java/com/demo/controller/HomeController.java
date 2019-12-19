package com.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	@GetMapping("/adminStuff")
	public String showAdminView() {
		return "adminView";
	}
	
	@GetMapping("/managerStuff")
	public String showManagerView() {
		return "managerView";
	}
	
	@GetMapping("/employeeStuff")
	public String showEmployeeView() {
		return "employeeView";
	}
}
