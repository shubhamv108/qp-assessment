package code.shubham.app.product.web.v1.controllers;

import code.shubham.app.product.services.ProductTreeService;
import code.shubham.app.product.web.v1.validatiors.ProductRequestValidator;
import code.shubham.app.productmodels.ProductDTO;
import code.shubham.commons.annotations.Role;
import code.shubham.commons.utils.ResponseUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/products")
@Tag(name = "Product")
@ConditionalOnProperty(prefix = "service", name = "module", havingValue = "web")
public class ProductController {

	private final ProductTreeService service;

	private final ApplicationContext applicationContext;

	@Autowired
	public ProductController(final ProductTreeService service, final ApplicationContext applicationContext) {
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

	@GetMapping("/{productId}")
	public ResponseEntity<?> get(@PathVariable("productId") final String productId) {
		return ResponseUtils.getDataResponseEntity(HttpStatus.FOUND, this.service.fetchById(productId));
	}

	@GetMapping
	public ResponseEntity<?> get() {
		return ResponseUtils.getDataResponseEntity(HttpStatus.FOUND, this.service.fetchAll());
	}

}
