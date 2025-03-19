package co.com.nequi.franchises.application.ports.out.persistence;

import java.util.List;

import co.com.nequi.franchises.application.domain.Product;
import reactor.core.publisher.Mono;

public interface ProductPersistencePort {

	Mono<Product> save(Product product);

	Mono<Product> getById(String productId);

	Mono<Void> delete(String id);

	Mono<Product> update(Product product);

	Mono<List<Product>> findMaxStock(String franchiseId);
}
