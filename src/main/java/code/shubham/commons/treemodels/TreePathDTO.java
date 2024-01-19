package code.shubham.commons.treemodels;

import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TreePathDTO {

	private String path;

	private Integer id;

	private String title;

}
