package com.email.emailservice.controller;

import com.email.emailservice.Dtos.EmailRequest;
import com.email.emailservice.Dtos.UserEmailResponse;
import com.email.emailservice.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/email")
public class EmailController {

	private final EmailService service;

	public EmailController(EmailService service) {
		this.service = service;
	}

	@PostMapping("/newemail")
	public ResponseEntity<String> sendEmailToAllUsers(@RequestBody EmailRequest body) {

		String response = service.sendEmail(body);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/")
	public ResponseEntity<List<UserEmailResponse>> getUserEmails(@RequestParam(name = "user") Long userId) {
		return ResponseEntity.ok(service.loadEmailsForUser(userId));
	}

}
