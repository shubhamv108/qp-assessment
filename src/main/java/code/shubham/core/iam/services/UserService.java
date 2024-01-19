package code.shubham.core.iam.services;

import code.shubham.core.iam.dao.entities.User;
import code.shubham.core.iam.dao.repositories.UserRepository;
import code.shubham.core.iamcommons.IUserService;
import code.shubham.core.iammodels.GetOrCreateUser;
import code.shubham.core.iammodels.GetUserResponse;
import code.shubham.core.iammodels.UserDTO;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Transactional
@Service
public class UserService implements IUserService {

	private final UserRepository repository;

	private final UserRoleService userRoleService;

	@Autowired
	public UserService(final UserRepository repository, final UserRoleService userRoleService) {
		this.repository = repository;
		this.userRoleService = userRoleService;
	}

	@Override
	public GetUserResponse getOrCreate(final GetOrCreateUser.Request request) {
		final Optional<User> existing = this.repository.findByEmail(request.getEmail());

		User persisted = null;
		if (existing.isPresent())
			persisted = existing.get();
		else
			persisted = this.create(User.builder().name(request.getName()).email(request.getEmail()).build());
		return GetUserResponse.builder()
			.user(new UserDTO(persisted.getId(), persisted.getEmail()))
			.roles(this.userRoleService.getAllRoles(persisted.getId()))
			.build();
	}

	public User create(final User user) {
		final User persisted = this.repository.save(user);
		this.userRoleService.setRoleToUser("USER", user.getId());
		return persisted;
	}

	@Override
	public GetUserResponse fetchById(final String userId) {
		return this.repository.findById(userId)
			.map(user -> GetUserResponse.builder()
				.user(new UserDTO(userId, user.getEmail()))
				.roles(this.userRoleService.getAllRoles(userId))
				.build())
			.orElse(null);
	}

}
