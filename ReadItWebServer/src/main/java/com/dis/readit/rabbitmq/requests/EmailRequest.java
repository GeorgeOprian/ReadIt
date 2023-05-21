package com.dis.readit.rabbitmq.requests;

import com.dis.readit.model.user.DataBaseUser;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
public class EmailRequest implements Serializable {

	@JsonProperty("sender")
	private Integer sender;

	@JsonProperty("recipients")
	private List<Integer> recipients;

	@JsonProperty("emailSubject")
	private String emailSubject;

	@JsonProperty("emailBody")
	private String emailBody;


	public static EmailRequest createEmailForUser(int senderId, List<Integer> recipients, String emailSubject, String emailBody) {
		EmailRequest emailRequest = new EmailRequest();

		emailRequest.setSender(senderId);
		emailRequest.setRecipients(recipients);
		emailRequest.setEmailSubject(emailSubject);
		emailRequest.setEmailBody(emailBody);

		return emailRequest;
	}

}
