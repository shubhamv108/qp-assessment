package code.shubham.app.productmodels;

import code.shubham.commons.treemodels.TreeNodeDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class ProductDTO {

	private String name;

	private String description;

	private String categoryId;

	private Duration duration;

	private List<TreeNodeDTO> children;

	public static ProductDTOBuilder builder() {
		return new ProductDTOBuilder();
	}

	public static class ProductDTOBuilder {

		private String name;

		private String description;

		private String categoryId;

		private Duration duration;

		private final List<TreeNodeDTO> children = new ArrayList<>();

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

		public ProductDTOBuilder duration(final Duration duration) {
			this.duration = duration;
			return this;
		}

		public ProductDTOBuilder child(final TreeNodeDTO node) {
			this.children.add(node);
			return this;
		}

		public ProductDTO build() {
			return new ProductDTO(name, description, categoryId, duration, children);
		}

	}

}
