package co.com.nequi.franchises.application.ports.in;

import co.com.nequi.franchises.application.domain.Franchise;
import reactor.core.publisher.Mono;

public interface FranchiseUseCase {

	Mono<Franchise> save(Franchise franchise);
	
	Mono<Franchise> update(Franchise franchise);
}
