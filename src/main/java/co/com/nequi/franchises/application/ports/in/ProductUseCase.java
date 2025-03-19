package co.com.nequi.franchises.application.ports.in;

import java.util.List;

import co.com.nequi.franchises.application.domain.Product;
import reactor.core.publisher.Mono;

public interface ProductUseCase {

	Mono<Product> save(Product product);
	
	Mono<Product> update(Product product);
	
	Mono<Void> delete(String productId);
	
	Mono<List<Product>> findMaxStock(String franchiseId);
}