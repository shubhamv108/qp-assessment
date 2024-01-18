package code.shubham.commons.treemodels;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TreeNodeDTO {

	@NotNull
	@NotEmpty
	@Min(3)
	@Min(64)
	private String title;

	private Map<String, Object> data;

	private List<TreeNodeDTO> children;

	public static TreeNodeDTOBuilder builder() {
		return new TreeNodeDTOBuilder();
	}

	public static class TreeNodeDTOBuilder {

		private String title;

		private final Map<String, Object> data = new LinkedHashMap<>();

		private final List<TreeNodeDTO> children = new ArrayList<>();

		public TreeNodeDTOBuilder title(final String title) {
			this.title = title;
			return this;
		}

		public TreeNodeDTOBuilder child(final TreeNodeDTO node) {
			this.children.add(node);
			return this;
		}

		public TreeNodeDTOBuilder data(final String key, final Object value) {
			this.data.put(key, value);
			return this;
		}

		public TreeNodeDTO build() {
			return new TreeNodeDTO(title, data, children);
		}

	}

}
