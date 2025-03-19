package co.com.nequi.franchises.adapter.out.persistence.dynamodb.product;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.com.nequi.franchises.adapter.out.persistence.dynamodb.DynamoClientConfig;
import co.com.nequi.franchises.adapter.out.persistence.dynamodb.DynamoSeparatorUtil;
import co.com.nequi.franchises.application.domain.MessagesEnum;
import co.com.nequi.franchises.application.domain.Product;
import co.com.nequi.franchises.application.exceptions.NequiException;
import co.com.nequi.franchises.application.ports.out.persistence.ProductPersistencePort;
import co.com.nequi.franchises.infrastructure.annotations.Adapter;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.IgnoreNullsMode;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.UpdateItemEnhancedRequest;

@Adapter
@Slf4j
class ProductDynamoAdapter implements ProductPersistencePort {

	private final DynamoDbTable<ProductDynamoEntity> productTable;

	public ProductDynamoAdapter(DynamoDbEnhancedClient client) {
		this.productTable = client.table(DynamoClientConfig.TABLE_NAME, ProductDynamoEntity.TABLE_SCHEMA);
	}

	@Override
	public Mono<Product> save(Product product) {
		log.info("Start saving product {}", product.getName());

		var entity = new ProductDynamoEntity();
		entity.setId(DynamoSeparatorUtil.separateKeys(product.getBranchId()).getValueOne());
		entity.setMeta(product.getBranchId() + DynamoSeparatorUtil.SEPARATOR + UUID.randomUUID().toString());
		entity.setName(product.getName());
		entity.setStock(product.getStock());

		return Mono.defer(() -> {
			productTable.putItem(entity);
			return Mono.just(entityToDomain(entity, product));
		});
	}

	@Override
	public Mono<Product> update(Product product) {

		return Mono.defer(() -> {
			var ent = getEntityById(product.getId());

			if (ent == null) {
				throw new NequiException(MessagesEnum.PR_NOT_FOUND);
			}

			ent.setName(product.getName());
			ent.setStock(product.getStock());

			var request = UpdateItemEnhancedRequest.builder(ProductDynamoEntity.class).item(ent)
					.ignoreNullsMode(IgnoreNullsMode.MAPS_ONLY).build();

			var response = productTable.updateItem(request);

			product.setName(response.getName());

			return Mono.just(product);
		});
	}

	private ProductDynamoEntity getEntityById(String productId) {
		log.info("Start query product with id {}", productId);

		var keys = DynamoSeparatorUtil.separateKeys(productId);

		Key key = Key.builder().partitionValue(keys.getValueOne()).sortValue(productId).build();

		return productTable.getItem(key);

	}

	@Override
	public Mono<Product> getById(String productId) {

		return Mono.defer(() -> {
			var item = getEntityById(productId);

			if (item == null) {
				return Mono.just(new Product());
			}

			var product = new Product();
			product.setId(item.getMeta());
			product.setName(item.getName());
			product.setStock(item.getStock());
			return Mono.just(product);
		});
	}

	@Override
	// TODO this does not scale on production mode
	public Mono<List<Product>> findMaxStock(String franchiseId) {

		return Mono.defer(() -> {

			var queryConditional = QueryConditional.sortBeginsWith(Key.builder().partitionValue(franchiseId)
					.sortValue(franchiseId + DynamoSeparatorUtil.SEPARATOR).build());

			var response = productTable.query(queryConditional);

			List<Product> products = new ArrayList<>();

			response.forEach(page -> {
				var items = page.items();
				var productList = items.stream().map(this::entityToDomain).toList();
				products.addAll(productList);
			});

			return Mono.just(products);
		});

	}

	@Override
	public Mono<Void> delete(String id) {

		var keys = DynamoSeparatorUtil.separateKeys(id);

		Key key = Key.builder().partitionValue(keys.getValueOne()).sortValue(id).build();

		var deletedItem = productTable.deleteItem(key);

		return Mono.fromCallable(() -> deletedItem).flatMap(v -> {
			log.info("Product deleted");
			return Mono.<Void>empty();
		});
	}

	private Product entityToDomain(ProductDynamoEntity entity, Product product) {
		var domain = new Product();
		domain.setId(entity.getMeta());
		domain.setName(entity.getName());
		domain.setStock(entity.getStock());
		domain.setBranchId(product.getBranchId());
		domain.setBranchName(product.getBranchName());

		return domain;
	}

	private Product entityToDomain(ProductDynamoEntity entity) {
		var domain = new Product();
		domain.setId(entity.getMeta());
		domain.setName(entity.getName());
		domain.setStock(entity.getStock());
		return domain;
	}
}
