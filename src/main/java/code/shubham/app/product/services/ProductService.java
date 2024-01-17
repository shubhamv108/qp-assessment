package code.shubham.app.product.services;

import code.shubham.app.product.dao.entities.Product;
import code.shubham.app.product.dao.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Slf4j
@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;

	@Transactional
	public Product create(final String name, final String description, final String categoryId,
			final Duration duration) {
		final Product product = this.repository.save(Product.builder()
			.name(name)
			.description(description)
			.categoryId(categoryId)
			.expirationDuration(duration)
			.build());

		return product;
	}

}
