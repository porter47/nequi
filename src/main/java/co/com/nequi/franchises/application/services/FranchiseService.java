package co.com.nequi.franchises.application.services;

import co.com.nequi.franchises.application.domain.Franchise;
import co.com.nequi.franchises.application.ports.in.FranchiseUseCase;
import co.com.nequi.franchises.application.ports.out.persistence.FranchisePersistencePort;
import co.com.nequi.franchises.infrastructure.annotations.UseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@UseCase
@RequiredArgsConstructor
class FranchiseService implements FranchiseUseCase {

	private final FranchisePersistencePort franchisePersistencePort;

	public Mono<Franchise> save(Franchise franchise) {
		// TODO validate the franchise name does not exists
		franchise.validate();
		franchise.setName(franchise.getName().toUpperCase());

		return franchisePersistencePort.save(franchise);
	}

	@Override
	public Mono<Franchise> update(Franchise franchise) {
		franchise.validate();
		franchise.setName(franchise.getName().toUpperCase());

		return franchisePersistencePort.update(franchise);
	}
}
