package code.shubham.commons.treecommons;

import code.shubham.commons.tree.dao.entities.Tree;
import code.shubham.commons.treemodels.TreeNodeDTO;
import code.shubham.commons.treemodels.TreePathDTO;

import java.util.List;

public interface ITreeService {

	List<TreePathDTO> fetchPathsById(Integer id);

	boolean isNotLeaf(Integer id);

	Tree save(TreeNodeDTO product, Integer parentId);

}