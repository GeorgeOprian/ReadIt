package com.email.emailservice.service;

import com.email.emailservice.Dtos.EmailRequest;
import com.email.emailservice.Dtos.UserEmailResponse;

import java.util.List;

public interface EmailService {

	String sendEmail(EmailRequest request);

	List<UserEmailResponse> loadEmailsForUser(Long userId);
}
