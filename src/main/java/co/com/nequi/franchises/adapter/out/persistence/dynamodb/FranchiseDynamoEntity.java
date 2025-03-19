package co.com.nequi.franchises.adapter.out.persistence.dynamodb;

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
public class FranchiseDynamoEntity {
	
	public static final TableSchema<FranchiseDynamoEntity> TABLE_SCHEMA = TableSchema
			.fromBean(FranchiseDynamoEntity.class);

	public static final String META_NAME = "META";

	private String id;

	private String meta;

	private String name;

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
}
