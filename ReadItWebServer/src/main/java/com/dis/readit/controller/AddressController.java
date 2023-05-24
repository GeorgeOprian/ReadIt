package com.dis.readit.controller;

import com.dis.readit.dtos.address.AddressFullDto;
import com.dis.readit.dtos.address.JudetDto;
import com.dis.readit.dtos.address.LocalitateDto;
import com.dis.readit.dtos.address.UserAddressInputDto;
import com.dis.readit.dtos.users.UserDto;
import com.dis.readit.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addresses")
public class AddressController {

	@Autowired
	private AddressService service;

	@GetMapping("/judete")
	public ResponseEntity<List<JudetDto>> getJudete() {
		return ResponseEntity.ok(service.getJudete());
	}

	@GetMapping("/localitati")
	public ResponseEntity<List<LocalitateDto>> getLocalitati() {
		return ResponseEntity.ok(service.getLocalitati());
	}

	@PostMapping("/address")
	public ResponseEntity<UserDto> addAddress(@RequestBody UserAddressInputDto userAddress) {
		return ResponseEntity.ok(service.addAddress(userAddress));
	}

	@PutMapping("/address")
	public ResponseEntity<UserDto> replaceAddress(@RequestBody UserAddressInputDto userAddress) {
		return ResponseEntity.ok(service.replaceAddress(userAddress));
	}

}
