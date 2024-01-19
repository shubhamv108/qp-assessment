package code.shubham.app.inventory.web.v1.controllers;

import code.shubham.app.inventory.convertors.InventoryDTOConvertor;
import code.shubham.app.inventory.services.InventoryService;
import code.shubham.app.inventory.web.v1.validations.CreteInventoryRequestValidator;
import code.shubham.app.inventory.web.v1.validations.UpdateInventoryRequestValidator;
import code.shubham.app.inventorymodels.CreateInventoryRequest;
import code.shubham.app.inventorymodels.InventoryDTO;
import code.shubham.app.inventorymodels.UpdateInventoryRequest;
import code.shubham.commons.annotations.Role;
import code.shubham.commons.utils.ResponseUtils;
import code.shubham.commons.utils.Utils;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/inventories")
@Tag(name = "Inventory")
@ConditionalOnProperty(prefix = "service", name = "module", havingValue = "web")
public class InventoryController {

	private final InventoryService service;

	@Autowired
	public InventoryController(final InventoryService service) {
		this.service = service;
	}

	@SecurityRequirement(name = "BearerAuth")
	@Role("ADMIN")
	@PostMapping
	public ResponseEntity<?> create(@RequestBody final CreateInventoryRequest request) {
		new CreteInventoryRequestValidator().validateOrThrowException(request);
		Utils.validateUserOrThrowException(request.getUserId());
		return ResponseUtils.getDataResponseEntity(HttpStatus.CREATED, this.service.create(request));
	}

	@SecurityRequirement(name = "BearerAuth")
	@Role("ADMIN")
	@PatchMapping("/{inventoryId}")
	public ResponseEntity<?> update(@PathVariable("inventoryId") final String inventoryId,
			@RequestBody final UpdateInventoryRequest request) {
		new UpdateInventoryRequestValidator().validateOrThrowException(request);
		Utils.validateUserOrThrowException(request.getUserId());
		return ResponseUtils.getDataResponseEntity(HttpStatus.OK, this.service.update(inventoryId, request));
	}

	@GetMapping
	public ResponseEntity<?> get(@RequestParam("productTreeId") final Integer productTreeId,
			@RequestParam(value = "supplierId", required = false) final String supplierId) {
		return ResponseUtils.getDataResponseEntity(HttpStatus.FOUND,
				this.service.fetchAll(productTreeId, supplierId)
					.stream()
					.map(InventoryDTOConvertor::convert)
					.collect(Collectors.toList()));
	}

}
