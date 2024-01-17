package code.shubham.app.inventory.services;

import code.shubham.app.inventory.dao.entities.Inventory;
import code.shubham.app.inventory.dao.repositories.InventoryRepository;
import code.shubham.app.inventorycommons.IInventoryService;
import code.shubham.app.inventorymodels.InventoryDTO;
import code.shubham.commons.exceptions.InvalidRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class InventoryService implements IInventoryService {

	private final InventoryRepository repository;

	@Autowired
	public InventoryService(final InventoryRepository repository) {
		this.repository = repository;
	}

	public Inventory create(final InventoryDTO request) {
		return this.repository
			.findByUserIdAndSupplierIdAndProductTreeId(request.getUserId(), request.getSupplierId(),
					request.getProductTreeId())
			.orElse(this.repository.save(Inventory.builder()
				.productTreeId(request.getProductTreeId())
				.supplierId(request.getSupplierId())
				.userId(request.getUserId())
				.price(request.getPrice())
				.quantity(request.getQuantity())
				.build()));
	}

	@Override
	public boolean updateQuantity(final String productTreeId, final String supplierId, final String userId,
			final int quantity) {
		this.repository.findByUserIdAndSupplierIdAndProductTreeId(userId, supplierId, productTreeId)
			.filter(inventory -> inventory.getQuantity() > 0)
			.orElseThrow(() -> new InvalidRequestException("No inventory found for productTreeId: %s", productTreeId));

		if (this.repository.updateQuantity(quantity, productTreeId) == 0)
			throw new InvalidRequestException("No inventory found for productTreeId: %s", productTreeId);
		return true;
	}

}
