package code.shubham.commons.tree.service;

import code.shubham.commons.tree.dao.entities.Tree;
import code.shubham.commons.tree.dao.repositories.TreeRepository;
import code.shubham.commons.treecommons.ITreeService;
import code.shubham.commons.treemodels.TreeNodeDTO;
import code.shubham.commons.treemodels.TreePathDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TreeService implements ITreeService {

	private final TreeRepository repository;

	@Autowired
	public TreeService(TreeRepository repository) {
		this.repository = repository;
	}

	@Override
	public Tree save(final TreeNodeDTO node, final Integer parentId) {
		final Tree created = this.repository.save(Tree.builder().title(node.getTitle()).parentId(parentId).build());
		Optional.ofNullable(node.getChildren())
			.ifPresent(children -> children.forEach(child -> this.save(child, created.getId())));
		return created;
	}

	@Override
	public List<TreePathDTO> fetchPathsById(final Integer parentId) {
		return this.repository.fetchPathsByParentId(parentId)
			.stream()
			.map(result -> TreePathDTO.builder()
				.path((String) result[0])
				.id((Integer) result[1])
				.title((String) result[2])
				.build())
			.toList();
	}

	@Override
	public boolean isNotLeaf(final Integer id) {
		return this.repository.findByParentId(id).isPresent() || this.repository.findById(id).isEmpty();
	}

}
