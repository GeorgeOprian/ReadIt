package com.email.emailservice.service;

import com.email.emailservice.Dtos.UserRequest;

public interface UserService {

	String handleUserOperation(UserRequest request);
}
