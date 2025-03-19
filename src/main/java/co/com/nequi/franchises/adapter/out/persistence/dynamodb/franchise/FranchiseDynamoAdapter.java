package co.com.nequi.franchises.adapter.out.persistence.dynamodb.franchise;

import java.util.UUID;

import co.com.nequi.franchises.adapter.out.persistence.dynamodb.DynamoClientConfig;
import co.com.nequi.franchises.adapter.out.persistence.dynamodb.FranchiseDynamoEntity;
import co.com.nequi.franchises.application.domain.Franchise;
import co.com.nequi.franchises.application.domain.MessagesEnum;
import co.com.nequi.franchises.application.exceptions.NequiException;
import co.com.nequi.franchises.application.ports.out.persistence.FranchisePersistencePort;
import co.com.nequi.franchises.infrastructure.annotations.Adapter;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;

@Adapter
@Slf4j
class FranchiseDynamoAdapter implements FranchisePersistencePort {

	private final DynamoDbTable<FranchiseDynamoEntity> franchiseTable;

	public FranchiseDynamoAdapter(DynamoDbEnhancedClient client) {
		this.franchiseTable = client.table(DynamoClientConfig.TABLE_NAME, FranchiseDynamoEntity.TABLE_SCHEMA);
	}

	@Override
	public Mono<Franchise> save(Franchise franchise) {

		log.info("Start saving franchise {}", franchise.getName());

		var entity = new FranchiseDynamoEntity(UUID.randomUUID().toString(), FranchiseDynamoEntity.META_NAME,
				franchise.getName());

		return Mono.defer(() -> {
			franchiseTable.putItem(entity);
			return Mono.just(entityToDomain(entity));
		});
	}

	@Override
	public Mono<Franchise> getById(String id) {
		log.info("Start query franchise with id {}", id);

		return Mono.defer(() -> {
			var entity = franchiseTable.getItem(new FranchiseDynamoEntity(id, FranchiseDynamoEntity.META_NAME, null));
			if (entity == null) {
				throw new NequiException(MessagesEnum.FR_NOT_FOUND);
			}

			return Mono.just(entityToDomain(entity));
		});
	}

	private Franchise entityToDomain(FranchiseDynamoEntity entity) {
		return new Franchise(entity.getId(), entity.getName());
	}

	@Override
	public Mono<Franchise> update(Franchise franchise) {
		return getById(franchise.getId()).flatMap(item -> {
			var entity = new FranchiseDynamoEntity(item.getId(), FranchiseDynamoEntity.META_NAME, franchise.getName());
			franchiseTable.updateItem(entity);
			return Mono.just(entityToDomain(entity));
		});
	}
}
