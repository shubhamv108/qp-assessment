package code.shubham.app.product.web.v1.validatiors;

import code.shubham.app.productmodels.ProductDTO;
import code.shubham.commons.treemodels.TreeNodeDTO;
import code.shubham.commons.utils.StringUtils;
import code.shubham.commons.validators.AbstractRequestValidator;
import code.shubham.commons.validators.IValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Scope("prototype")
@Component("ProductRequestValidator")
public class ProductRequestValidator extends AbstractRequestValidator<ProductDTO> {

	@Value("${product.tree.max.depth: 7}")
	private Integer maxDepth;

	@Override
	public IValidator<ProductDTO> validate(final ProductDTO request) {
		super.validate(request);

		if (StringUtils.isEmpty(request.getName()))
			this.putMessage("name", MUST_NOT_BE_EMPTY, "name");
		if (StringUtils.isEmpty(request.getDescription()))
			this.putMessage("description", MUST_NOT_BE_EMPTY, "description");
		if (this.isNotValidId(request.getCategoryId()))
			this.putMessage("categoryId", MUST_NOT_BE_EMPTY, "categoryId");

		this.validateTree(new TreeNodeDTO("$$$", null, request.getChildren()), 1);

		return this;
	}

	private void validateTree(final TreeNodeDTO node, final int depth) {
		if (node == null)
			return;

		if (depth > this.maxDepth)
			this.putMessage("children", "Depth of product tree cannot be more than %s", this.maxDepth);

		if (node.getTitle() == null) {
			final String key = String.format("children.[%s].title", depth);
			this.putMessage(key, MUST_NOT_BE_EMPTY, key);
		}

		Optional.ofNullable(node.getChildren())
			.ifPresent(children -> children.forEach(child -> this.validateTree(child, 2)));
	}

}
