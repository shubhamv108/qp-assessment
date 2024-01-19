package code.shubham.app.productmodels;

import code.shubham.commons.treemodels.TreePathDTO;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
public class GetProductResponse {

	private ProductDTO productDTO;

	private List<TreePathDTO> treePaths;

}
