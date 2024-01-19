package code.shubham.app.product.web.v1.controllers;

import code.shubham.app.product.services.ProductCategoryService;
import code.shubham.app.product.web.v1.validatiors.ProductCategoryRequestValidator;
import code.shubham.app.productmodels.ProductCategoryDTO;
import code.shubham.app.productmodels.PutProductCategoryRequest;
import code.shubham.commons.annotations.Role;
import code.shubham.commons.utils.ResponseUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/products/categories")
@Tag(name = "ProductCategory")
@ConditionalOnProperty(prefix = "service", name = "module", havingValue = "web")
public class ProductCategoryController {

	private final ProductCategoryService service;

	@Autowired
	public ProductCategoryController(final ProductCategoryService service) {
		this.service = service;
	}

	@Role("ADMIN")
	@PutMapping
	public ResponseEntity<?> put(@RequestBody final PutProductCategoryRequest request) {
		new ProductCategoryRequestValidator().validateOrThrowException(request);
		return ResponseUtils.getDataResponseEntity(HttpStatus.CREATED,
				Optional.ofNullable(this.service.getOrCreate(request.getName(), request.getDescription()))
					.map(category -> ProductCategoryDTO.builder()
						.name(category.getName())
						.description(category.getDescription())
						.productCategoryId(category.getId())
						.build())
					.orElse(null));
	}

	@GetMapping
	public ResponseEntity<?> getAll() {
		return ResponseUtils.getDataResponseEntity(HttpStatus.FOUND,
				this.service.fetchAll()
					.stream()
					.map(category -> ProductCategoryDTO.builder()
						.name(category.getName())
						.description(category.getDescription())
						.productCategoryId(category.getId())
						.build())
					.collect(Collectors.toList()));
	}

}
