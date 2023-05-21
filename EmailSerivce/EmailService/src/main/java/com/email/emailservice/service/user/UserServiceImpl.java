package com.email.emailservice.service.user;

import com.email.emailservice.Dtos.UserRequest;
import com.email.emailservice.model.User;
import com.email.emailservice.repository.UserRepository;
import com.email.emailservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

	private final UserRepository repository;

	public UserServiceImpl(UserRepository repository) {
		this.repository = repository;
	}

	@Override
	public String handleUserOperation(UserRequest request) {
		log.info("Enter User Operation");
		if (request.getOperation().equals(0)) {
			log.info("Delete User");
			repository.deleteById(request.getId());
		} else if (request.getOperation().equals(1) || request.getOperation().equals(2)) {
			log.info("Create/update User");
			User user = createUserFromRequest(request);
			try {
				try {
					repository.save(user);
				} catch (Exception e) {
					log.info(e.getMessage(), e);
				}
			} catch (Exception e) {
				log.info(e.getMessage());
			}
		} else {
			return "Operation " + request.getOperation() + " is not valid.";
		}

		return "User data saved.";
	}

	private User createUserFromRequest(UserRequest request) {
		User newUser = new User();
		newUser.setId(request.getId());
		newUser.setUserName(request.getUserName());
		newUser.setEmailAddress(request.getEmailAddress());
		return newUser;
	}
}
