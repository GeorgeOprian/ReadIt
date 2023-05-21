package com.email.emailservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@Entity
@Table(name = "EMAIL_USER")
public class User {

	@Id
	@Column(name = "USER_ID")
	private Long id;

	@Column(name = "USER_NAME")
	private String userName;

	@Column(name = "EMAIL")
	private String emailAddress;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "SENDER_ID")
	private List<EmailHistory> emails = new ArrayList<>();

}
