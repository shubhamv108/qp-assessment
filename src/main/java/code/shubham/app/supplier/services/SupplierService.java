package code.shubham.app.supplier.services;

import code.shubham.app.supplier.dao.entities.Supplier;
import code.shubham.app.supplier.dao.repositories.SupplierRepository;
import code.shubham.app.suppliermodels.CreateSupplierRequest;
import code.shubham.app.suppliermodels.SupplierDTO;
import code.shubham.commons.exceptions.InvalidRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SupplierService {

	private final SupplierRepository repository;

	@Autowired
	public SupplierService(final SupplierRepository repository) {
		this.repository = repository;
	}

	public SupplierDTO create(final CreateSupplierRequest request) {
		return this.convert(this.repository.save(Supplier.builder()
			.name(request.getName())
			.address(request.getAddress())
			.phone(request.getPhone())
			.userId(request.getUserId())
			.build()));
	}

	private SupplierDTO convert(final Supplier supplier) {
		return SupplierDTO.builder()
			.supplierId(supplier.getId())
			.address(supplier.getAddress())
			.phone(supplier.getPhone())
			.userId(supplier.getUserId())
			.build();
	}

	public SupplierDTO fetchById(String supplierId) {
		return this.repository.findById(supplierId)
			.map(this::convert)
			.orElseThrow(() -> new InvalidRequestException("supplierId", "No supplier found for supplierId: %s",
					supplierId));
	}

}
