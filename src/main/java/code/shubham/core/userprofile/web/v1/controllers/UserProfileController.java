package code.shubham.core.userprofile.web.v1.controllers;

import code.shubham.commons.contexts.UserIDContextHolder;
import code.shubham.commons.utils.ResponseUtils;
import code.shubham.commons.utils.Utils;
import code.shubham.core.userprofile.services.UserProfileService;
import code.shubham.core.userprofile.web.v1.validators.UpdateUserProfileRequestValidator;
import code.shubham.core.userprofilemodels.UpdateUserProfileRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users/profiles")
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "User Profiles")
@ConditionalOnProperty(prefix = "service", name = "module", havingValue = "web")
public class UserProfileController {

	private final UserProfileService service;

	@Autowired
	public UserProfileController(final UserProfileService service) {
		this.service = service;
	}

	@Operation(description = "PUT Profile Information",
			summary = "Idempotent endpoint for setting user profile information like address etc",
			responses = { @ApiResponse(description = "Success", responseCode = "200"),
					@ApiResponse(description = "Unauthenticated", responseCode = "401") })
	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestBody final UpdateUserProfileRequest request) {
		new UpdateUserProfileRequestValidator().validateOrThrowException(request);
		return ResponseUtils.getDataResponseEntity(HttpStatus.OK,
				this.service.update(UserIDContextHolder.get(), request.getAddress()));
	}

	@GetMapping
	public ResponseEntity<?> getByUserId(@RequestParam("userId") final String userId) {
		Utils.validateUserOrThrowException(userId);
		return ResponseUtils.getDataResponseEntity(HttpStatus.OK, this.service.fetchByUserId(userId));
	}

}
