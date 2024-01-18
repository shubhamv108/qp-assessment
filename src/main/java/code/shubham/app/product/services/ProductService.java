package code.shubham.app.product.services;

import code.shubham.app.product.dao.entities.Product;
import code.shubham.app.product.dao.repositories.ProductRepository;
import code.shubham.app.productmodels.ProductDTO;
import code.shubham.commons.tree.service.TreeService;
import code.shubham.commons.treemodels.TreeNodeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Slf4j
@Service
public class ProductService {

	private final ProductRepository repository;

	private final TreeService treeService;

	@Autowired
	public ProductService(final ProductRepository repository, final TreeService treeService) {
		this.repository = repository;
		this.treeService = treeService;
	}

	@Transactional
	public Product create(final ProductDTO request) {
		final Product product = this.repository.save(Product.builder()
			.name(request.getName())
			.description(request.getDescription())
			.categoryId(request.getCategoryId())
			.expirationDuration(request.getDuration())
			.build());

		this.treeService.save(new TreeNodeDTO("product", null, request.getChildren()), null);

		return product;
	}

}
