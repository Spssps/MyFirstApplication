package com.sps.sdp.application.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sps.sdp.application.manager.MyManager;

@RestController
@RequestMapping("securityCheck")
public class MyResource {

	public MyManager myManager;

	@Autowired
	public MyResource(MyManager myManager) {
		this.myManager = myManager;
	}

	@RequestMapping("/login")
	public String myMethod(@RequestParam String userName, @RequestParam String password) {

		String passwordFromDB = myManager.getUserDetails(userName);

		if (password.equals(passwordFromDB)) {
			return "Welcome to SPSDP world";
		} else {
			return "Sorry u r not Authorised to access this page";
		}

	}

}
