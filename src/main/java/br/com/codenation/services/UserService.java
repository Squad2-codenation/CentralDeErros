package br.com.codenation.services;

import static br.com.codenation.specifications.GenericSpecificationBuilder.filterRecords;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import br.com.codenation.dtos.UserDTO;
import br.com.codenation.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.codenation.entities.User;
import br.com.codenation.repositories.UserRepository;

@Service
public class UserService extends BaseService<UserRepository, User, UUID> {

	@Autowired
	public UserService(UserRepository repository) {
		super(repository);
	}

	public void delete(UUID id) {
		repository.findById(id).ifPresent(user -> {
			user.setActive(false);
			save(user);
		});
	}

	public User create(UserDTO userDTO) {
		return repository.save(User.builder()
				.name(userDTO.getName())
				.email(userDTO.getEmail())
				.token(TokenUtil.tokenGenerator(userDTO.getEmail(), userDTO.getName()))
//				.password(userDTO.getPassword())
				.build());
	}

	public List<User> findWithFilters(Map<Class<?>, Class<?>> params) {
		return repository.findAll(filterRecords(params));
	}
}
