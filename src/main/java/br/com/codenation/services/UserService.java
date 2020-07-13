package br.com.codenation.services;


import br.com.codenation.entities.User;
import br.com.codenation.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService extends BaseService<UserRepository, User, UUID> {

	@Autowired
	public UserService(UserRepository repository) {
		super(repository);
	}

	public void updateActive(UUID id, Boolean value) {
		repository.findById(id).ifPresent(user -> {
			user.setActive(value);
			save(user);
		});
	}

}
