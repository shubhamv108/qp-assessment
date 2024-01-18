package code.shubham.app.product.web.v1.controllers;

import code.shubham.app.product.services.ProductService;
import code.shubham.app.product.web.v1.validations.ProductRequestValidator;
import code.shubham.app.productmodels.ProductDTO;
import code.shubham.commons.annotations.Role;
import code.shubham.commons.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1/products")
@RestController
public class ProductController {

	private final ProductService service;

	private final ApplicationContext applicationContext;

	@Autowired
	public ProductController(final ProductService service, final ApplicationContext applicationContext) {
		this.service = service;
		this.applicationContext = applicationContext;
	}

	@Role("ADMIN")
	@PostMapping
	public ResponseEntity<?> create(@RequestBody final ProductDTO request) {
		this.applicationContext.getBean("ProductRequestValidator", ProductRequestValidator.class)
			.validateOrThrowException(request);
		return ResponseUtils.getDataResponseEntity(HttpStatus.CREATED, this.service.create(request));
	}

}
