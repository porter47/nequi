package co.com.nequi.franchises.application.ports.out.persistence;

import co.com.nequi.franchises.application.domain.Branch;
import reactor.core.publisher.Mono;

public interface BranchPersistencePort {
	
	Mono<Branch> getById(String id);

	Mono<Branch> save(Branch branch);
	
	Mono<Branch> update(Branch branch);
}
