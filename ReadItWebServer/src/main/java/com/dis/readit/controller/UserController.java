package com.dis.readit.controller;

import com.dis.readit.dtos.users.UserCreateDto;
import com.dis.readit.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

	private final UserService service;

	public UserController(UserService service) {
		this.service = service;
	}

	@PostMapping("/addUser")
	public ResponseEntity<UserCreateDto> addUser(@RequestBody UserCreateDto userDto) {

		return ResponseEntity.ok(service.addUser(userDto));
	}

}
