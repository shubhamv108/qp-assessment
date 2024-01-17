package code.shubham.app.product.web.v1.controllers;

import code.shubham.app.product.dao.entities.ProductCategory;
import code.shubham.app.product.services.ProductCategoryService;
import code.shubham.app.product.web.v1.validations.ProductCategoryRequestValidator;
import code.shubham.app.productmodels.ProductCategoryDTO;
import code.shubham.commons.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1/products/categories")
@RestController
public class ProductCategoryController {

	private final ProductCategoryService service;

	@Autowired
	public ProductCategoryController(final ProductCategoryService service) {
		this.service = service;
	}

	@PutMapping
	public ResponseEntity<?> put(@RequestBody final ProductCategoryDTO request) {
		new ProductCategoryRequestValidator().validateOrThrowException(request);
		return ResponseUtils.getDataResponseEntity(HttpStatus.CREATED,
				this.service.getOrCreate(request.getName(), request.getDescription()));
	}

}
