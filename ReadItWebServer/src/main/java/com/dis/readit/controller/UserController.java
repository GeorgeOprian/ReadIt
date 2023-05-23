package com.dis.readit.controller;

import com.dis.readit.dtos.users.UserCreateDto;
import com.dis.readit.dtos.users.UserDto;
import com.dis.readit.service.UserPersistenceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

	private final UserPersistenceService service;

	public UserController(UserPersistenceService service) {
		this.service = service;
	}

	@PostMapping("/addUser")
	public ResponseEntity<UserCreateDto> addUser(@RequestBody UserCreateDto userDto) {

		return ResponseEntity.ok(service.addUser(userDto));
	}

	@GetMapping("/details")
	public ResponseEntity<UserDto> getUserDetails(@RequestParam(name = "email") String email) {
		return ResponseEntity.ok(service.getUserDetails(email));
	}

}
