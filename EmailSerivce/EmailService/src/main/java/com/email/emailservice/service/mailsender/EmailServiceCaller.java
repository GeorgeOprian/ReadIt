package com.email.emailservice.service.mailsender;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletionException;

@Slf4j
@Service
public class EmailServiceCaller {

	private final JavaMailSender javaMailSender;

	public EmailServiceCaller(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public void sendSimpleEmail(String emailSubject, List<String> emailTo, String body) {
		SimpleMailMessage message = new SimpleMailMessage();

		String[] recipients = emailTo.toArray(String[]::new);

		message.setTo(recipients);
		message.setSubject(emailSubject);
		message.setText(body);

		try {
			javaMailSender.send(message);
		} catch (Exception e) {
			throw new CompletionException(e);
		}

		for (String recipient : recipients) {
			log.info("Mail sent to " + recipient);
		}
	}
}
