package com.email.emailservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "EMAIL_HISTORY")
public class EmailHistory {

	@Id
	@Column(name = "EMAIL_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "CONTENT")
	private String content;

	@Column(name = "SUBJECT")
	private String subject;

	@Column(name = "recipients")
	private String recipients;

}
