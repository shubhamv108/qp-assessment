package code.shubham.app.productmodels;

import code.shubham.commons.treemodels.TreeNodeDTO;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class ProductDTO {

	private String productId;

	private String name;

	private String description;

	private String categoryId;

	private List<TreeNodeDTO> children;

	public static ProductDTOBuilder builder() {
		return new ProductDTOBuilder();
	}

	public static class ProductDTOBuilder {

		private String id;

		private String name;

		private String description;

		private String categoryId;

		private final List<TreeNodeDTO> children = new ArrayList<>();

		public ProductDTOBuilder id(final String id) {
			this.id = id;
			return this;
		}

		public ProductDTOBuilder name(final String name) {
			this.name = name;
			return this;
		}

		public ProductDTOBuilder description(final String description) {
			this.description = description;
			return this;
		}

		public ProductDTOBuilder categoryId(final String categoryId) {
			this.categoryId = categoryId;
			return this;
		}

		public ProductDTOBuilder child(final TreeNodeDTO node) {
			this.children.add(node);
			return this;
		}

		public ProductDTO build() {
			return new ProductDTO(id, name, description, categoryId, children);
		}

	}

}
