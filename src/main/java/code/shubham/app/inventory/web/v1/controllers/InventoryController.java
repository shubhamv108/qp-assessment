package code.shubham.app.inventory.web.v1.controllers;

import code.shubham.app.inventory.services.InventoryService;
import code.shubham.app.inventory.web.v1.validations.InventoryRequestValidator;
import code.shubham.app.inventorymodels.InventoryDTO;
import code.shubham.commons.annotations.Role;
import code.shubham.commons.utils.ResponseUtils;
import code.shubham.commons.utils.Utils;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/inventories")
@SecurityRequirement(name = "BearerAuth")
@Role("ADMIN")
@Tag(name = "User")
@ConditionalOnProperty(prefix = "service", name = "module", havingValue = "web")
public class InventoryController {

	private final InventoryService service;

	@Autowired
	public InventoryController(final InventoryService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<?> create(@RequestBody final InventoryDTO request) {
		new InventoryRequestValidator().validateOrThrowException(request);
		Utils.validateUserOrThrowException(request.getUserId());
		return ResponseUtils.getDataResponseEntity(HttpStatus.CREATED, this.service.create(request));
	}

}
