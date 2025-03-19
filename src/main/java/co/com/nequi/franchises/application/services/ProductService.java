package co.com.nequi.franchises.application.services;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import co.com.nequi.franchises.application.domain.Branch;
import co.com.nequi.franchises.application.domain.Product;
import co.com.nequi.franchises.application.ports.in.ProductUseCase;
import co.com.nequi.franchises.application.ports.out.persistence.BranchPersistencePort;
import co.com.nequi.franchises.application.ports.out.persistence.ProductPersistencePort;
import co.com.nequi.franchises.infrastructure.annotations.UseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@UseCase
@RequiredArgsConstructor
class ProductService implements ProductUseCase {

	private final ProductPersistencePort productPersistencePort;

	private final BranchPersistencePort branchPersistencePort;

	@Override
	public Mono<Product> save(Product product) {
		// TODO validate the product name does not exists
		product.validate();
		product.nameToUpper();

		var branch = branchPersistencePort.getById(product.getBranchId());

		return branch.flatMap(br -> saveProduct(product, br));
	}

	@Override
	public Mono<Product> update(Product product) {
		product.validateUpdate();
		product.nameToUpper();

		return productPersistencePort.update(product);
	}

	@Override
	public Mono<Void> delete(String productId) {
		return productPersistencePort.delete(productId);
	}

	private Mono<Product> saveProduct(Product product, Branch branch) {
		product.setBranchId(branch.getId());
		product.setBranchName(branch.getName());
		return productPersistencePort.save(product);
	}

	@Override
	public Mono<List<Product>> findMaxStock(String franchiseId) {
		return productPersistencePort.findMaxStock(franchiseId).map(items -> {

			var branches = items.stream().filter(item -> item.getStock() == null).toList();

			items.removeIf(item -> item.getStock() == null);

			items.forEach(p -> branches.forEach(b -> {
				if (p.getId().contains(b.getId())) {
					p.setBranchName(b.getName());
					return;
				}
			}));

			return items.stream()
					.collect(Collectors.groupingBy(Product::getBranchName,
							Collectors.maxBy(Comparator.comparing(Product::getStock))))
					.values().stream().flatMap(Optional::stream).toList();
		});
	}
}
