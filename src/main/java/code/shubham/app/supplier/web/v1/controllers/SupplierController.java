package code.shubham.app.supplier.web.v1.controllers;

import code.shubham.app.supplier.services.SupplierService;
import code.shubham.app.supplier.web.v1.validators.CreateSupplierRequestValidator;
import code.shubham.app.suppliermodels.CreateSupplierRequest;
import code.shubham.commons.annotations.Role;
import code.shubham.commons.utils.ResponseUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/suppliers")
@Tag(name = "Supplier")
@ConditionalOnProperty(prefix = "service", name = "module", havingValue = "web")
public class SupplierController {

	private final SupplierService service;

	@Autowired
	public SupplierController(final SupplierService service) {
		this.service = service;
	}

	@Role("ADMIN")
	@PostMapping
	public ResponseEntity<?> create(@RequestBody final CreateSupplierRequest request) {
		new CreateSupplierRequestValidator().validateOrThrowException(request);
		return ResponseUtils.getDataResponseEntity(HttpStatus.CREATED, this.service.create(request));
	}

	@GetMapping("/{supplierId}")
	public ResponseEntity<?> get(@PathVariable("supplierId") final String supplierId) {
		return ResponseUtils.getDataResponseEntity(HttpStatus.FOUND, this.service.fetchById(supplierId));
	}

}
