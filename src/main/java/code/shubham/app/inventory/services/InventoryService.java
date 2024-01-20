package code.shubham.app.inventory.services;

import code.shubham.app.inventory.dao.entities.Inventory;
import code.shubham.app.inventory.dao.entities.InventoryOperation;
import code.shubham.app.inventory.dao.entities.InventoryOperationType;
import code.shubham.app.inventory.dao.repositories.InventoryOperationRepository;
import code.shubham.app.inventory.dao.repositories.InventoryRepository;
import code.shubham.app.inventorycommons.IInventoryService;
import code.shubham.app.inventorymodels.CreateInventoryRequest;
import code.shubham.app.inventorymodels.InventoryDTO;
import code.shubham.app.inventorymodels.UpdateInventoryRequest;
import code.shubham.app.productcommons.IProductTreeService;
import code.shubham.commons.exceptions.InvalidRequestException;
import code.shubham.app.inventory.convertors.InventoryDTOConvertor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class InventoryService implements IInventoryService {

	private final InventoryRepository repository;

	private final IProductTreeService productService;

	private final InventoryOperationRepository operationRepository;

	@Autowired
	public InventoryService(final InventoryRepository repository, final IProductTreeService productService,
			final InventoryOperationRepository operationRepository) {
		this.repository = repository;
		this.productService = productService;
		this.operationRepository = operationRepository;
	}

	public InventoryDTO create(final CreateInventoryRequest request) {
		if (this.productService.isNotLeaf(request.getProductTreeId()))
			throw new InvalidRequestException("productTreeId", "No valid product tree found for productTreeId: %s",
					"" + request.getProductTreeId());

		return InventoryDTOConvertor.convert(this.repository
			.findByUserIdAndSupplierIdAndProductTreeId(request.getUserId(), request.getSupplierId(),
					request.getProductTreeId())
			.orElse(this.repository.save(Inventory.builder()
				.productTreeId(request.getProductTreeId())
				.supplierId(request.getSupplierId())
				.userId(request.getUserId())
				.price(request.getPrice())
				.quantity(request.getQuantity())
				.build())));
	}

	public List<Inventory> fetchAll(final Integer productTreeId, final String supplierId) {
		return Optional.ofNullable(supplierId)
			.map(supplier -> this.repository.findByProductTreeIdAndSupplierId(productTreeId, supplierId))
			.orElse(this.repository.findByProductTreeId(productTreeId));
	}

	@Transactional
	@Override
	public boolean applyQuantityOperation(final String id, final int quantity, final String referenceId) {
		return this.incrementQuantity(id, quantity, referenceId, InventoryOperationType.APPLY);
	}

	@Transactional
	@Override
	public boolean revertQuantityOperation(final String referenceId) {
		final List<InventoryOperation> operations = this.operationRepository.findAllByReferenceId(referenceId);
		if (operations.size() > 1)
			throw new InvalidRequestException("referenceId", "no revertable apply operation found for referenceId: %s",
					referenceId);

		if (operations.stream()
			.anyMatch(inventoryOperation -> InventoryOperationType.REVERT.equals(inventoryOperation.getType())))
			throw new InvalidRequestException("referenceId",
					"inventory operation with referenceId: %s already reverted", referenceId);

		final var applyOperation = operations.stream()
			.filter(inventoryOperation -> InventoryOperationType.APPLY.equals(inventoryOperation.getType()))
			.findFirst();
		if (applyOperation.isEmpty())
			throw new InvalidRequestException("referenceId", "no apply operation found for referenceId: %s",
					referenceId);

		return this.incrementQuantity(applyOperation.get().getInventoryId(), -applyOperation.get().getQuantity(),
				referenceId, InventoryOperationType.REVERT);
	}

	private boolean incrementQuantity(final String id, final int quantity, final String referenceId,
			final InventoryOperationType type) {
		if (this.repository.incrementQuantity(quantity, id) == 0)
			throw new InvalidRequestException("No inventory found for id: %s", id);
		this.operationRepository.save(InventoryOperation.builder()
			.quantity(quantity)
			.referenceId(referenceId)
			.type(type)
			.inventoryId(id)
			.build());
		return true;
	}

	@Override
	public boolean hasQuantity(final String id, final int quantity) {
		if (this.repository.hasQuantity(quantity, id) == 0)
			throw new InvalidRequestException("inventoryId", "No inventory found for id: %s", id);
		return true;
	}

	public InventoryDTO update(final String inventoryId, final UpdateInventoryRequest request) {
		final Inventory inventory = this.repository.findByIdAndUserId(inventoryId, request.getUserId())
			.orElseThrow(() -> new InvalidRequestException("inventoryId", "no inventory found by inventoryId: %s",
					inventoryId));
		if (request.getPrice() != null)
			inventory.setPrice(request.getPrice());
		if (request.getQuantity() != null)
			inventory.setQuantity(request.getQuantity());
		return InventoryDTOConvertor.convert(this.repository.save(inventory));
	}

}
