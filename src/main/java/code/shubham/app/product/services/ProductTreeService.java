package code.shubham.app.product.services;

import code.shubham.app.product.dao.entities.Product;
import code.shubham.app.product.dao.repositories.ProductRepository;
import code.shubham.app.productcommons.IProductTreeService;
import code.shubham.app.productmodels.GetProductResponse;
import code.shubham.app.productmodels.ProductDTO;
import code.shubham.commons.exceptions.InvalidRequestException;
import code.shubham.commons.tree.dao.entities.Tree;
import code.shubham.commons.tree.service.TreeService;
import code.shubham.commons.treemodels.TreeNodeDTO;
import code.shubham.commons.treemodels.TreePathDTO;
import code.shubham.commons.treecommons.ITreeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductTreeService implements IProductTreeService {

	private final ProductRepository repository;

	private final ITreeService treeService;

	@Autowired
	public ProductTreeService(final ProductRepository repository, final TreeService treeService) {
		this.repository = repository;
		this.treeService = treeService;
	}

	@Transactional
	public ProductDTO create(final ProductDTO request) {
		final Tree tree = this.save(new TreeNodeDTO("product", null, request.getChildren()), null);
		final Product product = this.repository.save(Product.builder()
			.name(request.getName())
			.description(request.getDescription())
			.categoryId(request.getCategoryId())
			.treeId(tree.getId())
			.build());

		return this.convert(product);
	}

	public GetProductResponse fetchById(final String id) {
		final Product product = this.repository.findById(id)
			.orElseThrow(() -> new InvalidRequestException("id", "No product found with id: %s", id));

		return GetProductResponse.builder()
			.productDTO(ProductDTO.builder()
				.id(product.getId())
				.name(product.getName())
				.categoryId(product.getCategoryId())
				.description(product.getDescription())
				.build())
			.treePaths(this.fetchPathsById(product.getTreeId()))
			.build();
	}

	public List<GetProductResponse> fetchAll() {
		return this.repository.findAll()
			.stream()
			.map(product -> GetProductResponse.builder()
				.productDTO(this.convert(product))
				.treePaths(this.fetchPathsById(product.getTreeId()))
				.build())
			.collect(Collectors.toList());
	}

	@Override
	public List<TreePathDTO> fetchPathsById(final Integer treeId) {
		return this.treeService.fetchPathsById(treeId);
	}

	@Override
	public boolean isNotLeaf(final Integer treeId) {
		return this.treeService.isNotLeaf(treeId);
	}

	@Override
	public Tree save(final TreeNodeDTO node, final Integer parentId) {
		return this.treeService.save(node, parentId);
	}

	private ProductDTO convert(final Product product) {
		return ProductDTO.builder()
			.id(product.getId())
			.name(product.getName())
			.categoryId(product.getCategoryId())
			.description(product.getDescription())
			.build();
	}

}
