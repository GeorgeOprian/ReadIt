package com.dis.readit.controller;

import com.dis.readit.dtos.Test;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TestController {

	@GetMapping("/test")
	public Test test(){
		return new Test("test");
	}
}
