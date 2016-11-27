package com.sps.sdp.application.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyResource {
	
	@RequestMapping("/")
	public String myMethod() {
		return "Hello World.. This is SPSDP  Have a nice DAY";
	}

}
