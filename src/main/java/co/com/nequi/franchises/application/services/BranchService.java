package co.com.nequi.franchises.application.services;

import co.com.nequi.franchises.application.domain.Branch;
import co.com.nequi.franchises.application.domain.Franchise;
import co.com.nequi.franchises.application.domain.MessagesEnum;
import co.com.nequi.franchises.application.exceptions.NequiException;
import co.com.nequi.franchises.application.ports.in.BranchUseCase;
import co.com.nequi.franchises.application.ports.out.persistence.BranchPersistencePort;
import co.com.nequi.franchises.application.ports.out.persistence.FranchisePersistencePort;
import co.com.nequi.franchises.infrastructure.annotations.UseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@UseCase
@RequiredArgsConstructor
class BranchService implements BranchUseCase {

	private final FranchisePersistencePort franchisePersistencePort;

	private final BranchPersistencePort branchPersistencePort;

	@Override
	public Mono<Branch> save(Branch branch) {
		// TODO validate the franchise name does not exists
		branch.validate();
		branch.setName(branch.getName().toUpperCase());

		var franchise = franchisePersistencePort.getById(branch.getFranchiseId());

		return franchise.flatMap(fr -> saveBranch(branch, fr));
	}

	private Mono<Branch> saveBranch(Branch branch, Franchise franchise) {

		if (franchise == null) {
			throw new NequiException(MessagesEnum.FR_NOT_FOUND);
		}

		branch.setFranchiseId(franchise.getId());
		branch.setFranchiseName(franchise.getName());

		return branchPersistencePort.save(branch);
	}

	@Override
	public Mono<Branch> update(Branch branch) {
		branch.validateForUpdate();
		branch.setName(branch.getName().toUpperCase());

		return branchPersistencePort.update(branch);
	}
}
