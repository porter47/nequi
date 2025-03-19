package co.com.nequi.franchises.adapter.out.persistence.dynamodb.product;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDynamoEntity {
	
	public static final String TOP_PREFIX = "TOP";

	public static final TableSchema<ProductDynamoEntity> TABLE_SCHEMA = TableSchema.fromBean(ProductDynamoEntity.class);

	private String id;

	private String meta;

	private String name;

	private String branchName;

	private Double stock;

	private String aggregatedId;

	@DynamoDbAttribute("HASH_KEY")
	@DynamoDbPartitionKey
	public String getId() {
		return id;
	}

	@DynamoDbAttribute("SORT_KEY")
	@DynamoDbSortKey
	public String getMeta() {
		return meta;
	}

	@DynamoDbAttribute("NAME")
	public String getName() {
		return name;
	}

	@DynamoDbAttribute("BRANCH")
	public String getBranchName() {
		return branchName;
	}

	@DynamoDbAttribute("STOCK")
	public Double getStock() {
		return stock;
	}

	@DynamoDbAttribute("PROD_KEY")
	public String getAggregatedId() {
		return aggregatedId;
	}
}
