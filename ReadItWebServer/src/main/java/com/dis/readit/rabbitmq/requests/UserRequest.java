package com.dis.readit.rabbitmq.requests;

import com.dis.readit.model.user.DataBaseUser;
import lombok.Data;

@Data
public class UserRequest {

	public static final int USER_OPERATION_DELETE = 0;
	public static final int USER_OPERATION_CREATE = 1;
	public static final int USER_OPERATION_UPDATE = 2;

	private Integer operation;

	private Integer id;

	private String userName;

	private String emailAddress;

	public static UserRequest createForOperation (DataBaseUser user, int operation) {

		UserRequest userRequest = createFromUser(user);
		userRequest.setOperation(operation);

		return userRequest;
	}

	private static UserRequest createFromUser(DataBaseUser user) {
		UserRequest userRequest = new UserRequest();

		userRequest.setId(user.getUserId());
		userRequest.setUserName(user.getUserName());
		userRequest.setEmailAddress(user.getEmail());
		return userRequest;
	}

}
