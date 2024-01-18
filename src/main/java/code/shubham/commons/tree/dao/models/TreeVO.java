package code.shubham.commons.tree.dao.models;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TreeVO {

	private String path;

	private Integer id;

	private String title;

}
