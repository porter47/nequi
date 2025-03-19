package co.com.nequi.franchises.application.ports.out.persistence;

import co.com.nequi.franchises.application.domain.Franchise;
import reactor.core.publisher.Mono;

public interface FranchisePersistencePort {

	Mono<Franchise> getById(String id);

	Mono<Franchise> save(Franchise franchise);

	Mono<Franchise> update(Franchise franchise);
}