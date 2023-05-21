package com.email.emailservice.controller;

import com.email.emailservice.Dtos.EmailRequest;
import com.email.emailservice.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class Home {

	@RequestMapping("/")
	public String hello(){
		return "hello";
	}


}
