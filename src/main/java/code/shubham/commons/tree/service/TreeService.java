package code.shubham.commons.tree.service;

import code.shubham.commons.tree.dao.entities.Tree;
import code.shubham.commons.tree.dao.repositories.TreeRepository;
import code.shubham.commons.treemodels.TreeNodeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TreeService {

	private final TreeRepository repository;

	@Autowired
	public TreeService(TreeRepository repository) {
		this.repository = repository;
	}

	public Tree save(final TreeNodeDTO node, final Integer parentId) {
		final Tree created = this.repository.save(Tree.builder().title(node.getTitle()).parentId(parentId).build());
		node.getChildren().forEach(child -> this.save(child, created.getId()));
		return created;
	}

}
