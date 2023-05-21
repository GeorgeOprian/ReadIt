package com.email.emailservice.service.impl;

import com.email.emailservice.Dtos.EmailRequest;
import com.email.emailservice.Dtos.UserEmailResponse;
import com.email.emailservice.exception.UserNotFoundException;
import com.email.emailservice.model.EmailHistory;
import com.email.emailservice.model.User;
import com.email.emailservice.repository.EmailHistoryRepository;
import com.email.emailservice.repository.UserRepository;
import com.email.emailservice.service.EmailService;
import com.email.emailservice.service.mailsender.EmailServiceCaller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletionException;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

	private final EmailServiceCaller emailSender;

	private final EmailHistoryRepository emailHistoryRepository;
	private final UserRepository userRepository;

	public EmailServiceImpl(EmailServiceCaller emailSender, EmailHistoryRepository emailHistoryRepository, UserRepository userRepository) {
		this.emailSender = emailSender;
		this.emailHistoryRepository = emailHistoryRepository;
		this.userRepository = userRepository;
	}

	@Override
	public List<UserEmailResponse> loadEmailsForUser(Long userId) {
		Optional<User> userOpt = userRepository.findById(userId);
		if (userOpt.isEmpty()) {
			throw new UserNotFoundException("Sender with id: " + userId + " was not found.");
		}

		User user = userOpt.get();
		List<UserEmailResponse> response = new ArrayList<>();
		for (EmailHistory email : user.getEmails()) {
			UserEmailResponse ur = new UserEmailResponse();

			ur.setSubject(email.getSubject());
			ur.setContent(email.getContent());
			List<String> recipientsEmails = getRecipientsEmails(email.getRecipients());
			ur.setEmailAddresses(recipientsEmails);

			response.add(ur);
		}

		return response;
	}

	private List<String> getRecipientsEmails(String recipients) {
		List<String> emailAddresses = new ArrayList<>();
		if (recipients == null || recipients.isEmpty() || recipients.isBlank()) {
			return new ArrayList<>();
		}

		List<Long> recipientsIDs = Arrays.asList(recipients.split(",")).stream().map(id -> Long.parseLong(id)).collect(Collectors.toList());
		for (Long recipient : recipientsIDs) {
			Optional<User> recipientOpt = userRepository.findById(recipient);

			if (recipientOpt.isEmpty()) {
				throw new UserNotFoundException("User with id: " + recipient + " was not found.");
			}
			emailAddresses.add(recipientOpt.get().getEmailAddress());
		}
		return emailAddresses;
	}

	@Override
	public String sendEmail(EmailRequest requestBody) {
		log.info("Enter send emails service");
		Optional<User> senderOpt;
		try {
			senderOpt = userRepository.findById(requestBody.getSender());
		} catch (Exception e) {
			throw new CompletionException(e);
		}

		if (senderOpt.isEmpty()) {
			String errorMessage = "Sender with id: " + requestBody.getSender() + " was not found.";
			log.info(errorMessage);
			throw new UserNotFoundException(errorMessage);
		}

		log.info(senderOpt.get() + " was read");

		List<User> recipients;
		if (requestBody.getRecipients() != null && !requestBody.getRecipients().isEmpty()) {
			List<Long> inputUsers = requestBody.getRecipients();
			recipients = userRepository.findAllById(inputUsers);
			if (inputUsers.size() != recipients.size()) {
				findNotFoundUsersAndThrow(inputUsers, recipients);
			}

		} else {
			recipients = userRepository.findAll();
		}
		log.info("Recipients were read");

		List<String> recipientsEmailAddresses = recipients.stream().map(User::getEmailAddress).collect(Collectors.toList());

		emailSender.sendSimpleEmail(requestBody.getEmailSubject(), recipientsEmailAddresses, requestBody.getEmailBody());

		log.info("Email was sent.");

		saveSentEmails(senderOpt.get(), requestBody);
		log.info("Email was saved in db");
		return buildResponseMessage(requestBody);
	}

	private void saveSentEmails(User sender, EmailRequest requestBody) {
		List<EmailHistory> senderEmails = sender.getEmails();
		if (senderEmails.isEmpty()) {
			senderEmails = new ArrayList<>();
		}

		EmailHistory email = createNewEmailHistory(requestBody);
		senderEmails.add(email);

		sender.setEmails(senderEmails);

		emailHistoryRepository.save(email);
		userRepository.save(sender);

	}

	private EmailHistory createNewEmailHistory(EmailRequest requestBody) {
		EmailHistory eh = new EmailHistory();

		eh.setContent(requestBody.getEmailBody());
		eh.setSubject(requestBody.getEmailSubject());

		eh.setRecipients(createRecipients(requestBody, eh));

		return eh;
	}

	private String createRecipients(EmailRequest requestBody, EmailHistory eh) {
		return requestBody.getRecipients().stream().map(recipientId -> recipientId + ",").collect(Collectors.joining());
	}

	private void findNotFoundUsersAndThrow(List<Long> inputUsers, List<User> foundUsers) {
		Set<Long> foundUsersIds = foundUsers.stream().map(User::getId).collect(Collectors.toSet());

		Set<Long> inputUsersIds = new HashSet<>(inputUsers);

		inputUsersIds.removeAll(foundUsersIds);

		String errorMessage;

		if (inputUsersIds.size() > 1) {
			errorMessage = inputUsersIds.stream().map(String::valueOf).collect(Collectors.joining(" ", "Users with ids: ", " were not found."));
		} else {
			errorMessage = inputUsersIds.stream().map(String::valueOf).collect(Collectors.joining("", "User with id ", " was not found."));
		}
		log.info(errorMessage);
		throw new UserNotFoundException(errorMessage);
	}

	private String buildResponseMessage(EmailRequest requestBody) {
		StringBuilder responseMessage;

		if (requestBody.getRecipients() == null || requestBody.getRecipients().isEmpty()) {
			responseMessage = new StringBuilder("An e-mail was sent to all users.");
		} else {
			responseMessage = new StringBuilder("An e-mail was sent to users: ");
			for (Long recipient : requestBody.getRecipients()) {
				responseMessage.append(recipient).append(" ");
			}
		}

		return responseMessage.toString();
	}

}
