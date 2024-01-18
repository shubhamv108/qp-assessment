package code.shubham.app.product.services;

import code.shubham.app.product.dao.entities.ProductCategory;
import code.shubham.app.product.dao.repositories.ProductCategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class ProductCategoryService {

	private final ProductCategoryRepository repository;

	@Autowired
	public ProductCategoryService(final ProductCategoryRepository repository) {
		this.repository = repository;
	}

	public ProductCategory getOrCreate(final String name, final String description) {
		return this.repository.findByName(name)
			.orElse(this.repository.save(ProductCategory.builder().name(name).description(description).build()));
	}

	public Optional<ProductCategory> getById(final String id) {
		return this.repository.findById(id);
	}

}
