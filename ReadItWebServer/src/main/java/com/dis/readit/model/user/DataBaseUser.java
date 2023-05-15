package com.dis.readit.model.user;

import lombok.*;

import javax.persistence.*;

@ToString
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Entity(name = "User")
@Table(name = "read_it_user")
public class DataBaseUser {

	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;

	@Column(name = "email")
	private String email;

	@Column(name = "user_name")
	private String userName;
	@Column(name = "is_admin")
	private boolean isAdmin;

}
