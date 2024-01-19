package code.shubham.core.iam.web.v1.controllers;

import code.shubham.commons.contexts.UserIDContextHolder;
import code.shubham.commons.utils.ResponseUtils;
import code.shubham.core.iam.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "User")
@ConditionalOnProperty(prefix = "service", name = "module", havingValue = "web")
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(final UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/info")
	public ResponseEntity<?> getInfo() {
		return ResponseUtils.getDataResponseEntity(HttpStatus.OK,
				this.userService.fetchById(UserIDContextHolder.get()));
	}

	@GetMapping("/{userId}")
	public ResponseEntity<?> getById(@PathVariable("userId") final String userId) {
		return ResponseUtils.getDataResponseEntity(HttpStatus.OK, this.userService.fetchById(userId));
	}

}
