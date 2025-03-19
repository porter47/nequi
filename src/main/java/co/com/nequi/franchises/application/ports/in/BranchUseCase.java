package co.com.nequi.franchises.application.ports.in;

import co.com.nequi.franchises.application.domain.Branch;
import reactor.core.publisher.Mono;

public interface BranchUseCase {

	Mono<Branch> save(Branch branch);
	
	Mono<Branch> update(Branch branch);
}
