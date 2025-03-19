package co.com.nequi.franchises.adapter.out.persistence.dynamodb.branch;

import java.util.UUID;

import co.com.nequi.franchises.adapter.out.persistence.dynamodb.DynamoClientConfig;
import co.com.nequi.franchises.adapter.out.persistence.dynamodb.DynamoSeparatorUtil;
import co.com.nequi.franchises.adapter.out.persistence.dynamodb.FranchiseDynamoEntity;
import co.com.nequi.franchises.application.domain.Branch;
import co.com.nequi.franchises.application.domain.MessagesEnum;
import co.com.nequi.franchises.application.exceptions.NequiException;
import co.com.nequi.franchises.application.ports.out.persistence.BranchPersistencePort;
import co.com.nequi.franchises.infrastructure.annotations.Adapter;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;

@Adapter
@Slf4j
class BranchDynamoAdapter implements BranchPersistencePort {

	private final DynamoDbTable<FranchiseDynamoEntity> franchiseTable;

	public BranchDynamoAdapter(DynamoDbEnhancedClient client) {
		this.franchiseTable = client.table(DynamoClientConfig.TABLE_NAME, FranchiseDynamoEntity.TABLE_SCHEMA);
	}

	@Override
	public Mono<Branch> save(Branch branch) {

		log.info("Start saving branch {}", branch.getName());

		var entity = new FranchiseDynamoEntity(branch.getFranchiseId(),
				branch.getFranchiseId() + DynamoSeparatorUtil.SEPARATOR + UUID.randomUUID().toString(),
				branch.getName());

		return Mono.defer(() -> {
			franchiseTable.putItem(entity);
			return Mono.just(entityToDomain(entity, branch));
		});
	}

	@Override
	public Mono<Branch> getById(String id) {

		log.info("Start query branch with id {}", id);

		var keys = DynamoSeparatorUtil.separateKeys(id);

		return Mono.defer(() -> {
			var entity = franchiseTable.getItem(new FranchiseDynamoEntity(keys.getValueOne(),
					keys.getValueOne() + DynamoSeparatorUtil.SEPARATOR + keys.getValueTwo(), null));

			if (entity == null) {
				throw new NequiException(MessagesEnum.BR_NOT_FOUND);
			}

			return Mono.just(new Branch(entity.getMeta(), entity.getName(), entity.getId(), null));
		});
	}

	private Branch entityToDomain(FranchiseDynamoEntity entity, Branch branch) {

		if (entity == null) {
			return null;
		}

		return new Branch(entity.getMeta(), entity.getName(), branch.getFranchiseId(), branch.getFranchiseName());
	}

	@Override
	public Mono<Branch> update(Branch branch) {
		return getById(branch.getId()).flatMap(item -> {
			var entity = new FranchiseDynamoEntity(DynamoSeparatorUtil.separateKeys(item.getId()).getValueOne(),
					item.getId(), branch.getName());
			franchiseTable.updateItem(entity);
			return Mono.just(entityToDomain(entity, branch));
		});
	}
}
